package gentledog.members.members.dog.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetDogBreedOutDto {

    private Long id;
    private String dogBreedKorName;
    private String dogBreedEngName;

}
