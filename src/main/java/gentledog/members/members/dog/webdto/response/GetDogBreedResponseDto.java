package gentledog.members.members.dog.webdto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetDogBreedResponseDto {

    private Long id;
    private String dogBreedKorName;
    private String dogBreedEngName;

}
