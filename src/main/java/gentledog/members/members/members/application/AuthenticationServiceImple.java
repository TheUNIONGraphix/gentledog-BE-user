package gentledog.members.members.members.application;

import gentledog.members.members.dog.entity.DogList;
import gentledog.members.members.dog.infrastructure.DogListRepository;
import gentledog.members.global.common.response.BaseResponseStatus;
import gentledog.members.members.members.security.JwtTokenProvider;
import gentledog.members.global.common.exception.BaseException;
import gentledog.members.global.common.util.RedisUtil;
import gentledog.members.members.members.dto.SignInRequestDto;
import gentledog.members.members.members.dto.SignUpRequestDto;
import gentledog.members.members.members.entity.Members;
import gentledog.members.members.members.infrastructure.MembersRepository;
import gentledog.members.members.members.response.SignInResponse;
import gentledog.members.members.members.response.SignUpResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImple implements AuthenticationService{

    private final MembersRepository membersRepository;
    private final DogListRepository dogListRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final RedisUtil redisUtil;

    private final RedisTemplate redisTemplate;
    @Value("${JWT.token.refresh-expiration-time}")
    private long refreshExpirationTime;

    /**
     * 1. 시큐리티 회원가입
     * 2. 시큐리티 로그인
     * 3. 리프레쉬 토큰 재발급
     * 4. 로그아웃
     */

    @Override
    public void signUp(SignUpRequestDto signUpRequestDto) {

        if (membersRepository.existsByMembersEmail(signUpRequestDto.getMembersEmail())) {
            throw new BaseException(BaseResponseStatus.DUPLICATED_MEMBERS);
        }

        Members members = modelMapper.map(signUpRequestDto, Members.class);

        members.hashPassword(members.getPassword());
        log.info("members is : {}" , members);
        membersRepository.save(members);

        SignUpResponse.builder()
                .membersEmail(members.getMembersEmail())
                .build();
    }

    @Override
    public SignInResponse signIn(SignInRequestDto signInRequestDto) {

        // 로그인 하는 이메일이 없다면 에러
        Members members = membersRepository.findByMembersEmail(signInRequestDto.getMembersEmail())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_LOGIN));

        // 강아지 리스트에서 대표 반려견을 찾고 해당하는 반려견의 정보를 넘겨준다.
        // 없다면 null을 넘겨준다
        Optional<DogList> dogList = dogListRepository.findByMembersIdAndDefaultDog(members.getId(), Boolean.TRUE);
        Long dogId = null;
        dogId = dogList.map(DogList::getId).orElse(null);

        // 탈퇴한 회원이면 로그인 불가
        if (members.getDeletedAt() != null) {
            throw new BaseException(BaseResponseStatus.WITHDRAWAL_MEMBERS);
        }
        log.info("signInRequestDto is : {}" , signInRequestDto.getPassword());
        log.info("members is : {}" , members.getPassword());

        // 회원정보 일치하지 않으면 에러
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequestDto.getMembersEmail(),
                        signInRequestDto.getPassword()
                )
        );

        String accessToken = jwtTokenProvider.generateToken(members);
        String refreshToken = jwtTokenProvider.generateRefreshToken(members);

        log.info("accessToken is : {}" , accessToken);
        log.info("refreshToken is : {}" , refreshToken);

        return SignInResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .membersEmail(members.getMembersEmail())
                .membersName(members.getMembersName())
                .dogId(dogId)
                .role("MEMBERS")
                .build();
    }

    public SignInResponse regenerateToken(String email, String token) {


        String refreshToken = token.substring(7);

        Members members = membersRepository.findByMembersEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_MEMBERS));


        log.info("refreshToken is : {}" , refreshToken);
        log.info("members : {}" , members);

        // key userEmain로 value refreshToken 가져온다
        String redisInRefreshToken = (String) redisTemplate.opsForValue().get(email);
        // redis에 저장된 refreshToken과 일치하는지 확인한다
        if (redisInRefreshToken == null) {
            // redis에 저장된 refreshToken이 없다면 만료된 토큰이거나 잘못된 토큰이다
            throw new BaseException(BaseResponseStatus.TokenExpiredException);
        } else if(!redisInRefreshToken.equals(refreshToken)) {
            // redis에 저장된 refreshToken과 일치하지 않는다면 잘못된 토큰이다
            throw new BaseException(BaseResponseStatus.TokenInvalidException);
        } else {
            // Redis에서 해당 유저 loginId key 를 삭제하고 재로그인 하도록 클라이언트를 리턴한다
            redisUtil.deleteData(email);
        }



        //내가 가진 refreshtoken이랑 레디스 refreshtoken같으면 레디스 수정
        String newAccessToken = jwtTokenProvider.generateToken(members);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(members);

        redisTemplate.opsForValue().set(
                email,
                newRefreshToken,
                refreshExpirationTime,
                TimeUnit.MILLISECONDS
        );



        return SignInResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .membersEmail(email)
                .membersName(members.getMembersName())
                .build();

    }

    // 로그아웃
    public void signOut(String email) {
        log.info("email is : {}" , email);
        redisTemplate.delete(email); //Token 삭제

    }
}
