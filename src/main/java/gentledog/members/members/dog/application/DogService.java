package gentledog.members.members.dog.application;

import gentledog.members.members.dog.dto.in.CreateDogInDto;
import gentledog.members.members.dog.dto.in.CreateSignUpPreviousDogInDto;
import gentledog.members.members.dog.dto.out.GetDogBreedOutDto;
import gentledog.members.members.dog.dto.out.GetDogListOutDto;
import gentledog.members.members.dog.dto.out.GetDogOutDto;
import gentledog.members.members.dog.webdto.request.UpdateDefaultDogRequestDto;
import gentledog.members.members.dog.webdto.request.UpdateDogRequestDto;
import gentledog.members.members.dog.webdto.response.GetDogIdResponseDto;
import gentledog.members.members.dog.webdto.response.GetDogResponseDto;

import java.util.List;

public interface DogService {

    void signUpRegisterDog(CreateSignUpPreviousDogInDto createSignUpPreviousDogInDto);
    void registerDog(String membersEmail, CreateDogInDto createDogInDto);
    List<GetDogBreedOutDto> getDogBreedList();
    List<GetDogListOutDto> getDogList(String membersEmail);
    GetDogOutDto getDogById(String membersEmail, Long dogId);
    List<Long> getDogBreedInfoByEngName(String engName);
    void updateDog(Long dogListId, UpdateDogRequestDto dogUpdateRequestDto);
    GetDogIdResponseDto updateRepresentativeDog(String membersEmail, UpdateDefaultDogRequestDto updateDefaultDogRequestDto);
    void deleteDog(Long dogId);
}
