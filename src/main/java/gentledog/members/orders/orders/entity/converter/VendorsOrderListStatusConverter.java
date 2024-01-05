package gentledog.members.orders.orders.entity.converter;

import gentledog.members.global.common.enums.BaseEnumConverter;
import gentledog.members.orders.orders.entity.enums.VendorsOrderListStatus;

public class VendorsOrderListStatusConverter extends BaseEnumConverter<VendorsOrderListStatus, Integer, String> {
    public VendorsOrderListStatusConverter() {
        super(VendorsOrderListStatus.class);
    }
}
