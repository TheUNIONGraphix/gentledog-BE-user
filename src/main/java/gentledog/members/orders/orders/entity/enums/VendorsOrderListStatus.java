package gentledog.members.orders.orders.entity.enums;

import gentledog.members.global.common.enums.BaseEnum;
import gentledog.members.global.common.enums.BaseEnumConverter;
import jakarta.persistence.Converter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter // BaseEnum의 method를 impl하는것을 -> Getter로 대체
@AllArgsConstructor // 생성자를 직접 만들지 않고 -> AllArgs로 대체
public enum VendorsOrderListStatus implements BaseEnum<Integer, String> {

    /**
     * 1. 코드 작성
     * 2. field 선언
     * 3. converter 구현
     */

    // 1. 코드 작성
    // 주문 취소 (출고 처리 되기 전 까지 가능)
    CANCELED(0, "주문 취소"),
    // 주문 접수 (입금 확인 시)
    READY(1, "주문 접수"),
    // 출고 처리 중 (배송지 변경 불가)
    IN_PROGRESS(2, "출고 처리 중"),
    PACKED(3, "출고 완료"),
    FINISHED(4, "구매 확정");

    // 2. field 선언
    private final Integer code;
    private final String description;

    // 3. converter 구현
    @Converter(autoApply = true)
    static class thisConverter extends BaseEnumConverter<VendorsOrderListStatus, Integer, String> {
        public thisConverter() {
            super(VendorsOrderListStatus.class);
        }
    }
}
