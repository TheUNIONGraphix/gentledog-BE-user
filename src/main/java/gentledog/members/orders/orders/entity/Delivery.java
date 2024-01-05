package gentledog.members.orders.orders.entity;

import gentledog.members.global.common.entity.BaseTimeEntity;
import gentledog.members.orders.orders.entity.converter.DeliveryStatusConverter;
import gentledog.members.orders.orders.entity.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "delivery", catalog = "orders")
public class Delivery extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tracking_number", length = 20)
    private String trackingNumber;

    @Convert(converter = DeliveryStatusConverter.class)
    @Column(name = "delivery_status", columnDefinition = "tinyint")
    private DeliveryStatus deliveryStatus;

    @Column(name = "recipient_name", length = 20, nullable = false)
    private String recipientName;

    @Column(name = "recipient_address", length = 100, nullable = false)
    private String recipientAddress;

    @Column(name = "recipient_phone_number", length = 20, nullable = false)
    private String recipientPhoneNumber;

    @Column(name = "entrance_password", length = 20)
    private String entrancePassword;

    @Column(name = "delivery_request_message", length = 100)
    private String deliveryRequestMessage;


    // 배송 상태 변경
    public void updateDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
