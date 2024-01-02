package gentledog.members.members.members.application;

import gentledog.members.members.members.dto.MembersInfoUpdateDto;
import gentledog.members.members.members.response.MembersFindEmailResponse;
import gentledog.members.members.members.response.MembersInfoResponse;

public interface MembersService {

    MembersInfoResponse getMembersInfo(String email);
    void updateMembersInfo(String email, MembersInfoUpdateDto membersInfoUpdateDto);
    MembersFindEmailResponse findMembersEmail(String membersPhoneNumber);
    void updateMembersPassword(String membersEmail, String newPassword);
    void withdraw(String membersEmail);

}
