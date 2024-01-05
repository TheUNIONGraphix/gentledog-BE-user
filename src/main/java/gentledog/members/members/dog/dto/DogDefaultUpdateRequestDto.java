package gentledog.members.members.dog.dto;

import lombok.Getter;

@Getter
public class DogDefaultUpdateRequestDto {

    private Long oldDefaultDogId;
    private Long newDefaultDogId;
}
