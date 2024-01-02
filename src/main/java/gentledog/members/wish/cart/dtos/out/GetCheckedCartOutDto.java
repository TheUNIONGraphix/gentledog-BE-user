package gentledog.members.wish.cart.dtos.out;

import gentledog.members.wish.cart.dtos.ProductDto;
import lombok.Getter;

import java.util.List;
import java.util.TreeMap;

@Getter
public class GetCheckedCartOutDto {
    // 브랜드별로 정렬된 List
    private TreeMap<String, List<ProductDto>> checkedCart = new TreeMap<>();

    private Integer totalCount;

    public GetCheckedCartOutDto(TreeMap<String, List<ProductDto>> cart, Integer totalCount) {
        this.checkedCart = cart;
        this.totalCount = totalCount;
    }
}
