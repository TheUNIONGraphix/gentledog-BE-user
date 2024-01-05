package gentledog.members.orders.orders.infrastructure;

import gentledog.members.orders.orders.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findByVendorsOrderListIdOrderByCreatedAtDesc(Long vendorsOrderListId);

}
