package gentledog.members.orders.payment.entity.enums;

import gentledog.members.global.common.enums.BaseEnum;
import gentledog.members.global.common.enums.BaseEnumConverter;
import jakarta.persistence.Converter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter // BaseEnum의 method를 impl하는것을 -> Getter로 대체
@AllArgsConstructor // 생성자를 직접 만들지 않고 -> AllArgs로 대체
public enum PaymentStatus implements BaseEnum<Integer, String> {
    /**
     * 1. 코드 작성
     * 2. field 선언
     * 3. converter 구현
     */

    // 1. 코드 작성
    READY(0, "인증 전"),
    IN_PROGRESS(1, "결제수단 소유자 인증완료"),
    DONE(2, "결제 완료"),
    CANCELED(3, "승인결제 취소"),
    PARTIAL_CANCELED(4, "승인결제 부분취소"),
    ABORTED(5, "결제승인 실패"),
    EXPIRED(6, "결제 유효시간 만료")
    ;

    // 2. field 선언
    private final Integer code;
    private final String description;

    // 3. converter 구현
    @Converter(autoApply = true)
    static class thisConverter extends BaseEnumConverter<PaymentStatus, Integer, String> {
        public thisConverter() {
            super(PaymentStatus.class);
        }
    }
}
