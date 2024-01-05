package gentledog.members.members.dog.presentation;

import gentledog.members.members.dog.application.DogService;
import gentledog.members.members.dog.dto.DogDefaultUpdateRequestDto;
import gentledog.members.members.dog.dto.DogRegistrationRequestDto;
import gentledog.members.members.dog.dto.DogSignUpRegistrationRequestDto;
import gentledog.members.members.dog.dto.DogUpdateRequestDto;
import gentledog.members.members.dog.response.DogBreedInfoResponse;
import gentledog.members.members.dog.response.DogIdInfoResponse;
import gentledog.members.members.dog.response.DogInfoResponse;
import gentledog.members.global.common.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members/dog")
@Slf4j
@RequiredArgsConstructor
public class DogController {

    private final DogService dogService;

    /**
     * 1. 유저 회원가입 시 반려견 등록
     * 2. 유저 반려견 등록
     * 3. 반려견 전체 품종 조회
     * 4. 반려견 정보 조회
     * 5. 반려견 정보 수정
     * 6. 대표 반려견 변경
     * 7. 반려견 정보 삭제
     */

    @Operation(summary = "로그인 이전 반려견 등록", description = "로그인 이후 필터 필요없는 반려견 등록", tags = { "Members Dog" })
    @PostMapping("/signup")
    public BaseResponse<?> dogSignUpRegister(@RequestBody DogSignUpRegistrationRequestDto dogSignUpRegistrationRequestDto) {
        dogService.signUpRegisterDog(dogSignUpRegistrationRequestDto);
        return new BaseResponse<>();
    }

    @Operation(summary = "로그인 이후 반려견 등록", description = "로그인 이후 필터 필요한 반려견 등록", tags = { "Members Dog" })
    @PostMapping("")
    public BaseResponse<?> dogRegister(@RequestHeader("membersEmail") String membersEmail,
                                       @RequestBody DogRegistrationRequestDto dogRegistrationRequestDto) {
        dogService.registerDog(membersEmail, dogRegistrationRequestDto);
        return new BaseResponse<>();
    }

    @Operation(summary = "반려견 품종 조회", description = "반려견 품종 조회", tags = { "Members Dog" })
    @GetMapping("/breeds")
    public BaseResponse<List<DogBreedInfoResponse>> dogBreedInfo() {
        List<DogBreedInfoResponse> dogBreeds = dogService.getDogBreedInfo();
        return new BaseResponse<>(dogBreeds);
    }

    @Operation(summary = "모든 반려견 정보 조회", description = "모든 반려견 정보 조회", tags = { "Members Dog" })
    @GetMapping("")
    public BaseResponse<List<DogInfoResponse>> dogInfo(@RequestHeader("membersEmail") String membersEmail) {
        List<DogInfoResponse> dogInfoResponse = dogService.getDogInfo(membersEmail);
        log.info("dogInfoResponse : " + dogInfoResponse);
        return new BaseResponse<>(dogInfoResponse);
    }

    @Operation(summary = "id로 특정 반려견 정보 조회", description = "id로 특정 반려견 정보 조회", tags = { "Members Dog" })
    @GetMapping("/{dogId}")
    public BaseResponse<DogInfoResponse> dogInfoById(@RequestHeader("membersEmail") String membersEmail,
                                                     @PathVariable("dogId") Long dogId) {
        DogInfoResponse dogInfoResponse = dogService.getDogInfo(membersEmail, dogId);
        return new BaseResponse<>(dogInfoResponse);
    }

    @Operation(summary = "반려견 영어 이름으로 모든 dogId 조회", description = "반려견 영어 이름으로 모든 dogId 조회", tags = { "Members Dog" })
    @GetMapping("/breeds/eng-name/{breedEngName}")
    public BaseResponse<List<Long>> dogBreedInfoByEngName(@PathVariable("breedEngName") String breedEngName) {
        List<Long> dogIdInfoResponse = dogService.getDogBreedInfoByEngName(breedEngName);
        return new BaseResponse<>(dogIdInfoResponse);
    }

    @Operation(summary = "반려견 정보 수정", description = "반려견 정보 수정", tags = { "Members Dog" })
    @PutMapping("")
    public BaseResponse<?> dogUpdate(@RequestParam("dogListId") Long dogListId,
                                     @RequestBody DogUpdateRequestDto dogRegisterRequestDto) {
        dogService.updateDog(dogListId, dogRegisterRequestDto);
        return new BaseResponse<>();
    }

    @Operation(summary = "대표 반려견 변경", description = "현재 대표 반려견 false로 변경하고 새로운 대표 반려견 true 변경",
            tags = { "Members Dog" })
    @PutMapping("/default")
    public BaseResponse<DogIdInfoResponse> dogRepresentativeUpdate(@RequestHeader("membersEmail") String membersEmail,
                                                                   @RequestBody DogDefaultUpdateRequestDto dogDefaultUpdateRequestDto) {
        DogIdInfoResponse dogIdInfoResponse = dogService.updateRepresentativeDog(membersEmail, dogDefaultUpdateRequestDto);
        return new BaseResponse<>(dogIdInfoResponse);
    }

    @Operation(summary = "반려견 정보 삭제", description = "반려견 정보 삭제", tags = { "Members Dog" })
    @DeleteMapping("")
    public BaseResponse<?> dogDelete(@RequestParam("dogId") Long dogId) {
        dogService.deleteDog(dogId);
        return new BaseResponse<>();
    }

}
