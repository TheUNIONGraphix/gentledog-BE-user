package gentledog.members.members.members.application;


import gentledog.members.members.members.dto.SignInRequestDto;
import gentledog.members.members.members.dto.SignUpRequestDto;
import gentledog.members.members.members.response.SignInResponse;

public interface AuthenticationService {

    void signUp(SignUpRequestDto signUpRequestDto);
    SignInResponse signIn(SignInRequestDto signInRequestDto);
    void signOut(String accessToken);
    SignInResponse regenerateToken(String email, String token);
}
