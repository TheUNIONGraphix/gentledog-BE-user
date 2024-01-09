package gentledog.members.orders.orders.dto.in.members;

import gentledog.members.orders.orders.entity.enums.OrdersDetailStatus;
import lombok.Getter;

@Getter
public class CreateOrdersDetailInDto {

    private Long productId;
    private Long productDetailId;
    private String productName;
    private Integer productStock;
    private Integer productPrice;
    private String productSize;
    private String productColor;
    private OrdersDetailStatus productOrdersStatus;
    private Integer productDiscountRate;
    private String productImageUrl;
    private Long couponId;
    private Integer couponDiscountPrice;

}
