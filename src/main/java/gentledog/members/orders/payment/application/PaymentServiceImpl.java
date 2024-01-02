package gentledog.members.orders.payment.application;

import gentledog.members.orders.payment.dto.CancelsRequestDto;
import gentledog.members.orders.payment.dto.PaymentRequestDto;
import gentledog.members.orders.payment.entity.Cancels;
import gentledog.members.orders.payment.entity.Payment;
import gentledog.members.orders.payment.entity.enums.PaymentStatus;
import gentledog.members.orders.payment.infrastructure.*;
import gentledog.members.global.common.exception.BaseException;
import gentledog.members.global.common.response.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;
    private final CancelsRepository cancelsRepository;
    private final ProductPaymentRepository productPaymentRepository;
    private final ModelMapper modelMapper;

    /**
     * payment
     *
     * 1. 결제 요청
     * 2. 결제 키로 조회
     * 3. 결제 취소
     */

    // 1. 결제 요청
    @Override
    public void paymentRequest(PaymentRequestDto requestDto) {
        // 조회 결과가 존재한다면 중복된다는 의미이므로, ALREADY_PAID_ORDER_ID EXCEPTION을 return
        Payment searchResult = getPaymentByKey(requestDto.getPaymentKey());
        if (!(searchResult == null)) {
            throw new BaseException(BaseResponseStatus.ALREADY_PAID_ORDER_ID);
        }

        // modelMapper를 사용하여 entity 생성
        Payment payment = modelMapper.map(requestDto, Payment.class);
        // 각 상품에 Payment를 추가
        payment.getProductPaymentList().forEach(
                product -> product.updatePayment(payment));
        // payment와 productPayment를 같이 저장한다
        paymentRepository.save(payment);
    }

    // 2. 결제 키로 조회
    @Override
    @Transactional(readOnly = true)
    public Payment getPaymentByKey(String paymentKey) {
        Optional<Payment> payment = paymentRepository.findByPaymentKey(paymentKey);
        // paymentKey로 조회
        if (payment.isPresent()) {
            return payment.get();
        }
        // 존재하지 않는다면 null을 return
        return null;
    }

    // 3. 결제 취소
    @Override
    public void paymentCancel(CancelsRequestDto requestDto) {
        // paymentKey로 기존 결제를 조회 및 상태 업데이트
        Payment payment = getPaymentByKey(requestDto.getPaymentKey());
        // 조회가 존재하지 않는다면, 에러를 return
        if (payment == null) {
            throw new BaseException(BaseResponseStatus.DOSE_NOT_EXIST_PAYMENT);
        }
        // 기존 payment 내역을 update (취소 가능 잔고->결제취소 금액만큼 제외, 결제 상태-> 결제취소로 변경)
        payment.updateBalanceAmount(requestDto.getCancelAmount());
        payment.updateStatus(PaymentStatus.CANCELED);

        // 취소 객체 생성
        Cancels cancels = modelMapper.map(requestDto, Cancels.class);
        cancels = cancels.toBuilder().payment(payment).build();
        // 저장
        cancelsRepository.save(cancels);
    }
}
