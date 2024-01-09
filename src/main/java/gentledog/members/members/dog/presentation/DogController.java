package gentledog.members.members.dog.presentation;

import gentledog.members.members.dog.application.DogService;
import gentledog.members.members.dog.dto.in.CreateDogInDto;
import gentledog.members.members.dog.dto.in.CreateSignUpPreviousDogInDto;
import gentledog.members.members.dog.dto.out.GetDogBreedOutDto;
import gentledog.members.members.dog.dto.out.GetDogListOutDto;
import gentledog.members.members.dog.dto.out.GetDogOutDto;
import gentledog.members.members.dog.webdto.request.UpdateDefaultDogRequestDto;
import gentledog.members.members.dog.webdto.request.CreateDogRequestDto;
import gentledog.members.members.dog.webdto.request.CreateSignUpPreviousDogRequestDto;
import gentledog.members.members.dog.webdto.request.UpdateDogRequestDto;
import gentledog.members.members.dog.webdto.response.GetDogBreedResponseDto;
import gentledog.members.members.dog.webdto.response.GetDogIdResponseDto;
import gentledog.members.members.dog.webdto.response.GetDogListResponseDto;
import gentledog.members.members.dog.webdto.response.GetDogResponseDto;
import gentledog.members.global.common.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members/dog")
@Slf4j
@RequiredArgsConstructor
public class DogController {

    private final DogService dogService;
    private final ModelMapper modelMapper;

    /**
     * 1. 유저 회원가입 시 반려견 등록
     * 2. 유저 반려견 등록
     * 3. 반려견 전체 품종 조회
     * 4. 반려견 정보 조회
     * 5. 반려견 정보 수정
     * 6. 대표 반려견 변경
     * 7. 반려견 정보 삭제
     */

    @Operation(summary = "회원가입 이전 반려견 등록", description = "로그인 이후 반려견 등록", tags = { "Members Dog" })
    @PostMapping("/signup")
    public BaseResponse<?> createSigUpPreviousDog(@RequestBody CreateSignUpPreviousDogRequestDto
                                                              createSignUpPreviousDogRequestDto) {

        CreateSignUpPreviousDogInDto createSignUpPreviousDogInDto = modelMapper.map(createSignUpPreviousDogRequestDto,
                CreateSignUpPreviousDogInDto.class);

        dogService.signUpRegisterDog(createSignUpPreviousDogInDto);
        return new BaseResponse<>();
    }

    @Operation(summary = "반려견 등록", description = "회원가입 이후 반려견 등록", tags = { "Members Dog" })
    @PostMapping("")
    public BaseResponse<?> createDog(@RequestHeader("membersEmail") String membersEmail,
                                     @RequestBody CreateDogRequestDto createDogRequestDto) {

        CreateDogInDto createDogInDto = modelMapper.map(createDogRequestDto, CreateDogInDto.class);

        dogService.registerDog(membersEmail, createDogInDto);
        return new BaseResponse<>();
    }

    @Operation(summary = "반려견 전체 품종 조회", description = "반려견 전체 품종 조회", tags = { "Members Dog" })
    @GetMapping("/breeds")
    public BaseResponse<List<GetDogBreedResponseDto>> getDogBreedList() {

        List<GetDogBreedOutDto> dogBreeds = dogService.getDogBreedList();

        List<GetDogBreedResponseDto> dogBreedResponseDtos = dogBreeds.stream()
                .map(item -> modelMapper.map(item, GetDogBreedResponseDto.class))
                .toList();

        return new BaseResponse<>(dogBreedResponseDtos);
    }

    @Operation(summary = "모든 반려견 정보 조회", description = "모든 반려견 정보 조회", tags = { "Members Dog" })
    @GetMapping("")
    public BaseResponse<List<GetDogListResponseDto>> getDogList(@RequestHeader("membersEmail") String membersEmail) {

        List<GetDogListOutDto> getDogListOutDtos = dogService.getDogList(membersEmail);

        List<GetDogListResponseDto> getDogListResponseDtos = getDogListOutDtos.stream()
                .map(item -> modelMapper.map(item, GetDogListResponseDto.class))
                .toList();

        return new BaseResponse<>(getDogListResponseDtos);
    }

    @Operation(summary = "id로 특정 반려견 정보 조회", description = "id로 특정 반려견 정보 조회", tags = { "Members Dog" })
    @GetMapping("/{dogId}")
    public BaseResponse<GetDogResponseDto> getDogById(@RequestHeader("membersEmail") String membersEmail,
                                                      @PathVariable("dogId") Long dogId) {

        GetDogResponseDto getDogResponseDto = modelMapper.map(
                dogService.getDogById(membersEmail, dogId), GetDogResponseDto.class);

        return new BaseResponse<>(getDogResponseDto);
    }

    @Operation(summary = "반려견 영어 이름으로 모든 dogId 조회", description = "반려견 영어 이름으로 모든 dogId 조회", tags = { "Members Dog" })
    @GetMapping("/breeds/eng-name/{breedEngName}")
    public BaseResponse<List<Long>> getDogBreedByEngNameList(@PathVariable("breedEngName") String breedEngName) {

        List<Long> dogIdInfoResponse = dogService.getDogBreedInfoByEngName(breedEngName);
        return new BaseResponse<>(dogIdInfoResponse);
    }

    @Operation(summary = "반려견 정보 수정", description = "반려견 정보 수정", tags = { "Members Dog" })
    @PutMapping("")
    public BaseResponse<?> updateDog(@RequestParam("dogId") Long dogId,
                                     @RequestBody UpdateDogRequestDto dogRegisterRequestDto) {
        dogService.updateDog(dogId, dogRegisterRequestDto);
        return new BaseResponse<>();
    }

    @Operation(summary = "대표 반려견 변경", description = "현재 대표 반려견 false로 변경하고 새로운 대표 반려견 true 변경",
            tags = { "Members Dog" })
    @PutMapping("/default")
    public BaseResponse<GetDogIdResponseDto> updateRepresentativeDog(@RequestHeader("membersEmail") String membersEmail,
                                                                     @RequestBody UpdateDefaultDogRequestDto updateDefaultDogRequestDto) {
        GetDogIdResponseDto getDogIdResponseDto = dogService.updateRepresentativeDog(membersEmail, updateDefaultDogRequestDto);
        return new BaseResponse<>(getDogIdResponseDto);
    }

    @Operation(summary = "반려견 정보 삭제", description = "반려견 정보 삭제", tags = { "Members Dog" })
    @DeleteMapping("")
    public BaseResponse<?> deleteDog(@RequestParam("dogId") Long dogId) {
        dogService.deleteDog(dogId);
        return new BaseResponse<>();
    }

}
