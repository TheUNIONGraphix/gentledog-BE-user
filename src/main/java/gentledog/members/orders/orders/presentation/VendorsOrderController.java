package gentledog.members.orders.orders.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/orders/vendor")
@RequiredArgsConstructor
@Slf4j
public class VendorsOrderController {

    private final ModelMapper modelMapper;

//    @Operation(summary = "판매자 주문 조회", description = "판매자 주문 조회", tags = { "Orders Vendor" })
//    @GetMapping("")
//    public BaseResponse<> getOrdersSummary(@RequestHeader("vendorEmail") String vendorEmail) {
//
//
//    }

}
