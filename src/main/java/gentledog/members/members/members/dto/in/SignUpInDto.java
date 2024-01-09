package gentledog.members.members.members.dto.in;

import gentledog.members.members.members.entity.MemberGenderStatus;
import lombok.Getter;

@Getter
public class SignUpInDto {

    private String membersEmail;
    private String password;
    private String membersName;
    private String membersPhoneNumber;
    private Integer membersAge;
    private MemberGenderStatus membersGender;

}
