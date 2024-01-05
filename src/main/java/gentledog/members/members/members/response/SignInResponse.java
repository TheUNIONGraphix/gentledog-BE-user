package gentledog.members.members.members.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInResponse {

    private String accessToken;
    private String refreshToken;
    private String membersEmail;
    private String membersName;
    private Long dogId;
    private String role;

}
