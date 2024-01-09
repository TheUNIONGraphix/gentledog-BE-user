package gentledog.members.members.members.application;

import gentledog.members.members.members.dto.in.UpdateMembersInDto;
import gentledog.members.members.members.dto.out.GetMembersEmailOutDto;
import gentledog.members.members.members.dto.out.GetMembersOutDto;
import gentledog.members.members.members.webdto.request.UpdateMembersRequestDto;

public interface MembersService {

    GetMembersOutDto getMembers(String email);
    void updateMembers(String email, UpdateMembersInDto updateMembersInDto);
    GetMembersEmailOutDto getMembersEmail(String membersPhoneNumber);
    void updateMembersPassword(String membersEmail, String newPassword);
    void withdraw(String membersEmail);

}
