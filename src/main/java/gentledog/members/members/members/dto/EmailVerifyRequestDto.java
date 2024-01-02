package gentledog.members.members.members.dto;

import lombok.Getter;

@Getter
public class EmailVerifyRequestDto {

    private String membersEmail;
    private String code;

}
