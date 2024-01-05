package gentledog.members.orders.orders.webdto.in.members;

import gentledog.members.orders.orders.dto.in.members.DeliveryOrdersInRequestDto;
import gentledog.members.orders.orders.dto.in.members.VendorsOrderListInRequestDto;
import lombok.Getter;

import java.util.List;

@Getter
public class OrdersRegisterInWebDto {
    private DeliveryOrdersInRequestDto deliveryOrdersInRequestDto;
    private List<VendorsOrderListInRequestDto> vendorsOrderListInRequestDto;

}
