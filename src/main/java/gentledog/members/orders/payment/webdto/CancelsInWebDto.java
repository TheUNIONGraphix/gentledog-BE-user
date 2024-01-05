package gentledog.members.orders.payment.webdto;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class CancelsInWebDto {
    private Integer cancelAmount;
    private String transactionKey;
    private String receiptKey;
    private String paymentKey;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime canceledAt;
}
