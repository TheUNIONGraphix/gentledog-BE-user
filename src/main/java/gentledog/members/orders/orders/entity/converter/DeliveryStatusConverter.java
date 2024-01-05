package gentledog.members.orders.orders.entity.converter;


import gentledog.members.global.common.enums.BaseEnumConverter;
import gentledog.members.orders.orders.entity.enums.DeliveryStatus;

public class DeliveryStatusConverter extends BaseEnumConverter<DeliveryStatus, Integer, String> {

    public DeliveryStatusConverter() {
        super(DeliveryStatus.class);
    }
}
