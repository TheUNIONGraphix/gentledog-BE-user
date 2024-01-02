package gentledog.members.members.members.dto;

import gentledog.members.members.members.entity.MemberGenderStatus;
import lombok.Getter;

@Getter
public class MembersInfoUpdateDto {

    private String membersEmail;
    private String membersName;
    private String membersPhoneNumber;
    private Integer membersAge;
    private MemberGenderStatus membersGender;

}
