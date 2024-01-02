package gentledog.members.orders.orders.dto.in.members;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderRegisterRequestDto {
    private DeliveryOrdersInRequestDto deliveryOrdersInRequestDto;
    private List<VendorsOrderListInRequestDto> vendorsOrderListInRequestDto;

}