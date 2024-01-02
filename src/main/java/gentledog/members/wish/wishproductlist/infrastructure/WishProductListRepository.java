package gentledog.members.wish.wishproductlist.infrastructure;

import gentledog.members.wish.wishproductlist.entity.WishProductList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishProductListRepository extends JpaRepository<WishProductList, Long> {
    /**
     * 1. membersEmail로 찜한 상품 리스트 조회
     * 2. membersEmail과 ProductId로 찜한상품 단건 조회
     * 3. 찜한상품 삭제
     * 4. membersEmail과 ProductId로 중복여부 조회
     */

    List<WishProductList> findAllBymembersEmail(String membersEmail);

    Optional<WishProductList> findBymembersEmailAndProductId(String membersEmail, Long productId);

    void deleteByWishProductListId(Long id);

    Boolean existsBymembersEmailAndProductId(String membersEmail, Long productId);


}
