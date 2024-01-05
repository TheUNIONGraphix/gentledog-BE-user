package gentledog.members.orders.payment.infrastructure;

import gentledog.members.orders.payment.entity.ProductPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPaymentRepository extends JpaRepository<ProductPayment, Long> {
}
