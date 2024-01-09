package gentledog.members.orders.orders.presentation;

import gentledog.members.global.common.response.BaseResponse;
import gentledog.members.orders.orders.application.members.MembersOrdersService;
import gentledog.members.orders.orders.dto.in.members.CreateOrdersInDto;
import gentledog.members.orders.orders.webdto.request.members.CreateOrdersRequestDto;
import gentledog.members.orders.orders.webdto.response.members.GetOrdersNumberResponseDto;
import gentledog.members.orders.orders.webdto.response.members.GetVendorsOrdersResponseDto;
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

    private final MembersOrdersService membersOrdersService;
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
    public BaseResponse<GetOrdersNumberResponseDto> createOrder(@RequestHeader("membersEmail") String membersEmail,
                                                           @RequestBody
                                                              CreateOrdersRequestDto createOrdersRequestDto) {

        log.info("createOrdersRequestDto : {}", createOrdersRequestDto.getDeliveryOrders().getDeliveryRequestMessage());
        CreateOrdersInDto createOrdersInDto = modelMapper.map(createOrdersRequestDto, CreateOrdersInDto.class);
        log.info("createOrdersInDto : {}", createOrdersInDto.getDeliveryOrders().getDeliveryRequestMessage());


        GetOrdersNumberResponseDto getOrdersNumberResponseDto = modelMapper.map(
                membersOrdersService.createOrders(membersEmail, createOrdersInDto), GetOrdersNumberResponseDto.class);
        return new BaseResponse<>(getOrdersNumberResponseDto);
    }

    @Operation(summary = "유저 주문 조회", description = "유저 주문 무한스크롤 조회", tags = { "Orders members" })
    @GetMapping("")
    public BaseResponse<GetVendorsOrdersResponseDto> getOrdersSummary(@RequestHeader("membersEmail") String membersEmail,
                                                                      @RequestParam(value = "groupId", required = false)
                                                                           Long groupId
    ) {

       GetVendorsOrdersResponseDto getVendorsOrdersResponseDtos = modelMapper.map(
               membersOrdersService.getOrdersSummary(membersEmail, groupId), GetVendorsOrdersResponseDto.class);

        return new BaseResponse<>(getVendorsOrdersResponseDtos);
    }

    @Operation(summary = "유저 주문 삭제", description = "유저 주문 삭제", tags = { "Orders members" })
    @PutMapping("/{ordersNumber}")
    public BaseResponse<Void> deleteOrders(@RequestHeader("membersEmail") String membersEmail,
                                           @PathVariable("ordersNumber") String ordersNumber) {

        membersOrdersService.deleteOrder(membersEmail, ordersNumber);
        return new BaseResponse<>();
    }
}
