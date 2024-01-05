package gentledog.members.wish.cart.dtos.out;

import gentledog.members.wish.cart.dtos.ProductDto;
import lombok.Getter;

import java.util.List;
import java.util.TreeMap;

@Getter
public class GetCartOutDto {
    // 브랜드별로 정렬된 List
    private TreeMap<String, List<ProductDto>> cart = new TreeMap<>();

    public GetCartOutDto(TreeMap<String, List<ProductDto>> cart) {
        this.cart = cart;
    }
}
