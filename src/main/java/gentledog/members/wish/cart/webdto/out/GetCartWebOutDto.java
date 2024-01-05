package gentledog.members.wish.cart.webdto.out;

import gentledog.members.wish.cart.dtos.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.TreeMap;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCartWebOutDto {
    // 브랜드별로 정렬된 List
    @Builder.Default
    private TreeMap<String, List<ProductDto>> cart = new TreeMap<>();
}
