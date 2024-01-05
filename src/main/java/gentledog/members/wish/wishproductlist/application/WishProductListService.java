package gentledog.members.wish.wishproductlist.application;


import gentledog.members.wish.wishproductlist.dtos.out.GetWishProductOutDto;

public interface WishProductListService {
    /**
     * 1. 상품 찜하기/찜취소하기
     * 2. 찜한 상품 조회하기
     * 3. 해당 상품 찜 확인
     */

    // 1. 상품 찜하기/찜취소하기
    Boolean pickProduct(String membersEmail, Long productId);

    // 2. 찜한 상품 조회하기
    GetWishProductOutDto getWishProductList(String membersEmail);

    // 3. 해당 상품 찜 확인
    Boolean isWish(String membersEmail, Long productId);
}
