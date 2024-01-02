package gentledog.members.orders.members.infrastructure;

import gentledog.members.orders.members.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}
