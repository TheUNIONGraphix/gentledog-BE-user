package gentledog.members.orders.orders.entity.converter;


import gentledog.members.global.common.enums.BaseEnumConverter;
import gentledog.members.orders.orders.entity.enums.OrderDetailStatus;

public class OrderDetailStatusConverter extends BaseEnumConverter<OrderDetailStatus, Integer, String> {

    public OrderDetailStatusConverter() {super(OrderDetailStatus.class);}
}
