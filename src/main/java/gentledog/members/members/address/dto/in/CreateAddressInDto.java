package gentledog.members.members.address.dto.in;

import lombok.Getter;

@Getter
public class CreateAddressInDto {

    private String membersAddress;
    private String addressAlias;
    private String recipientPhoneNumber;
    private String recipientName;
    private String addressRequestMessage;
    private String entrancePassword;
    private Boolean defaultAddress;

}
