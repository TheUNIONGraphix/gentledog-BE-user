package gentledog.members.members.members.webdto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegenerateTokenResponseDto {

    private String accessToken;
    private String refreshToken;
    private String membersEmail;
    private String membersName;
    private Long dogId;
    private String role;
}
