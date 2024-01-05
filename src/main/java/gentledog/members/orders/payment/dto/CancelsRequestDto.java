package gentledog.members.orders.payment.dto;


import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CancelsRequestDto {
    private Integer cancelAmount;
    private String transactionKey;
    private String receiptKey;
    private String paymentKey;
    private LocalDateTime canceledAt;
}
