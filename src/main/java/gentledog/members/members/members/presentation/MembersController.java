package gentledog.members.members.members.presentation;

import gentledog.members.global.common.response.BaseResponse;
import gentledog.members.members.members.application.AuthenticationService;
import gentledog.members.members.members.application.MembersService;
import gentledog.members.members.members.dto.in.*;
import gentledog.members.members.members.dto.out.SignInOutDto;
import gentledog.members.members.members.webdto.request.*;
import gentledog.members.members.members.webdto.response.RegenerateTokenResponseDto;
import gentledog.members.members.members.webdto.response.SignInResponseDto;
import gentledog.members.members.members.webdto.response.GetMembersEmailResponseDto;
import gentledog.members.members.members.webdto.response.GetMembersResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@Slf4j
@RequiredArgsConstructor
public class MembersController {

    private final AuthenticationService authenticationService;
    private final MembersService membersService;
    private final ModelMapper modelMapper;

    /**
     *
     * 1. 회원가입
     * 2. 로그인
     * 3. 유저 정보 조회
     * 4. 유저 정보 수정
     * 5. 유저 이메일 찾기
     * 6. 유저 비밀번호 수정
     * 7. 유저 탈톼
     * 8. 유저 로그아웃
     * 9. 리프레쉬 토큰 재발급
     *
     */

    @Operation(summary = "회원가입", description = "회원가입", tags = { "Members Sign" })
    @PostMapping("/signup")
    public BaseResponse<?> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {

        SignUpInDto signUpInDto = modelMapper.map(signUpRequestDto, SignUpInDto.class);
        authenticationService.signUp(signUpInDto);
        return new BaseResponse<>();
    }

    @Operation(summary = "로그인", description = "로그인", tags = { "Members Sign" })
    @PostMapping("/signin")
    public BaseResponse<SignInResponseDto> signIn(@RequestBody SignInRequestDto signinRequestDto) {

        SignInInDto signInInDto = modelMapper.map(signinRequestDto, SignInInDto.class);
        SignInOutDto signInOutDto = authenticationService.signIn(signInInDto);
        SignInResponseDto signInResponseDto = modelMapper.map(signInOutDto, SignInResponseDto.class);

        return new BaseResponse<>(signInResponseDto);
    }

    @Operation(summary = "유저 조회", description = "유저 조회", tags = { "Members Sign" })
    @GetMapping("/info")
    public BaseResponse<GetMembersResponseDto> getMembers(@RequestHeader("membersEmail") String membersEmail) {

        GetMembersResponseDto getMembersResponseDto = modelMapper.map(membersService.getMembers(membersEmail),
                GetMembersResponseDto.class);

        return new BaseResponse<>(getMembersResponseDto);
    }

    @Operation(summary = "유저 정보 수정", description = "유저 정보 수정", tags = { "Members Sign" })
    @PutMapping("/info")
    public BaseResponse<?> updateMembers(@RequestHeader("membersEmail") String membersEmail,
                                         @RequestBody UpdateMembersRequestDto updateMembersRequestDto) {

        UpdateMembersInDto updateMembersInDto = modelMapper.map(updateMembersRequestDto, UpdateMembersInDto.class);
        membersService.updateMembers(membersEmail, updateMembersInDto);
        return new BaseResponse<>();
    }

    @Operation(summary = "유저 이메일 찾기", description = "유저 이메일 찾기", tags = { "Members Sign" })
    @GetMapping("/email")
    public BaseResponse<GetMembersEmailResponseDto> getMembersEmail(@RequestParam("phonenumber")
                                                                         String membersPhoneNumber) {

        GetMembersEmailResponseDto getMembersEmailResponseDto = modelMapper.map(
                membersService.getMembersEmail(membersPhoneNumber), GetMembersEmailResponseDto.class);
        return new BaseResponse<>(getMembersEmailResponseDto);
    }

    @Operation(summary = "유저 비밀번호 수정", description = "유저 비밀번호 수정", tags = { "Members Sign" })
    @PutMapping("/password")
    public BaseResponse<?> updateMembersPassword(@RequestHeader("membersEmail") String membersEmail,
                                                 @RequestBody UpdateMembersPasswordRequestDto
                                                         updateMembersPasswordRequestDto) {

        UpdateMembersPasswordInDto updateMembersPasswordInDto = modelMapper.map(
                updateMembersPasswordRequestDto, UpdateMembersPasswordInDto.class);
        membersService.updateMembersPassword(membersEmail, updateMembersPasswordInDto.getPassword());
        return new BaseResponse<>();
    }

    @Operation(summary = "유저 탈퇴", description = "유저 탈퇴", tags = { "Members Sign" })
    @PutMapping("/withdraw")
    public BaseResponse<?> withdraw(@RequestHeader("membersEmail") String membersEmail) {

        membersService.withdraw(membersEmail);
        return new BaseResponse<>();
    }

    @Operation(summary = "유저 로그아웃", description = "유저 로그아웃", tags = { "Members Sign" })
    @PostMapping("/signout")
    public BaseResponse<?> signOut(@RequestHeader("membersEmail") String membersEmail) {

        authenticationService.signOut(membersEmail);
        return new BaseResponse<>();
    }

    @Operation(summary = "토큰 재발급", description = "access token이 만료 됐다면 실행", tags = { "Members Sign" })
    @PostMapping("/refresh")
    public BaseResponse<RegenerateTokenResponseDto> regenerateToken(@RequestHeader("membersEmail") String membersEmail,
                                                           @RequestBody RegenerateTokenRequestDto
                                                                   regenerateTokenRequestDto) {

        RegenerateTokenInDto regenerateTokenInDto = modelMapper.map(regenerateTokenRequestDto,
                RegenerateTokenInDto.class);
        RegenerateTokenResponseDto regenerateTokenResponseDto = modelMapper.map(authenticationService.regenerateToken(membersEmail,
                regenerateTokenInDto.getRefreshToken()), RegenerateTokenResponseDto.class);
        return new BaseResponse<>(regenerateTokenResponseDto);

    }

}
