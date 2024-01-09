package gentledog.members.members.address.webdto.request;

import lombok.Getter;

@Getter
public class CreateAddressRequestDto {

    private String membersAddress;
    private String addressAlias;
    private String recipientPhoneNumber;
    private String recipientName;
    private String addressRequestMessage;
    private String entrancePassword;
    private Boolean defaultAddress;

}
