package gentledog.members.members.dog.dto;

import lombok.Getter;

@Getter
public class DogRegistrationRequestDto {

    private String dogName;
    private Integer dogAge;
    private Integer dogGender;
    private Integer dogWeight;
    private String dogImageUrl;
    private String dogFurColor;
    private Integer dogBodyLength;
    private Integer dogBreastGirth;
    private Integer dogNeckGirth;
    private Integer dogLegLength;
    private Boolean defaultDog;
    private Long dogBreed;

}
