package gentledog.members.orders.payment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "cancels", catalog = "orders")
public class Cancels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", referencedColumnName = "id", nullable = false)
    private Payment payment;

    @Column(name = "canceled_at", nullable = false)
    private LocalDateTime canceledAt;

    @Column(name = "cancel_amount", nullable = false)
    private Integer cancelAmount;

    @Column(name = "transaction_key", length = 64, nullable = false)
    private String transactionKey;

    @Column(name = "receipt_key", length = 200, nullable = false)
    private String receiptKey;


}
