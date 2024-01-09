package gentledog.members.orders.orders.entity;

import gentledog.members.global.common.entity.BaseTimeEntity;
import gentledog.members.orders.orders.entity.converter.VendorsOrderListStatusConverter;
import gentledog.members.orders.orders.entity.enums.VendorsOrderListStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "vendors_orders_list", catalog = "orders")
public class VendorsOrdersList extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number", length = 20, nullable = false)
    private String orderNumber;

    @Column(name = "vendor_email", nullable = false)
    private String vendorEmail;

    @Column(name = "brand_name", length = 100, nullable = false)
    private String brandName;

    @Column(name = "brand_logo_image_url", length = 100)
    private String brandLogoImageUrl;

    @Convert(converter = VendorsOrderListStatusConverter.class)
    @Column(name = "vendors_order_list_status", columnDefinition = "tinyint", nullable = false)
    private VendorsOrderListStatus vendorsOrderListStatus;

    @Column(name = "members_email", length = 320)
    private String membersEmail;

    @Column(name = "members_name", length = 20, nullable = false)
    private String membersName;

    @Column(name = "members_phone_number", length = 20, nullable = false)
    private String membersPhoneNumber;

    @Column(name = "orders_request_message", length = 100)
    private String ordersRequestMessage;

    @Column(name = "dog_id")
    private Long dogId;

    @Column(name = "delivery_fee", nullable = false)
    private Integer deliveryFee;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Column(name = "delete_status", columnDefinition = "tinyint(1)", nullable = false)
    private Integer orderDeleteStatus;

    @Column(name = "group_id")
    private Long groupId;

    @Builder.Default
    @OneToMany(mappedBy = "vendorsOrdersList", cascade = CascadeType.ALL)
    private List<OrdersDetail> ordersDetailList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "delivery_orders_id", referencedColumnName = "id")
    private Delivery delivery;


    // 주문 내역 삭제
    public void updateOrderDeleteStatus() {
        this.orderDeleteStatus = 0;
    }
}
