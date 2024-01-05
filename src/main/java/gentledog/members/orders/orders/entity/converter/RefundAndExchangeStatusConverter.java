package gentledog.members.orders.orders.entity.converter;


import gentledog.members.global.common.enums.BaseEnumConverter;
import gentledog.members.orders.orders.entity.enums.RefundAndExchangeStatus;

public class RefundAndExchangeStatusConverter extends BaseEnumConverter<RefundAndExchangeStatus, Integer, String> {

        public RefundAndExchangeStatusConverter() {
            super(RefundAndExchangeStatus.class);
        }

}
