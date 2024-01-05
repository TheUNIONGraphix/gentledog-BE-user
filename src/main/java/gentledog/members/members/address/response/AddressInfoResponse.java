package gentledog.members.members.address.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class AddressInfoResponse {

        private Long addressId;
        private String membersAddress;
        private String addressAlias;
        private String recipientPhoneNumber;
        private String recipientName;
        private String addressRequestMessage;
        private String entrancePassword;
        private Boolean defaultAddress;

}
