package gentledog.members.orders.orders.webdto.response.vendors;

import com.fasterxml.jackson.annotation.JsonFormat;
import gentledog.members.orders.orders.dto.out.members.GetOrdersDetailOutDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class VendorsOrderInfoOutWebDto {

    private Long VendorsOrderId;
    private String orderNumber;
    private String brandName;
    private String brandLogoImageUrl;
    private String vendorEmail;
    private Integer totalPrice;
    private String productNameAndTotalCount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @Builder.Default
    private List<GetOrdersDetailOutDto> orderDetailList = new ArrayList<>();
}
