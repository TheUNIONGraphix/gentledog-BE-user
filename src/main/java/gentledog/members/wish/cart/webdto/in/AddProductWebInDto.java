package gentledog.members.wish.cart.webdto.in;

import lombok.Getter;

@Getter
public class AddProductWebInDto {
    private Long productDetailId;

    private Integer count;

    private String brandName;
}
