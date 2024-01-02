package gentledog.members.orders.orders.infrastructure;

import gentledog.members.orders.orders.dto.out.members.VendorsOrderSummaryOutResponseDto;

import java.util.List;

public interface VendorsOrderListRepositoryCustom {

    List<VendorsOrderSummaryOutResponseDto> findByFilter(String membersEmail, Long groupId);
}
