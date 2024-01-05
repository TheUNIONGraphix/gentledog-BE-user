package gentledog.members.orders.payment.entity;

import gentledog.members.global.common.exception.BaseException;
import gentledog.members.global.common.response.BaseResponseStatus;
import gentledog.members.orders.payment.entity.enums.PaymentMethod;
import gentledog.members.orders.payment.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "payment", catalog = "orders")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_key", length = 200, nullable = false)
    private String paymentKey;

    @Column(name = "payment_method", length = 1, nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "payment_status", columnDefinition = "tinyint", length = 10, nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "payment_total_amount", nullable = false)
    private Integer paymentTotalAmount;

    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;

    @Column(name = "approved_at", nullable = false)
    private LocalDateTime approvedAt;

    @Column(name = "is_partial", columnDefinition = "tinyint(1)", nullable = false)
    private Boolean isPartial;

    @Column(name = "receipt_url", length = 100, nullable = false)
    private String receipt_url;

    @Column(name = "balance_amount", nullable = false)
    private Integer balanceAmount;

    @Column(name = "used_point", columnDefinition = "int default 0")
    private Integer usedPoint;

    @OneToMany(mappedBy = "payment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductPayment> productPaymentList;


    /**
     * Payment
     * 1. 결제상태 업데이트
     * 2. 취소가능 잔고 업데이트
     * 3. 상품 결제내역 업데이트
     */

    // 1. 결제상태 업데이트
    public void updateStatus(PaymentStatus status) {
        this.paymentStatus = status;
    }

    // 2. 취소 가능 잔고 업데이트
    public void updateBalanceAmount(Integer cancledAmount) {
        if (this.balanceAmount >= cancledAmount) {
            this.balanceAmount = this.balanceAmount - cancledAmount;
        } else {
            throw new BaseException(BaseResponseStatus.CANCELED_AMOUNT_EXCEEDED);
        }
    }
}
