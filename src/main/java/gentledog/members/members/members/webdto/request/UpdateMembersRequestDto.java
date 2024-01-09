package gentledog.members.members.members.webdto.request;

import gentledog.members.members.members.entity.MemberGenderStatus;
import lombok.Getter;

@Getter
public class UpdateMembersRequestDto {

    private String membersEmail;
    private String membersName;
    private String membersPhoneNumber;
    private Integer membersAge;
    private MemberGenderStatus membersGender;

}
