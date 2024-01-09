package gentledog.members.members.address.webdto.request;

import lombok.Getter;

@Getter
public class UpdateDefaultAddressRequestDto {

    private Long oldDefaultAddressId;
    private Long newDefaultAddressId;

}
