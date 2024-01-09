package gentledog.members.orders.orders.webdto.response.members;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrdersDetailResponseDto {

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
