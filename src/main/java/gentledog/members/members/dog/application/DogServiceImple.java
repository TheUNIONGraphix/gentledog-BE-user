package gentledog.members.members.dog.application;

import gentledog.members.members.dog.dto.DogDefaultUpdateRequestDto;
import gentledog.members.members.dog.dto.DogRegistrationRequestDto;
import gentledog.members.members.dog.dto.DogSignUpRegistrationRequestDto;
import gentledog.members.members.dog.dto.DogUpdateRequestDto;
import gentledog.members.members.dog.entity.Dog;
import gentledog.members.members.dog.entity.DogBreed;
import gentledog.members.members.dog.entity.DogList;
import gentledog.members.members.dog.infrastructure.DogBreedRepository;
import gentledog.members.members.dog.infrastructure.DogListRepository;
import gentledog.members.members.dog.infrastructure.DogRepository;
import gentledog.members.members.dog.response.DogBreedInfoResponse;
import gentledog.members.members.dog.response.DogIdInfoResponse;
import gentledog.members.members.dog.response.DogInfoResponse;
import gentledog.members.global.common.response.BaseResponseStatus;
import gentledog.members.global.common.exception.BaseException;
import gentledog.members.members.members.entity.Members;
import gentledog.members.members.members.infrastructure.MembersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DogServiceImple implements DogService {

    /**
     * 1. 유저 회원가입 시 반려견 등록
     * 2. 유저 반려견 등록
     * 3. 반려견 전체 품종 조회
     * 4. id로 반려견 정보 조회
     * 5. 전체 반려견 정보 조회
     * 6. 반려견 영어 이름으로 품종 조회
     * 7. 반려견 정보 수정
     * 8. 대표 반려견 변경
     * 9. 반려견 정보 삭제
     */

    private final MembersRepository membersRepository;
    private final DogRepository dogRepository;
    private final DogListRepository dogListRepository;
    private final DogBreedRepository dogBreedRepository;
    private final ModelMapper modelMapper;

    @Override
    public void signUpRegisterDog(DogSignUpRegistrationRequestDto dogSignUpRegistrationRequestDto) {
        Members members = membersRepository.findByMembersEmail(dogSignUpRegistrationRequestDto.getMembersEmail())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_MEMBERS));
        // 첫 회원가입 반려견은 true로 설정
        Dog dog = modelMapper.map(dogSignUpRegistrationRequestDto, Dog.class);

        // dogbreedId로 dogBreed 조회 및 dog에 저장
        DogBreed dogBreed = dogBreedRepository.findById(dogSignUpRegistrationRequestDto.getDogBreed())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_DOG_BREED));
        dog.setDogBreed(dogBreed);
        dogRepository.save(dog);

        // dogList의 default값 변경
        DogList dogList = DogList.builder()
                .dog(dog)
                .members(members)
                .defaultDog(true)
                .build();

        dogListRepository.save(dogList);
    }

    /**
     * 유저 반려견 등록
     * @param dogRegistrationRequestDto
     */
    @Override
    public void registerDog(String membersEmail, DogRegistrationRequestDto dogRegistrationRequestDto) {
        Members members = membersRepository.findByMembersEmail(membersEmail)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_MEMBERS));

        Dog dog = modelMapper.map(dogRegistrationRequestDto, Dog.class);

        // dogbreedId로 dogBreed 조회 및 dog에 저장
        DogBreed dogBreed = dogBreedRepository.findById(dogRegistrationRequestDto.getDogBreed())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_DOG_BREED));
        dog.setDogBreed(dogBreed);
        dogRepository.save(dog);

        // dogRegistrationRequestDto getDefaultDog가 true이고 기존에 defaultDog가 true인 값이 있다면 false로 변경
        if (dogRegistrationRequestDto.getDefaultDog()) {
            Optional<DogList> dogList = dogListRepository.findByMembersIdAndDefaultDog(members.getId(), true);
            dogList.ifPresent(list -> list.updateDefaultDog(false));
        }

        // dogList의 default값 변경
        DogList dogList = DogList.builder()
                .dog(dog)
                .members(members)
                .defaultDog(dogRegistrationRequestDto.getDefaultDog())
                .build();

        dogListRepository.save(dogList);
    }

    /**
     * 반려견 전체 품종 조회
     *
     * @return
     */
    @Override
    public List<DogBreedInfoResponse> getDogBreedInfo() {
        log.info("dogBreedRepository.findAll() : {}", dogBreedRepository.findAll());
        return dogBreedRepository.findAll()
                .stream()
                .map(dogBreed -> modelMapper.map(dogBreed, DogBreedInfoResponse.class))
                .toList();
    }

    /**
     * id로 반려견 정보 조회
     * @param membersEmail
     * @param dogId
     * @return
     */
    @Override
    public DogInfoResponse getDogInfo(String membersEmail, Long dogId) {
        Dog dog = dogRepository.findById(dogId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_DOG));

        DogInfoResponse dogInfoResponse = modelMapper.map(dog, DogInfoResponse.class);
        return dogInfoResponse.toBuilder()
                .dogBreedKorName(dog.getDogBreed().getDogBreedKorName())
                .build();

    }

    /**
     * 전체 반려견 정보 조회
     * @param membersEmail
     * @return
     */
    @Override
    public List<DogInfoResponse> getDogInfo(String membersEmail) {
        Members members = membersRepository.findByMembersEmail(membersEmail)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_MEMBERS));
        // 유저의 반려견 리스트 조회
        List<DogList> dogList = dogListRepository.findByMembersId(members.getId());

        // DogList 엔터티 리스트를 스트림으로 변환
        return dogList.stream()
                .map(item -> {
                    Dog myDog = item.getDog(); // DogList 엔터티에서 Dog 객체를 가져옴
                    // dogInfoResponse값 넣어줌
                    DogInfoResponse dogInfoResponse = modelMapper.map(myDog, DogInfoResponse.class);
                    // dogbreedname값 넣어줌
                    dogInfoResponse = dogInfoResponse.toBuilder()
                            .dogBreedKorName(myDog.getDogBreed().getDogBreedKorName())
                            .defaultDog(item.getDefaultDog())
                            .build();
                    return dogInfoResponse;
                })
                .toList();

    }

    /**
     * 반려견 영어 이름으로 품종 조회
     * @param engName
     * @return
     */
    @Override
    public List<Long> getDogBreedInfoByEngName(String engName) {
        DogBreed dogBreed = dogBreedRepository.findByDogBreedEngName(engName)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_DOG_BREED));

        List<Dog> dogList = dogRepository.findAllByDogBreedId(dogBreed.getId());
        log.info("dogList : {}", dogList);
        // dog 리스트에서 dogId만 추출해서 하나의 리스트 만듬
        if (dogList.isEmpty()) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_DOG_BREED_INFO);
        } else {
            List<Long> dogIdList = new ArrayList<>();
            for (Dog dog : dogList) {
                dogIdList.add(dog.getId());
            }
            return dogIdList;
        }
    }

    /**
     * 반려견 정보 수정
     * @param dogId
     * @param dogUpdateRequestDto
     */
    @Override
    public void updateDog(Long dogId, DogUpdateRequestDto dogUpdateRequestDto) {

        Dog dog = dogRepository.findById(dogId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_DOG));
        dog.updateDog(dogUpdateRequestDto);

    }

    /**
     * 대표 반려견 변경
     * @param membersEmail
     * @param dogDefaultUpdateRequestDto
     * @return
     */
    @Override
    public DogIdInfoResponse updateRepresentativeDog(String membersEmail, DogDefaultUpdateRequestDto dogDefaultUpdateRequestDto) {

        Members members = membersRepository.findByMembersEmail(membersEmail)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_MEMBERS));

        // 1. null이 아니라면 기존 대표 반려견 정보 조회 하고 false로 변경
        if (dogDefaultUpdateRequestDto.getOldDefaultDogId() != null) {
            DogList dogList = dogListRepository.findByMembersIdAndDogId(members.getId(),
                    dogDefaultUpdateRequestDto.getOldDefaultDogId());
            dogList.updateDefaultDog(false);
        }

        // 2. newDogId로 dogList의 defaultDog를 true로 변경
        DogList dogList = dogListRepository.findByDogId(dogDefaultUpdateRequestDto.getNewDefaultDogId());
        dogList.updateDefaultDog(true);

        // 3. dogList에서 대표 반려견 이미지를 전달해준다.
        Dog dog = dogList.getDog();
        return DogIdInfoResponse.builder()
                .DogId(dog.getId())
                .build();
    }

    /**
     * 반려견 정보 삭제
     * @param dogId
     */
    @Override
    public void deleteDog(Long dogId) {

        /**
         * DogList에서 Foreign Key로 dogId를 참조하고 있기 때문에 dogId를 참조하는 DogList를 먼저 삭제해야 한다.
         * 그리고 DogList를 삭제하면 DogList의 Foreign Key로 dogId를 참조하고 있기 때문에 dogId를 참조하는 Dog를 삭제해야 한다.
         * 만일, Dog를 먼저 삭제하면 DogList의 Foreign Key로 dogId를 참조하고 있기 때문에 DogList를 삭제할 수 없다.
         */
        DogList dogList = dogListRepository.findByDogId(dogId);
        dogListRepository.delete(dogList);

        Dog dog = dogRepository.findById(dogId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_DOG));
        dogRepository.delete(dog);

    }
}
