package gentledog.members.wish.wishproductlist.dtos.out;

import gentledog.members.wish.wishproductlist.entity.WishProductList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetWishProductOutDto {
    List<WishProductList> wishList;
}
