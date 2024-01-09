package gentledog.members.members.address.presentation;

import gentledog.members.members.address.application.AddressService;
import gentledog.members.members.address.dto.in.CreateAddressInDto;
import gentledog.members.members.address.dto.in.UpdateDefaultAddressInDto;
import gentledog.members.members.address.dto.out.GetAddressOutDto;
import gentledog.members.members.address.dto.out.GetDefaultAddressOutDto;
import gentledog.members.members.address.webdto.request.UpdateDefaultAddressRequestDto;
import gentledog.members.members.address.webdto.request.CreateAddressRequestDto;
import gentledog.members.members.address.webdto.response.GetAddressResponseDto;
import gentledog.members.global.common.response.BaseResponse;
import gentledog.members.members.address.webdto.response.GetDefaultAddressResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members/address")
@Slf4j
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final ModelMapper modelMapper;

    /**
     * 1. 배송지 등록
     * 2. 배송지 조회
     * 3. 배송지 수정
     * 4. 대표 배송지 변경
     * 5. 배송지 삭제
     */

    @Operation(summary = "배송지 등록", description = "배송지 등록", tags = { "Members Address" })
    @PostMapping("")
    public BaseResponse<?> createAddress(@RequestHeader("membersEmail") String membersEmail,
                                         @RequestBody CreateAddressRequestDto createAddressRequestDto) {

        CreateAddressInDto createAddressInDto = modelMapper.map(createAddressRequestDto, CreateAddressInDto.class);
        addressService.createAddress(membersEmail, createAddressInDto);
        return new BaseResponse<>();
    }

    @Operation(summary = "배송지 조회", description = "배송지 조회", tags = { "Members Address" })
    @GetMapping("")
    public BaseResponse<List<GetAddressResponseDto>> getAddress(@RequestHeader("membersEmail") String membersEmail) {

        List<GetAddressOutDto> getAddressOutDtos = addressService.getAddress(membersEmail);

        // getAddressOutDtos 값들을 하나씩 꺼내서 GetAddressResponseDto로 변환
        List<GetAddressResponseDto> getAddressResponseDtos = getAddressOutDtos.stream()
                .map(item -> modelMapper.map(item, GetAddressResponseDto.class))
                .toList();

        return new BaseResponse<>(getAddressResponseDtos);

    }

    @Operation(summary = "배송지 수정", description = "배송지 수정", tags = { "Members Address" })
    @PutMapping("")
    public BaseResponse<?> updateAddress(@RequestHeader("membersEmail") String membersEmail,
                                         @RequestParam("addressId") Long addressId,
                                         @RequestBody CreateAddressRequestDto createAddressRequestDto) {

        CreateAddressInDto createAddressInDto = modelMapper.map(createAddressRequestDto, CreateAddressInDto.class);
        addressService.updateAddress(membersEmail, addressId, createAddressInDto);
        return new BaseResponse<>();
    }

    @Operation(summary = "대표 배송지 조회", description = "대표 배송지 조회", tags = { "Members Address" })
    @GetMapping("/default")
    public BaseResponse<GetDefaultAddressResponseDto> getDefaultAddress(@RequestHeader("membersEmail") String membersEmail) {

        GetDefaultAddressOutDto getDefaultAddressOutDto = addressService.getDefaultAddress(membersEmail);

        GetDefaultAddressResponseDto getDefaultAddressResponseDto = modelMapper.map(getDefaultAddressOutDto,
                GetDefaultAddressResponseDto.class);

        return new BaseResponse<>(getDefaultAddressResponseDto);
    }

    @Operation(summary = "대표 배송지 변경", description = "대표 배송지 변경", tags = { "Members Address" })
    @PutMapping("/default")
    public BaseResponse<?> updateDefaultAddress(@RequestHeader("membersEmail") String membersEmail,
                                                @RequestBody UpdateDefaultAddressRequestDto
                                                        updateDefaultAddressRequestDto) {

        UpdateDefaultAddressInDto updateDefaultAddressInDto = modelMapper.map(updateDefaultAddressRequestDto,
                UpdateDefaultAddressInDto.class);
        addressService.updateDefaultAddress(membersEmail, updateDefaultAddressInDto);
        return new BaseResponse<>();
    }

    @Operation(summary = "배송지 삭제", description = "배송지 진짜 삭제", tags = { "Members Address" })
    @DeleteMapping("")
    public BaseResponse<?> deleteAddress(@RequestParam("addressId") Long addressId) {
        addressService.deleteAddress(addressId);
        return new BaseResponse<>();
    }
}
