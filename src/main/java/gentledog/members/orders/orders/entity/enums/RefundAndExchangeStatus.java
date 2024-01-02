package gentledog.members.orders.orders.entity.enums;


import gentledog.members.global.common.enums.BaseEnum;
import gentledog.members.global.common.enums.BaseEnumConverter;
import jakarta.persistence.Converter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter // BaseEnum의 method를 impl하는것을 -> Getter로 대체
@AllArgsConstructor // 생성자를 직접 만들지 않고 -> AllArgs로 대체
public enum RefundAndExchangeStatus implements BaseEnum<Integer, String> {

    /**
     * 1. 코드 작성
     * 2. field 선언
     * 3. converter 구현
     */

    // 반품 또는 교환 주문 접수
    REFUND_READY(0, "반품 접수"),
    EXCHANGE_READY(1, "교환 접수"),
    // 상품 검수 중
    REFUND_IN_PROGRESS(2, "반품 처리 중"),
    EXCHANGE_IN_PROGRESS(3, "교환 처리 중"),
    // 반품 확인 후 환불 또는 교환 주문 완료 상태 (환불은 결제대행사를 통해 1~3일 내 환불)
    REFUND_DONE(4, "환불 완료"),
    EXCHANGE_DONE(5, "교환 완료"),
    // 반품 또는 교환 취소 상태(검수 실패 또는 유저의 취소 요청)
    REFUND_CANCELED(6, "교환 취소"),
    EXCHANGE_CANCEL(7, "환불 취소");

    private final Integer code;
    private final String description;

    @Converter(autoApply = true)
    static class thisConverter extends BaseEnumConverter<DeliveryStatus, Integer, String> {
        public thisConverter() {
            super(DeliveryStatus.class);
        }
    }

}

