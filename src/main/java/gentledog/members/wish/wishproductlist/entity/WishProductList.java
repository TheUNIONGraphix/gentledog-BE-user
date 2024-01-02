package gentledog.members.wish.wishproductlist.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wish_product_list")
public class WishProductList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishProductListId;

    private String membersEmail;

    private Long productId;
}
