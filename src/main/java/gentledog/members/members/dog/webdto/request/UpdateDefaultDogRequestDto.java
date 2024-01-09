package gentledog.members.members.dog.webdto.request;

import lombok.Getter;

@Getter
public class UpdateDefaultDogRequestDto {

    private Long oldDefaultDogId;
    private Long newDefaultDogId;
}
