package gentledog.members.orders.payment.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_payment", catalog = "orders")
public class ProductPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vendor_email")
    private String vendorEmail;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "product_main_image_url")
    private String productMainImageUrl;

    @Column(name = "product_amount")
    private Integer productAmount;

    @Column(name = "count")
    private Integer count;

    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Payment payment;


    // Payment 추가
    public void updatePayment(Payment payment) {
        this.payment = payment;
    }

}
