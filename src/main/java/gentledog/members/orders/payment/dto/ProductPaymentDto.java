package gentledog.members.orders.payment.dto;

import gentledog.members.orders.payment.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductPaymentDto {
    private String vendorEmail;

    private String productName;

    private String productCode;

    private String productMainImageUrl;

    private Integer productAmount;

    private Integer count;

    private Payment payment;

    public void updatePayment(Payment payment) {
        this.payment = payment;
    }
}
