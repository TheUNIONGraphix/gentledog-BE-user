package gentledog.members.orders.orders.entity.enums;

import gentledog.members.global.common.enums.BaseEnum;
import gentledog.members.global.common.enums.BaseEnumConverter;
import jakarta.persistence.Converter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter // BaseEnum의 method를 impl하는것을 -> Getter로 대체
@AllArgsConstructor // 생성자를 직접 만들지 않고 -> AllArgs로 대체
public enum DeliveryStatus implements BaseEnum<Integer, String> {

    /**
     * 1. 코드 작성
     * 2. field 선언
     * 3. converter 구현
     */

    // 1. 코드 작성
    // 상품 상태가 PACKED(3) 출고 완료 되는 동시에 배송 접수 상태로 변경
    CANCELED(0, "배송 취소"),
    READY(1, "배송 접수"),
    IN_PROGRESS(2, "배송 중"),
    DONE(3, "배송 완료");

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

