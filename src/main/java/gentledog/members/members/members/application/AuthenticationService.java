package gentledog.members.members.members.application;


import gentledog.members.members.members.dto.in.SignInInDto;
import gentledog.members.members.members.dto.in.SignUpInDto;
import gentledog.members.members.members.dto.out.RegenerateTokenOutDto;
import gentledog.members.members.members.dto.out.SignInOutDto;
import gentledog.members.members.members.webdto.request.SignUpRequestDto;
import gentledog.members.members.members.webdto.response.SignInResponseDto;

public interface AuthenticationService {

    void signUp(SignUpInDto signUpInDto);
    SignInOutDto signIn(SignInInDto signInInDto);
    void signOut(String accessToken);
    RegenerateTokenOutDto regenerateToken(String email, String token);
}
