package gentledog.members.orders.payment.infrastructure;

import gentledog.members.orders.payment.entity.Cancels;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CancelsRepository extends JpaRepository<Cancels, Long> {
}
