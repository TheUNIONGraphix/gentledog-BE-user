package gentledog.members.wish.cart.infrastructure;


import gentledog.members.wish.cart.entity.ProductInCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<ProductInCart, Long> {
    /**
     * cart
     * 1. 유저 이메일로 조회
     * 2. 중복 조회
     * 3. 유저 이메일과 checked로 조회
     */

    // 1. 유저 이메일로 조회
    List<ProductInCart> findBymembersEmail(String membersEmail);

    // 2. 중복 조회
    Boolean existsByProductDetailId(Long productDetailId);

    // 3. 유저 이메일과 checked로 조회
    List<ProductInCart> findBymembersEmailAndChecked(String membersEmail, Boolean checked);

}
