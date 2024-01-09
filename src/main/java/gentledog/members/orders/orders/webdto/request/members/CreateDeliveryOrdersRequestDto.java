package gentledog.members.orders.orders.webdto.request.members;

import lombok.Getter;

@Getter
public class CreateDeliveryOrdersRequestDto {

    private String recipientName;
    private String recipientAddress;
    private String recipientPhoneNumber;
    private String entrancePassword;
    private String deliveryRequestMessage;

}
