package gentledog.members.orders.orders.dto.in.members;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateOrdersInDto {
    private CreateDeliveryOrdersInDto deliveryOrders;
    private List<CreateVendorsOrdersListInDto> vendorsOrdersList;

}