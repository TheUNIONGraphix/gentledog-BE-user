package gentledog.members.members.members.response;

import gentledog.members.members.members.entity.MemberGenderStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MembersInfoResponse {

    private String membersEmail;
    private String membersName;
    private String membersPhoneNumber;
    private Integer membersAge;
    private MemberGenderStatus membersGender;

}
