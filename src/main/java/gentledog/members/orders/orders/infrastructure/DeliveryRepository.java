package gentledog.members.orders.orders.infrastructure;

import gentledog.members.orders.orders.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
