package gentledog.members.members.address.dto;

import lombok.Getter;

@Getter
public class AddressDefaultUpdateRequestDto {

    private Long oldDefaultAddressId;
    private Long newDefaultAddressId;

}
