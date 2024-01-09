package gentledog.members.members.address.application;

import gentledog.members.members.address.dto.in.CreateAddressInDto;
import gentledog.members.members.address.dto.in.UpdateDefaultAddressInDto;
import gentledog.members.members.address.dto.out.GetAddressOutDto;
import gentledog.members.members.address.dto.out.GetDefaultAddressOutDto;
import gentledog.members.members.address.webdto.request.UpdateDefaultAddressRequestDto;
import gentledog.members.members.address.webdto.request.CreateAddressRequestDto;
import gentledog.members.members.address.webdto.response.GetAddressResponseDto;
import gentledog.members.members.address.webdto.response.GetDefaultAddressResponseDto;

import java.util.List;

public interface AddressService {

    void createAddress(String membersEmail, CreateAddressInDto createAddressInDto);
    List<GetAddressOutDto> getAddress(String membersEmail);
    GetDefaultAddressOutDto getDefaultAddress(String membersEmail);
    void updateAddress(String membersEmail, Long addressId, CreateAddressInDto createAddressInDto);
    void updateDefaultAddress(String membersEmail, UpdateDefaultAddressInDto updateDefaultAddressInDto);
    void deleteAddress(Long addressId);
}
