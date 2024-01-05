package gentledog.members.orders.payment.webdto;

import gentledog.members.orders.payment.dto.ProductPaymentDto;
import gentledog.members.orders.payment.entity.enums.PaymentMethod;
import gentledog.members.orders.payment.entity.enums.PaymentStatus;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
public class PaymentInWebDto {
    private String paymentKey;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private Integer paymentTotalAmount;

    private Boolean isPartial;

    private String receipt_url;

    private Integer balanceAmount;

    private Integer usedPoint;

    // String을 LocalDateTime으로 변환해서 받아옴
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime requestedAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime approvedAt;

    private List<ProductPaymentDto> productPaymentList;
}
