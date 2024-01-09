package gentledog.members.orders.orders.webdto.response.members;

import com.fasterxml.jackson.annotation.JsonFormat;
import gentledog.members.orders.orders.entity.enums.VendorsOrderListStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GetOrdersSummaryResponseDto {

    private Long vendorsOrderId;
    private Long groupId;
    private String orderNumber;
    private String brandName;
    private String brandLogoImageUrl;
    private String productImageUrl;
    private String vendorEmail;
    private Integer totalPrice;
    private String productNameAndTotalCount;
    private VendorsOrderListStatus vendorsOrderListStatus;
    private String vendorsOrderListStatusDescription;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private List<GetOrdersDetailResponseDto> orderDetailList = new ArrayList<>();


}
