package gentledog.members.orders.orders.dto.out.members;

import com.fasterxml.jackson.annotation.JsonFormat;
import gentledog.members.orders.orders.entity.enums.VendorsOrderListStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class VendorsOrderSummaryOutResponseDto {

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
    @Builder.Default
    private List<OrderDetailOutResponseDto> orderDetailList = new ArrayList<>();

    // vendorsOrderListStatusDescription 필드를 업데이트합니다.
    public void updateVendorsOrderListStatusDescription() {
        this.vendorsOrderListStatusDescription = this.vendorsOrderListStatus.getDescription();
    }

    // OrderDetailOutResponseDto에서 첫 번째 값 productImageUrl를 넣어준다.
    public void updateProductImageUrl() {
        this.productImageUrl = orderDetailList.get(0).getProductImageUrl();
    }

    // orderDetailList에서 첫 상품명과 List<OrderDetail>에서의 상품 수량을 합쳐서 productNameAndTotalCount 필드를 업데이트합니다.
    public void updateProductNameAndTotalCount() {
        this.productNameAndTotalCount =
                orderDetailList.get(0).getProductName() + " 외 " + (orderDetailList.size() - 1) + "개";
    }

}
