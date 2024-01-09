package gentledog.members.orders.orders.dto.out.members;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrdersDetailOutDto {

    private Long productId;
    private String productName;
    private String productImageUrl;
    private Integer productPrice;
    private String productSize;
    private String productColor;
    private Integer productStock;
    private Integer productDiscountRate;
    private Long couponId;
    private Integer couponDiscountPrice;

}
