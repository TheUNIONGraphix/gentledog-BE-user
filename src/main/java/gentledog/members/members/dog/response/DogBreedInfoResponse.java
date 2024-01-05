package gentledog.members.members.dog.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DogBreedInfoResponse {

    private Long id;
    private String dogBreedKorName;
    private String dogBreedEngName;

}
