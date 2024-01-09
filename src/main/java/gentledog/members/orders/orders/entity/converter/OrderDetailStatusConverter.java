package gentledog.members.orders.orders.entity.converter;


import gentledog.members.global.common.enums.BaseEnumConverter;
import gentledog.members.orders.orders.entity.enums.OrdersDetailStatus;

public class OrderDetailStatusConverter extends BaseEnumConverter<OrdersDetailStatus, Integer, String> {

    public OrderDetailStatusConverter() {super(OrdersDetailStatus.class);}
}
