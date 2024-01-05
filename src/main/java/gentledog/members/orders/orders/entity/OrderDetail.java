package gentledog.members.orders.orders.entity;

import gentledog.members.global.common.entity.BaseTimeEntity;
import gentledog.members.orders.orders.entity.converter.OrderDetailStatusConverter;
import gentledog.members.orders.orders.entity.enums.OrderDetailStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_detail", catalog = "orders")
public class OrderDetail extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vendors_order_list_id", referencedColumnName = "id")
    private VendorsOrderList vendorsOrderList;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "product_detail_id", nullable = false)
    private Long productDetailId;

    @Column(name = "product_name", length = 100, nullable = false)
    private String productName;

    @Column(name = "product_stock", nullable = false)
    private Integer productStock;

    @Column(name = "product_price", nullable = false)
    private Integer productPrice;

    @Column(name = "product_size", length = 20, nullable = false)
    private String productSize;

    @Column(name = "product_color", length = 20, nullable = false)
    private String productColor;

    @Convert(converter = OrderDetailStatusConverter.class)
    @Column(name = "product_order_status", columnDefinition = "tinyint", nullable = false)
    private OrderDetailStatus productOrderStatus;

    @Column(name = "product_discount_rate", nullable = false)
    private Integer productDiscountRate;

    @Column(name = "product_image_url", length = 100, nullable = false)
    private String productImageUrl;

    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "coupon_discount_price")
    private Integer couponDiscountPrice;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refundAndExchange_id", referencedColumnName = "id")
    private RefundAndExchange refundAndExchange;

    // 상품 주문 상태 변경(주문 접수, 주문 완료, 주문 취소, 구매 확정, 교환, 배송)
    public void updateProductOrdersStatus(OrderDetailStatus productOrderStatus) {
        this.productOrderStatus = productOrderStatus;
    }

    // 상품 VendorsOrderList 변경
    public void updateVendorsOrderList(VendorsOrderList vendorsOrderList) {
        this.vendorsOrderList = vendorsOrderList;
    }

}
