package gentledog.members.orders.orders.dto.in.members;

import gentledog.members.orders.orders.entity.enums.OrderDetailStatus;
import lombok.Getter;

@Getter
public class OrderDetailInRequestDto {

    private Long productId;
    private Long productDetailId;
    private String productName;
    private Integer productStock;
    private Integer productPrice;
    private String productSize;
    private String productColor;
    private OrderDetailStatus productOrderStatus;
    private Integer productDiscountRate;
    private String productImageUrl;
    private Long couponId;
    private Integer couponDiscountPrice;

}
