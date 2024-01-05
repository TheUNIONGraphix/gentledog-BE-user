package gentledog.members.members.members.application;

import gentledog.members.global.common.response.BaseResponseStatus;
import gentledog.members.global.common.exception.BaseException;
import gentledog.members.members.members.dto.MembersInfoUpdateDto;
import gentledog.members.members.members.entity.Members;
import gentledog.members.members.members.infrastructure.MembersRepository;
import gentledog.members.members.members.response.MembersFindEmailResponse;
import gentledog.members.members.members.response.MembersInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MembersServiceImple implements MembersService {

    /**
     *
     * 1. 유저 정보 조회
     * 2. 유저 정보 수정
     * 3. 유저 아이디 찾기
     * 4. 유저 비밀번호 수정
     * 5. 유저 탈퇴
     *
     * @param
     * @return
     */

    private final MembersRepository membersRepository;

    // 1. 유저 정보 조회
    @Override
    @Transactional(readOnly = true)
    public MembersInfoResponse getMembersInfo(String email) {
        Members members = membersRepository.findByMembersEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_MEMBERS));
        log.info("members is : {}", members);
        return MembersInfoResponse.builder()
                .membersEmail(members.getMembersEmail())
                .membersName(members.getMembersName())
                .membersPhoneNumber(members.getMembersPhoneNumber())
                .membersAge(members.getMembersAge())
                .membersGender(members.getMembersGender())
                .build();
    }

    // 2. 유저 정보 수정
    @Override
    public void updateMembersInfo(String email, MembersInfoUpdateDto membersInfoUpdateDto) {
        Members members = membersRepository.findByMembersEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_MEMBERS));
        members.updateMembersInfo(membersInfoUpdateDto.getMembersEmail()
                , membersInfoUpdateDto.getMembersName()
                , membersInfoUpdateDto.getMembersPhoneNumber()
                , membersInfoUpdateDto.getMembersAge()
                , membersInfoUpdateDto.getMembersGender());

    }

    // 3. 유저 이메일 찾기
    @Override
    @Transactional(readOnly = true)
    public MembersFindEmailResponse findMembersEmail(String membersPhoneNumber) {
        Members members = membersRepository.findByMembersPhoneNumber(membersPhoneNumber)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_MEMBERS));

        return MembersFindEmailResponse.builder()
                .membersEmail(members.getMembersEmail())
                .build();

    }

    // 4. 유저 비밀번호 수정
    @Override
    public void updateMembersPassword(String email, String newPassword) {
        Members members = membersRepository.findByMembersEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_MEMBERS));

        String phoneNum = members.getMembersPhoneNumber();

        /**
         * 개인정보를 기반으로 구성된 비밀번호는 보안상의 위험성을 가지고 있습니다.
         * 예측 가능성, 해킹 공격에 취약 등등 많은 이유가 있으며
         * 많은 보안 정책과 규정은 사용자가 개인정보를 기반으로 한 비밀번호를 사용하지 않도록 권고하고 있습니다.
         */

        // 전화번호의 중간 4자리와 끝 4자리를 추출하여 비밀번호와 비교
        String middleNum = phoneNum.substring(3, 7);
        String lastNum = phoneNum.substring(phoneNum.length() - 4);

        // 비밀번호가 같거나, 비밀번호에 이메일이 포함되어 있거나, 비밀번호에 전화번호가 포함되어 있으면 실패
        if (new BCryptPasswordEncoder().matches(newPassword, members.getPassword())) {
            throw new BaseException(BaseResponseStatus.PASSWORD_SAME_FAILED);
        } else if (newPassword.contains(email)) {
            throw new BaseException(BaseResponseStatus.PASSWORD_CONTAIN_EMAIL_FAILED);
        } else if (newPassword.contains(middleNum) || newPassword.contains(lastNum)) {
            throw new BaseException(BaseResponseStatus.PASSWORD_CONTAIN_NUM_FAILED);
        } else {
            // 비밀번호가 같지 않으면 비밀번호를 암호화하여 저장
            members.hashPassword(newPassword);
        }
    }

    // 5. 유저 탈퇴
    @Override
    public void withdraw(String email) {
        Members members = membersRepository.findByMembersEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_MEMBERS));
        members.deactivateMembers();
    }



}
