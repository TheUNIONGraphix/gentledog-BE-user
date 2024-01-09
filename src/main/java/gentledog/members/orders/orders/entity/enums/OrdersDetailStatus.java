package gentledog.members.orders.orders.entity.enums;

import gentledog.members.global.common.enums.BaseEnum;
import gentledog.members.global.common.enums.BaseEnumConverter;
import jakarta.persistence.Converter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter // BaseEnum의 method를 impl하는것을 -> Getter로 대체
@AllArgsConstructor // 생성자를 직접 만들지 않고 -> AllArgs로 대체
public enum OrdersDetailStatus implements BaseEnum<Integer, String> {

    /**
     * 1. 코드 작성
     * 2. field 선언
     * 3. converter 구현
     */

    // 1. 코드 작성
    // 일반, 교환, 환불
    ORDERS(1, "일반"),
    EXCHANGE(2, "교환"),
    REFUND(3, "환불");

    // 2. field 선언
    private final Integer code;
    private final String description;

    // 3. converter 구현
    @Converter(autoApply = true)
    static class thisConverter extends BaseEnumConverter<DeliveryStatus, Integer, String> {
        public thisConverter() {
            super(DeliveryStatus.class);
        }
    }
}
