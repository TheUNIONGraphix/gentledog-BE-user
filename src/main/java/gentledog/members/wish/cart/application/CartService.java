package gentledog.members.wish.cart.application;


import gentledog.members.wish.cart.dtos.in.AddProductInDto;
import gentledog.members.wish.cart.dtos.in.UpdateCheckedInDto;
import gentledog.members.wish.cart.dtos.out.GetCartOutDto;
import gentledog.members.wish.cart.dtos.out.GetCheckedCartOutDto;

public interface CartService {

    /**
     * Cart
     * 1. 장바구니에 상품 추가
     * 2. 장바구니 조회
     * 3. 체크 선택/취소
     * 4. 장바구니 상품 삭제
     * 5. 장바구니 상품 수량 변경
     * 6. 장바구니 선택한 물품만 조회
     */

    // 1. 장바구니에 상품 추가
    void addProduct(AddProductInDto inDto, String membersEmail);

    // 2. 장바구니 조회
    GetCartOutDto getCart(String membersEmail);

    // 3. 체크 선택/취소
    void updateChecked(UpdateCheckedInDto inDto);

    // 4. 장바구니 상품 삭제
    void deleteProduct(Long productInCartId);

    // 5. 장바구니 상품 수량 변경
    void updateProductCount(Long productInCartId, Integer count);

    // 6. 장바구니 선택한 물품만 조회
    GetCheckedCartOutDto getCheckedCart(String membersEmail);
}
