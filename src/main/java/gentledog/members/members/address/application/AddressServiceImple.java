package gentledog.members.members.address.application;

import gentledog.members.members.address.dto.AddressDefaultUpdateRequestDto;
import gentledog.members.members.address.dto.AddressRegistrationRequestDto;
import gentledog.members.members.address.entity.Address;
import gentledog.members.members.address.entity.AddressList;
import gentledog.members.members.address.infrastructure.AddressListRepository;
import gentledog.members.members.address.infrastructure.AddressRepository;
import gentledog.members.members.address.response.AddressInfoResponse;
import gentledog.members.global.common.response.BaseResponseStatus;
import gentledog.members.global.common.exception.BaseException;
import gentledog.members.members.members.entity.Members;
import gentledog.members.members.members.infrastructure.MembersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AddressServiceImple implements AddressService {

    /**
     * 1. 배송지 등록
     * 2. 배송지 조회
     * 3. 배송지 수정
     * 4. 대표 배송지 변경
     * 5. 배송지 삭제
     */

    private final MembersRepository membersRepository;
    private final AddressRepository addressRepository;
    private final AddressListRepository addressListRepository;
    private final ModelMapper modelMapper;

    /**
     * 1. 배송지 등록
     * @param membersEmail
     * @param addressRegistrationRequestDto
     */
    @Override
    public void registerAddress(String membersEmail, AddressRegistrationRequestDto addressRegistrationRequestDto) {
        Members members = membersRepository.findByMembersEmail(membersEmail)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_MEMBERS));

        /**
         * addressRegistrationRequestDto에서 true값이 들어오고 기존에 기존에 다른 default true값이 있다면 false로 변경 하고
         * address에 default true값을 넣어준다.
         */

        if (addressRegistrationRequestDto.getDefaultAddress()) {
            AddressList addressList1 = addressListRepository.findByMembersIdAndDefaultAddress(members.getId(),
                    true);

            if (addressList1 != null) {
                addressList1.updateDefaultAddress(false);
            }

        }

        // addressRegistrationRequestDto를 address 엔터티로 매핑
        Address address = modelMapper.map(addressRegistrationRequestDto, Address.class);
        addressRepository.save(address);

        // addresslist의 default값 변경
        AddressList addressList = AddressList.builder()
                .address(address)
                .members(members)
                .defaultAddress(addressRegistrationRequestDto.getDefaultAddress())
                .build();

        addressListRepository.save(addressList);
    }

    /**
     * @param membersEmail
     * @return
     */
    @Override
    public List<AddressInfoResponse> findAddress(String membersEmail) {
        Members members = membersRepository.findByMembersEmail(membersEmail)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_MEMBERS));

        // 유저의 주소지 리스트를 조회
        List<AddressList> addressListList = addressListRepository.findByMembersId(members.getId());
        log.info("{}", addressListList);
        // 주소지 리스트를 AddressInfoResponse로 매핑
        return addressListList.stream().map(item -> {
            // 주소지 엔터티를 조회
            Address address = item.getAddress();
            // 주소지 엔터티를 AddressInfoResponse로 매핑

            AddressInfoResponse addressInfoResponse = modelMapper.map(address, AddressInfoResponse.class);
            // 주소지 엔터티의 defaultAddress값을 AddressInfoResponse에 넣어준다.
            addressInfoResponse = addressInfoResponse.toBuilder()
                    .addressId(item.getAddress().getId())
                    .defaultAddress(item.getDefaultAddress())
                    .build();
            return addressInfoResponse;
        }).toList();

    }

    @Override
    public AddressInfoResponse findDefaultAddress(String membersEmail) {
        Members members = membersRepository.findByMembersEmail(membersEmail)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_MEMBERS));

        AddressList addressList = addressListRepository.findByMembersIdAndDefaultAddress(members.getId(), true);
        if (addressList != null) {
            Address address = addressList.getAddress();
            AddressInfoResponse addressInfoResponse = modelMapper.map(address, AddressInfoResponse.class);
            addressInfoResponse = addressInfoResponse.toBuilder()
                    .defaultAddress(addressList.getDefaultAddress())
                    .build();
            return addressInfoResponse;
        } else {
            return null;
        }
    }

    /**
     * @param addressId
     * @param addressRegistrationRequestDto
     */
    @Override
    public void updateAddress(String membersEmail, Long addressId, AddressRegistrationRequestDto addressRegistrationRequestDto) {
        Members members = membersRepository.findByMembersEmail(membersEmail)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_MEMBERS));

        // addressList에서 true값이 들어오고 기존에 기존에 다른 default true값이 있다면 false로 변경 하고
        // address에 default true값을 넣어준다.
        if (addressRegistrationRequestDto.getDefaultAddress()) {
            AddressList addressList1 = addressListRepository.findByMembersIdAndDefaultAddress(members.getId(),
                    true);

            if (addressList1 != null) {
                addressList1.updateDefaultAddress(false);
            }
        }

        //
        Address address = addressRepository.findById(addressId)
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_ADDRESS));
        address.updateAddress(addressRegistrationRequestDto);

        AddressList addressList = addressListRepository.findByMembersIdAndAddressId(members.getId(), addressId);
        addressList.updateDefaultAddress(addressRegistrationRequestDto.getDefaultAddress());

    }

    /**
     * @param membersEmail
     * @param addressDefaultUpdateRequestDto
     */
    @Override
    public void updateDefaultAddress(String membersEmail, AddressDefaultUpdateRequestDto addressDefaultUpdateRequestDto) {

        Members members = membersRepository.findByMembersEmail(membersEmail)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_MEMBERS));

        // 1. oldAddress로 addressList의 defaultAddress를 false로 변경
        if (addressDefaultUpdateRequestDto.getOldDefaultAddressId() != null) {
            AddressList addressList = addressListRepository.findByMembersIdAndAddressId(members.getId(),
                    addressDefaultUpdateRequestDto.getOldDefaultAddressId());
            addressList.updateDefaultAddress(false);
        }

        // 2. newAddress로 addressList의 defaultAddress를 true로 변경
        AddressList addressList = addressListRepository.findByMembersIdAndAddressId(members.getId(),
                addressDefaultUpdateRequestDto.getNewDefaultAddressId());
        addressList.updateDefaultAddress(true);
    }

    /**
     *
     * @param addressId
     */
    @Override
    public void deleteAddress(Long addressId) {



        AddressList addressList = addressListRepository.findByAddressId(addressId);
        addressListRepository.delete(addressList);

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_ADDRESS));
        addressRepository.delete(address);

    }
}
