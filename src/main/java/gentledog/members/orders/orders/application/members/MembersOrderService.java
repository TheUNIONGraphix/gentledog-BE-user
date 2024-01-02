package gentledog.members.orders.orders.application.members;


import gentledog.members.orders.orders.dto.in.members.OrderRegisterRequestDto;
import gentledog.members.orders.orders.dto.out.members.OrdersSuccessResponseDto;
import gentledog.members.orders.orders.dto.out.members.VendorsOrderSearchOutResponseDto;

public interface MembersOrderService {

    // 주문 생성
    OrdersSuccessResponseDto registerOrders(String membersEmail, OrderRegisterRequestDto orderRegisterRequestDto);

    // 주문 요약 조회
    VendorsOrderSearchOutResponseDto getOrdersSummary(String membersEmail, Long groupId);

    // 주문 삭제
    void deleteOrder(String membersEmail, String orderNumber);
}
