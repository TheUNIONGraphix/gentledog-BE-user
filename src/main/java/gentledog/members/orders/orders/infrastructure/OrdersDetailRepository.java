package gentledog.members.orders.orders.infrastructure;

import gentledog.members.orders.orders.entity.OrdersDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersDetailRepository extends JpaRepository<OrdersDetail, Long> {

    List<OrdersDetail> findByVendorsOrdersListIdOrderByCreatedAtDesc(Long vendorsOrdersListId);

}
