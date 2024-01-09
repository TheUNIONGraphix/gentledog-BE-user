package gentledog.members.orders.orders.application.members;


import gentledog.members.orders.orders.dto.in.members.CreateOrdersInDto;
import gentledog.members.orders.orders.dto.out.members.GetOrdersNumberOutDto;
import gentledog.members.orders.orders.dto.out.members.GetVendorsOrdersOutDto;

public interface MembersOrdersService {

    // 주문 생성
    GetOrdersNumberOutDto createOrders(String membersEmail, CreateOrdersInDto createOrdersInDto);

    // 주문 요약 조회
    GetVendorsOrdersOutDto getOrdersSummary(String membersEmail, Long groupId);

    // 주문 삭제
    void deleteOrder(String membersEmail, String orderNumber);
}
