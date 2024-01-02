package gentledog.members.orders.orders.entity;

import gentledog.members.orders.orders.entity.converter.RefundAndExchangeStatusConverter;
import gentledog.members.orders.orders.entity.enums.RefundAndExchangeStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "refund_and_exchange", catalog = "orders")
public class RefundAndExchange {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "refund_and_exchange_stock", nullable = false)
    private Integer refundAndExchangeStock;

    @Convert(converter = RefundAndExchangeStatusConverter.class)
    @Column(name = "refund_and_exchange_status", columnDefinition = "tinyint", nullable = false)
    private RefundAndExchangeStatus refundAndExchangeStatus;

    @Column(name = "refund_and_exchange_reason", length = 255, nullable = false)
    private String refundAndExchangeReason;

    @Column(name = "refund_and_exchange_type", columnDefinition = "tinyint(1)", nullable = false)
    private Integer refundAndExchangeType;

    @Column(name = "orders_detail_id", nullable = false)
    private Long ordersDetailId;

}
