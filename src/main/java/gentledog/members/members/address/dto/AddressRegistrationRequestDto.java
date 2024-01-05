package gentledog.members.members.address.dto;

import lombok.Getter;

@Getter
public class AddressRegistrationRequestDto {

    private String membersAddress;
    private String addressAlias;
    private String recipientPhoneNumber;
    private String recipientName;
    private String addressRequestMessage;
    private String entrancePassword;
    private Boolean defaultAddress;

}
