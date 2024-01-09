package gentledog.members.members.address.application;

import gentledog.members.members.address.dto.in.CreateAddressInDto;
import gentledog.members.members.address.dto.in.UpdateDefaultAddressInDto;
import gentledog.members.members.address.dto.out.GetAddressOutDto;
import gentledog.members.members.address.dto.out.GetDefaultAddressOutDto;
import gentledog.members.members.address.webdto.request.UpdateDefaultAddressRequestDto;
import gentledog.members.members.address.webdto.request.CreateAddressRequestDto;
import gentledog.members.members.address.entity.Address;
import gentledog.members.members.address.entity.AddressList;
import gentledog.members.members.address.infrastructure.AddressListRepository;
import gentledog.members.members.address.infrastructure.AddressRepository;
import gentledog.members.members.address.webdto.response.GetAddressResponseDto;
import gentledog.members.global.common.response.BaseResponseStatus;
import gentledog.members.global.common.exception.BaseException;
import gentledog.members.members.address.webdto.response.GetDefaultAddressResponseDto;
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
     * @param createAddressInDto
     */
    @Override
    public void createAddress(String membersEmail, CreateAddressInDto createAddressInDto) {
        Members members = membersRepository.findByMembersEmail(membersEmail)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_MEMBERS));

        /**
         * addressRegistrationRequestDto에서 true값이 들어오고 기존에 기존에 다른 default true값이 있다면 false로 변경 하고
         * address에 default true값을 넣어준다.
         */

        if (createAddressInDto.getDefaultAddress()) {
            AddressList addressList1 = addressListRepository.findByMembersIdAndDefaultAddress(members.getId(),
                    true);

            if (addressList1 != null) {
                addressList1.updateDefaultAddress(false);
            }

        }

        // addressRegistrationRequestDto를 address 엔터티로 매핑
        Address address = modelMapper.map(createAddressInDto, Address.class);
        addressRepository.save(address);

        // addresslist의 default값 변경
        AddressList addressList = AddressList.builder()
                .address(address)
                .members(members)
                .defaultAddress(createAddressInDto.getDefaultAddress())
                .build();

        addressListRepository.save(addressList);
    }

    /**
     * @param membersEmail
     * @return
     */
    @Override
    public List<GetAddressOutDto> getAddress(String membersEmail) {
        Members members = membersRepository.findByMembersEmail(membersEmail)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_MEMBERS));

        // 유저의 주소지 리스트를 조회
        List<AddressList> addressListList = addressListRepository.findByMembersId(members.getId());
        log.info("{}", addressListList);
        // 주소지 리스트를 AddressInfoResponse로 매핑
        return addressListList.stream().map(item -> {
            // 주소지 엔터티를 조회
            Address address = item.getAddress();

            // 주소지 엔터티를 AddressInfoResponse 매핑
            GetAddressOutDto getAddressOutDto = modelMapper.map(address, GetAddressOutDto.class);

            // 주소지 엔터티의 defaultAddress GetAddressOutDto 넣어준다.
            getAddressOutDto = getAddressOutDto.toBuilder()
                    .id(item.getAddress().getId())
                    .defaultAddress(item.getDefaultAddress())
                    .build();
            log.info("{}", getAddressOutDto);
            return getAddressOutDto;
        }).toList();

    }

    @Override
    public GetDefaultAddressOutDto getDefaultAddress(String membersEmail) {
        Members members = membersRepository.findByMembersEmail(membersEmail)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_MEMBERS));

        AddressList addressList = addressListRepository.findByMembersIdAndDefaultAddress(members.getId(), true);
        if (addressList != null) {
            Address address = addressList.getAddress();
            GetDefaultAddressOutDto getDefaultAddressOutDto = modelMapper.map(address, GetDefaultAddressOutDto.class);
            getDefaultAddressOutDto = getDefaultAddressOutDto.toBuilder()
                    .defaultAddress(addressList.getDefaultAddress())
                    .build();
            return getDefaultAddressOutDto;
        } else {
            return null;
        }
    }

    /**
     * @param addressId
     * @param createAddressInDto
     */
    @Override
    public void updateAddress(String membersEmail, Long addressId, CreateAddressInDto createAddressInDto) {
        Members members = membersRepository.findByMembersEmail(membersEmail)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_MEMBERS));

        // addressList에서 true값이 들어오고 기존에 기존에 다른 default true값이 있다면 false로 변경 하고
        // address에 default true값을 넣어준다.
        if (createAddressInDto.getDefaultAddress()) {
            AddressList addressList1 = addressListRepository.findByMembersIdAndDefaultAddress(members.getId(),
                    true);

            if (addressList1 != null) {
                addressList1.updateDefaultAddress(false);
            }
        }

        Address address = addressRepository.findById(addressId)
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_ADDRESS));
        address.updateAddress(createAddressInDto);

        AddressList addressList = addressListRepository.findByMembersIdAndAddressId(members.getId(), addressId);
        addressList.updateDefaultAddress(createAddressInDto.getDefaultAddress());

    }

    /**
     * @param membersEmail
     * @param updateDefaultAddressInDto
     */
    @Override
    public void updateDefaultAddress(String membersEmail, UpdateDefaultAddressInDto updateDefaultAddressInDto) {

        Members members = membersRepository.findByMembersEmail(membersEmail)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_MEMBERS));

        // 1. oldAddress로 addressList의 defaultAddress를 false로 변경
        if (updateDefaultAddressInDto.getOldDefaultAddressId() != null) {
            AddressList addressList = addressListRepository.findByMembersIdAndAddressId(members.getId(),
                    updateDefaultAddressInDto.getOldDefaultAddressId());
            addressList.updateDefaultAddress(false);
        }

        // 2. newAddress로 addressList의 defaultAddress를 true로 변경
        AddressList addressList = addressListRepository.findByMembersIdAndAddressId(members.getId(),
                updateDefaultAddressInDto.getNewDefaultAddressId());
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
