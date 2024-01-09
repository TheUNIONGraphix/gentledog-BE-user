package gentledog.members.members.members.presentation;

import gentledog.members.global.common.response.BaseResponse;
import gentledog.members.members.members.application.MailService;
import gentledog.members.members.members.dto.in.CreateAuthEmailInDto;
import gentledog.members.members.members.dto.in.CheckEmailInDto;
import gentledog.members.members.members.webdto.request.AuthEmailRequestDto;
import gentledog.members.members.members.webdto.request.CheckEmailRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/members")
@Slf4j
@RequiredArgsConstructor
public class EmailController {

    private final MailService mailService;
    private final ModelMapper modelMapper;

    @Operation(summary = "이메일 중복 검사", description = "이메일 중복 검사", tags = { "Members Sign" })
    @GetMapping("/signup/email-duplicate")
    public BaseResponse<?> isDuplicateEmail(@RequestParam String membersEmail) {

        return new BaseResponse<>(mailService.isDuplicateEmail(membersEmail));
    }

    @Operation(summary = "이메일 인증 요청", description = "이메일 인증 요청", tags = { "Members Sign" })
    @PostMapping("/signup/email-auth")
    public BaseResponse<?> authEmail(@RequestBody AuthEmailRequestDto authEmailRequestDto)
            throws MessagingException {

        CreateAuthEmailInDto createAuthEmailInDto = modelMapper.map(authEmailRequestDto, CreateAuthEmailInDto.class);
        mailService.sendEmailAuthentication(createAuthEmailInDto.getMembersEmail());
        return new BaseResponse<>();
    }

    @Operation(summary = "이메일 인증 확인", description = "이메일 인증 확인", tags = { "Members Sign" })
    @PostMapping ("/signup/email-check")
    public BaseResponse<?> checkEmail(@RequestBody CheckEmailRequestDto checkEmailRequestDto) {

        CheckEmailInDto checkEmailInDto = modelMapper.map(checkEmailRequestDto, CheckEmailInDto.class);
        mailService.verifyEmailCode(checkEmailInDto.getMembersEmail(), checkEmailInDto.getCode());
        return new BaseResponse<>();
    }
}
