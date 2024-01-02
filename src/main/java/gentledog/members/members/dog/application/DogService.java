package gentledog.members.members.dog.application;

import gentledog.members.members.dog.dto.DogDefaultUpdateRequestDto;
import gentledog.members.members.dog.dto.DogRegistrationRequestDto;
import gentledog.members.members.dog.dto.DogSignUpRegistrationRequestDto;
import gentledog.members.members.dog.dto.DogUpdateRequestDto;
import gentledog.members.members.dog.response.DogBreedInfoResponse;
import gentledog.members.members.dog.response.DogIdInfoResponse;
import gentledog.members.members.dog.response.DogInfoResponse;

import java.util.List;

public interface DogService {

    void signUpRegisterDog(DogSignUpRegistrationRequestDto dogSignUpRegistrationRequestDto);
    void registerDog(String membersEmail, DogRegistrationRequestDto dogRegistrationRequestDto);
    List<DogBreedInfoResponse> getDogBreedInfo();
    DogInfoResponse getDogInfo(String membersEmail, Long dogId);
    List<DogInfoResponse> getDogInfo(String membersEmail);
    List<Long> getDogBreedInfoByEngName(String engName);
    void updateDog(Long dogListId, DogUpdateRequestDto dogUpdateRequestDto);
    DogIdInfoResponse updateRepresentativeDog(String membersEmail, DogDefaultUpdateRequestDto dogDefaultUpdateRequestDto);
    void deleteDog(Long dogId);
}
