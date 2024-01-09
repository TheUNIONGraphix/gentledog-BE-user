package gentledog.members.members.dog.dto.in;

import lombok.Getter;

@Getter
public class UpdateDefaultDogInDto {

    private Long oldDefaultDogId;
    private Long newDefaultDogId;
}
