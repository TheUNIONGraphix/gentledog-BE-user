package gentledog.members.orders.orders.infrastructure;

import gentledog.members.orders.orders.dto.out.members.GetOrdersSummaryOutDto;

import java.util.List;

public interface VendorsOrdersListRepositoryCustom {

    List<GetOrdersSummaryOutDto> findByFilter(String membersEmail, Long groupId);
}
