package gentledog.members.members.address.application;

import gentledog.members.members.address.dto.AddressDefaultUpdateRequestDto;
import gentledog.members.members.address.dto.AddressRegistrationRequestDto;
import gentledog.members.members.address.response.AddressInfoResponse;

import java.util.List;

public interface AddressService {

    void registerAddress(String membersEmail, AddressRegistrationRequestDto addressRegistrationRequestDto);
    List<AddressInfoResponse> findAddress(String membersEmail);
    AddressInfoResponse findDefaultAddress(String membersEmail);
    void updateAddress(String membersEmail, Long addressId, AddressRegistrationRequestDto addressRegistrationRequestDto);
    void updateDefaultAddress(String membersEmail, AddressDefaultUpdateRequestDto addressDefaultUpdateRequestDto);
    void deleteAddress(Long addressId);
}
