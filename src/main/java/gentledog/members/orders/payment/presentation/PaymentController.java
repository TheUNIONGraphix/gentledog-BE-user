package gentledog.members.orders.payment.presentation;

import gentledog.members.orders.payment.application.PaymentServiceImpl;
import gentledog.members.orders.payment.dto.CancelsRequestDto;
import gentledog.members.orders.payment.dto.PaymentRequestDto;
import gentledog.members.orders.payment.webdto.CancelsInWebDto;
import gentledog.members.orders.payment.webdto.PaymentInWebDto;
import gentledog.members.global.common.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentServiceImpl paymentService;
    private final ModelMapper modelMapper;

    /**
     * payment
     * 1. 결제
     * 2. 결제 취소
     * 3. 상품별 결제
     */

    //1. 결제
    @Operation(summary = "결제", description = "결제내역 저장", tags = {"Orders Payment"})
    @PostMapping("")
    public BaseResponse<?> requestPayment(@RequestBody PaymentInWebDto inDto) {
        PaymentRequestDto requestDto = modelMapper.map(inDto, PaymentRequestDto.class);
        paymentService.paymentRequest(requestDto);
        return new BaseResponse<>();
    }

    //2. 결제 취소
    @Operation(summary = "결제 취소", description = "결제 취소", tags = {"Orders Payment"})
    @PostMapping("/cancel")
    public BaseResponse<?> cancelPayment(@RequestBody CancelsInWebDto inDto) {
        CancelsRequestDto requestDto = modelMapper.map(inDto, CancelsRequestDto.class);
        paymentService.paymentCancel(requestDto);
        return new BaseResponse<>();
    }
}
