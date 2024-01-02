package gentledog.members.members.address.presentation;

import gentledog.members.members.address.application.AddressService;
import gentledog.members.members.address.dto.AddressDefaultUpdateRequestDto;
import gentledog.members.members.address.dto.AddressRegistrationRequestDto;
import gentledog.members.members.address.response.AddressInfoResponse;
import gentledog.members.global.common.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members/address")
@Slf4j
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    /**
     * 1. 배송지 등록
     * 2. 배송지 조회
     * 3. 배송지 수정
     * 4. 대표 배송지 변경
     * 5. 배송지 삭제
     */

    @Operation(summary = "배송지 등록", description = "배송지 등록", tags = { "Members Address" })
    @PostMapping("")
    public BaseResponse<?> addressRegister(@RequestHeader("membersEmail") String membersEmail,
                                           @RequestBody AddressRegistrationRequestDto addressRegistrationRequestDto) {
        addressService.registerAddress(membersEmail, addressRegistrationRequestDto);
        return new BaseResponse<>();
    }

    @Operation(summary = "배송지 조회", description = "배송지 조회", tags = { "Members Address" })
    @GetMapping("")
    public BaseResponse<List<AddressInfoResponse>> addressFind(@RequestHeader("membersEmail") String membersEmail) {
        List<AddressInfoResponse> addressInfoResponse = addressService.findAddress(membersEmail);
        return new BaseResponse<>(addressInfoResponse);
    }

    @Operation(summary = "배송지 수정", description = "배송지 수정", tags = { "Members Address" })
    @PutMapping("")
    public BaseResponse<?> addressUpdate(@RequestHeader("membersEmail") String membersEmail,
                                         @RequestParam("addressId") Long addressId,
                                         @RequestBody AddressRegistrationRequestDto addressRegistrationRequestDto) {
        addressService.updateAddress(membersEmail, addressId, addressRegistrationRequestDto);
        return new BaseResponse<>();
    }

    @Operation(summary = "대표 배송지 조회", description = "대표 배송지 조회", tags = { "Members Address" })
    @GetMapping("/default")
    public BaseResponse<AddressInfoResponse> addressFindDefault(@RequestHeader("membersEmail") String membersEmail) {
        AddressInfoResponse addressInfoResponse = addressService.findDefaultAddress(membersEmail);
        return new BaseResponse<>(addressInfoResponse);
    }

    @Operation(summary = "대표 배송지 변경", description = "대표 배송지 변경", tags = { "Members Address" })
    @PutMapping("/default")
    public BaseResponse<?> addressUpdateDefault(@RequestHeader("membersEmail") String membersEmail,
                                                @RequestBody AddressDefaultUpdateRequestDto
                                                        addressDefaultUpdateRequestDto) {
        addressService.updateDefaultAddress(membersEmail, addressDefaultUpdateRequestDto);
        return new BaseResponse<>();
    }

    @Operation(summary = "배송지 삭제", description = "배송지 삭제", tags = { "Members Address" })
    @DeleteMapping("")
    public BaseResponse<?> addressDelete(@RequestParam("addressId") Long addressId) {
        addressService.deleteAddress(addressId);
        return new BaseResponse<>();
    }
}
