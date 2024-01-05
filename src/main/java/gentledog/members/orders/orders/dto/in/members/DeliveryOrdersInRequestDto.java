package gentledog.members.orders.orders.dto.in.members;

import lombok.Getter;

@Getter
public class DeliveryOrdersInRequestDto {

    private String recipientName;
    private String recipientAddress;
    private String recipientPhoneNumber;
    private String entrancePassword;
    private String deliveryRequestMessage;

}
