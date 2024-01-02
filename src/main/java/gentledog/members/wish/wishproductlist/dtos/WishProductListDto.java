package gentledog.members.wish.wishproductlist.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishProductListDto {
    private Long wishProductId;

    private String membersEmail;

    private Long productId;
}
