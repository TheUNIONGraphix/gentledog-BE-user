package gentledog.members.orders.orders.presentation;

import gentledog.members.global.common.response.BaseResponse;
import gentledog.members.orders.orders.application.members.MembersOrderService;
import gentledog.members.orders.orders.dto.in.members.OrderRegisterRequestDto;
import gentledog.members.orders.orders.dto.out.members.OrdersSuccessResponseDto;
import gentledog.members.orders.orders.webdto.in.members.OrdersRegisterInWebDto;
import gentledog.members.orders.orders.webdto.out.members.VendorsOrderInfoOutWebDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders/members")
@RequiredArgsConstructor
@Slf4j
public class MembersOrderController {

    private final MembersOrderService membersOrderService;
    private final ModelMapper modelMapper;
    /**
     * 1. 주문 생성
     * 2. 주문 조회
     *
     * @return
     */

    // 1. 주문 생성

    @Operation(summary = "주문 생성", description = "주문 생성", tags = { "Orders members" })
    @PostMapping("")
    public BaseResponse<OrdersSuccessResponseDto> createOrder(@RequestHeader("membersEmail") String membersEmail,
                                                              @RequestBody OrdersRegisterInWebDto ordersRegisterInWebDto) {
        log.info("ordersRegisterInWebDto : {}", ordersRegisterInWebDto.getVendorsOrderListInRequestDto().get(0).getMembersName());
        OrderRegisterRequestDto orderRegisterRequestDto = modelMapper.map(ordersRegisterInWebDto,
                OrderRegisterRequestDto.class);
        OrdersSuccessResponseDto ordersSuccessResponseDto =  membersOrderService.registerOrders(membersEmail, orderRegisterRequestDto);
        return new BaseResponse<>(ordersSuccessResponseDto);
    }

    @Operation(summary = "유저 주문 조회", description = "유저 주문 무한스크롤 조회", tags = { "Orders members" })
    @GetMapping("")
    public BaseResponse<VendorsOrderInfoOutWebDto> getOrdersSummary(@RequestHeader("membersEmail") String membersEmail,
                                                                    @RequestParam(value = "groupId", required = false)
                                                                           Long groupId
    ) {

       VendorsOrderInfoOutWebDto vendorsOrderInfoOutWebDtos = modelMapper.map(
               membersOrderService.getOrdersSummary(membersEmail, groupId), VendorsOrderInfoOutWebDto.class);

        return new BaseResponse<>(vendorsOrderInfoOutWebDtos);
    }

    @Operation(summary = "유저 주문 삭제", description = "유저 주문 삭제", tags = { "Orders members" })
    @PutMapping("/{orderNumber}")
    public BaseResponse<Void> deleteOrder(@RequestHeader("membersEmail") String membersEmail,
                                          @PathVariable("orderNumber") String orderNumber) {

        membersOrderService.deleteOrder(membersEmail, orderNumber);
        return new BaseResponse<>();
    }
}
