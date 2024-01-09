package gentledog.members.orders.orders.webdto.request.members;

import gentledog.members.orders.orders.dto.in.members.CreateDeliveryOrdersInDto;
import gentledog.members.orders.orders.dto.in.members.CreateVendorsOrdersListInDto;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateOrdersRequestDto {
    private CreateDeliveryOrdersRequestDto deliveryOrders;
    private List<CreateVendorsOrdersListRequestDto> vendorsOrdersList;

}
