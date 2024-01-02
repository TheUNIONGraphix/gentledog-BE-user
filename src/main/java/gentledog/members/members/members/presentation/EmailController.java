package gentledog.members.members.members.presentation;

import gentledog.members.global.common.response.BaseResponse;
import gentledog.members.members.members.application.MailService;
import gentledog.members.members.members.dto.EmailAuthRequestDto;
import gentledog.members.members.members.dto.EmailVerifyRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/members")
@Slf4j
@RequiredArgsConstructor
public class EmailController {

    private final MailService mailService;

    @Operation(summary = "이메일 중복 검사", description = "이메일 중복 검사", tags = { "Members Sign" })
    @GetMapping("/signup/email-check")
    public BaseResponse<?> checkEmail(@RequestParam String membersEmail) {
        return new BaseResponse<>(mailService.verifyEmail(membersEmail));
    }


    @Operation(summary = "이메일 인증 요청", description = "이메일 인증 요청", tags = { "Members Sign" })
    @PostMapping("/signup/email-auth")
    public BaseResponse<?> sendEmailAuthentication(@RequestBody EmailAuthRequestDto emailAuthRequestDto)
            throws MessagingException {
        mailService.sendEmailAuthentication(emailAuthRequestDto.getMembersEmail());
        return new BaseResponse<>();
    }

    @Operation(summary = "이메일 인증 확인", description = "이메일 인증 확인", tags = { "Members Sign" })
    @PostMapping ("/signup/email-verify")
    public BaseResponse<?> emailVerify(@RequestBody EmailVerifyRequestDto emailVerifyRequestDto) {
        mailService.verifyEmailCode(emailVerifyRequestDto.getMembersEmail(), emailVerifyRequestDto.getCode());
        return new BaseResponse<>();
    }
}
