package gentledog.members.wish.wishproductlist.presentation;

import gentledog.members.wish.wishproductlist.application.WishProductListService;
import gentledog.members.wish.wishproductlist.dtos.out.GetWishProductOutDto;
import gentledog.members.wish.wishproductlist.webdto.in.PickProductWebInDto;
import gentledog.members.wish.wishproductlist.webdto.out.GetWishProductWetOutDto;
import gentledog.members.global.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wish/wishproductlist")
@RequiredArgsConstructor
public class WishProductListController {

    private final WishProductListService wishProductListService;
    private final ModelMapper modelMapper;

    /**
     * 1. 상품 찜하기/찜취소하기
     * 2. 찜한 상품 조회하기
     * 3. 해당 상품 찜 확인
     */

    // 1. 상품 찜하기/찜취소하기
    @PostMapping("")
    public BaseResponse<?> pickProduct(@RequestHeader String membersEmail, @RequestBody PickProductWebInDto webInDto) {
        Boolean status = wishProductListService.pickProduct(membersEmail, webInDto.getProductId());
        return new BaseResponse<>(status);
    }

    // 2. 찜한 상품 조회하기
    @GetMapping("/all")
    public BaseResponse<?> getWishProductList(@RequestHeader String membersEmail) {
        GetWishProductOutDto outDto = wishProductListService.getWishProductList(membersEmail);
        GetWishProductWetOutDto wetOutDto = modelMapper.map(outDto, GetWishProductWetOutDto.class);
        return new BaseResponse<>(wetOutDto);
    }


    // 3. 해당 상품 찜 확인
    @GetMapping("/{productId}")
    public BaseResponse<?> isWish(@RequestHeader String membersEmail, @PathVariable Long productId) {
        Boolean result = wishProductListService.isWish(membersEmail, productId);
        return new BaseResponse<>(result);
    }

}
