package gentledog.members.members.members.webdto.response;

import gentledog.members.members.members.entity.MemberGenderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetMembersResponseDto {

    private String membersEmail;
    private String membersName;
    private String membersPhoneNumber;
    private Integer membersAge;
    private MemberGenderStatus membersGender;

}
