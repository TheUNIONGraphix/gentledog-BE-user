package gentledog.members.members.members.presentation;

import gentledog.members.global.common.response.BaseResponse;
import gentledog.members.members.members.application.AuthenticationService;
import gentledog.members.members.members.application.MembersService;
import gentledog.members.members.members.dto.*;
import gentledog.members.members.members.response.SignInResponse;
import gentledog.members.members.members.response.MembersFindEmailResponse;
import gentledog.members.members.members.response.MembersInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@Slf4j
@RequiredArgsConstructor
public class MembersController {

    private final AuthenticationService authenticationService;
    private final MembersService membersService;

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
            authenticationService.signUp(signUpRequestDto);
            return new BaseResponse<>();
    }

    @Operation(summary = "로그인", description = "로그인", tags = { "Members Sign" })
    @PostMapping("/signin")
    public BaseResponse<SignInResponse> signIn(@RequestBody SignInRequestDto signinRequestDto) {
            SignInResponse signInResponse = authenticationService.signIn(signinRequestDto);
            return new BaseResponse<>(signInResponse);
    }

    @Operation(summary = "유저 정보 조회", description = "유저 정보 조회", tags = { "Members Sign" })
    @GetMapping("/info")
    public BaseResponse<MembersInfoResponse> getMembersInfo(@RequestHeader("membersEmail") String membersEmail) {
        // 토큰값에서 loginId 추출
        MembersInfoResponse membersInfoResponse = membersService.getMembersInfo(membersEmail);
        return new BaseResponse<>(membersInfoResponse);
    }

    @Operation(summary = "유저 정보 수정", description = "유저 정보 수정", tags = { "Members Sign" })
    @PutMapping("/info")
    public BaseResponse<?> updateMembersInfo(@RequestHeader("membersEmail") String membersEmail,
                                             @RequestBody MembersInfoUpdateDto membersInfoUpdateDto) {

        membersService.updateMembersInfo(membersEmail, membersInfoUpdateDto);
        return new BaseResponse<>();
    }

    @Operation(summary = "유저 이메일 찾기", description = "유저 이메일 찾기", tags = { "Members Sign" })
    @GetMapping("/email")
    public BaseResponse<MembersFindEmailResponse> findmembersEmail(@RequestParam("phonenumber") String membersPhoneNumber) {
        MembersFindEmailResponse membersFindEmailResponse = membersService.findMembersEmail(membersPhoneNumber);
        return new BaseResponse<>(membersFindEmailResponse);
    }

    @Operation(summary = "유저 비밀번호 수정", description = "유저 비밀번호 수정", tags = { "Members Sign" })
    @PutMapping("/password")
    public BaseResponse<?> updateMembersPassword(@RequestHeader("membersEmail") String membersEmail,
                                                 @RequestBody MembersPasswordUpdateDto membersPasswordUpdateDto) {
        membersService.updateMembersPassword(membersEmail, membersPasswordUpdateDto.getPassword());
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
    public BaseResponse<SignInResponse> regenerateToken(@RequestHeader("membersEmail") String membersEmail,
                                                        @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {

        SignInResponse signInResponse = authenticationService.regenerateToken(membersEmail, refreshTokenRequestDto.getRefreshToken());
        return new BaseResponse<>(signInResponse);

    }

}
