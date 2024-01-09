package gentledog.members.members.address.dto.in;

import lombok.Getter;

@Getter
public class UpdateDefaultAddressInDto {

    private Long oldDefaultAddressId;
    private Long newDefaultAddressId;

}
