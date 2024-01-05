package gentledog.members.wish.cart.application;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gentledog.members.wish.cart.dtos.ProductDto;
import gentledog.members.wish.cart.dtos.in.AddProductInDto;
import gentledog.members.wish.cart.dtos.in.UpdateCheckedInDto;
import gentledog.members.wish.cart.dtos.out.GetCartOutDto;
import gentledog.members.wish.cart.dtos.out.GetCheckedCartOutDto;
import gentledog.members.wish.cart.entity.ProductInCart;
import gentledog.members.wish.cart.infrastructure.CartRepository;
import gentledog.members.global.common.exception.BaseException;
import gentledog.members.global.common.response.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CartServiceImpl implements CartService{
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;
    private final JPAQueryFactory jpaQueryFactory;

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
    @Override
    public void addProduct(AddProductInDto inDto, String membersEmail) {
        // 중복 조회
        if (cartRepository.existsByProductDetailId(inDto.getProductDetailId()) == true) {
            throw new BaseException(BaseResponseStatus.ALREADY_ADDED_PRODUCT);
        }
        // 중복되지 않았다면 추가
        inDto.setmembersEmail(membersEmail);
        ProductInCart productInCart = modelMapper.map(inDto, ProductInCart.class);
        cartRepository.save(productInCart);
    }

    // 2. 장바구니 조회
    @Override
    @Transactional(readOnly = true)
    public GetCartOutDto getCart(String membersEmail) {
        // membersEmail로 cart 조회
        List<ProductInCart> productInCart = cartRepository.findBymembersEmail(membersEmail);

        // 상품을 브랜드별로 분류 -> key:브랜드이름, value: 상품dto리스트
        TreeMap<String, List<ProductDto>> byBrand = new TreeMap<>();
        productInCart.forEach(product ->{
            String brandName = product.getBrandName();

            // productDto 생성
            ProductDto productDto = ProductDto.builder()
                    .productDetailId(product.getProductDetailId())
                    .count(product.getCount())
                    .checked(product.getChecked())
                    .productInCartId(product.getId())
                    .build();

            // 브랜드 이름에 맞춰서 저장
            List<ProductDto> productDtoList;
            if (byBrand.containsKey(brandName) == true) {
                productDtoList = byBrand.get(brandName);
                productDtoList.add(productDto);
            } else {
                productDtoList = new ArrayList<>();
                productDtoList.add(productDto);
            }
            byBrand.put(brandName, productDtoList);
        });

        // GetCartOutDto 생성 및 return
        return new GetCartOutDto(byBrand);
    }

    // 3. 체크 선택/취소
    @Override
    public void updateChecked(UpdateCheckedInDto inDto) {
        // inDto의 모든 상품의 check 상태를 변경
        inDto.getChangedCheckedList().forEach(product ->{
            // id로 조회
            ProductInCart productInCart = cartRepository.findById(product.getProductInCartId())
                    .orElseThrow(()-> new BaseException(BaseResponseStatus.NO_DATA));
            // check 업데이트
            productInCart.updateChecked(product.getChecked());
        });
    }

    // 4. 장바구니 상품 삭제
    @Override
    public void deleteProduct(Long productInCartId) {
        cartRepository.deleteById(productInCartId);
    }

    // 5. 장바구니 상품 수량 변경
    @Override
    public void updateProductCount(Long productInCartId, Integer count) {
        ProductInCart product = cartRepository.findById(productInCartId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_DATA));
        product.updateCount(count);
    }

    // 6. 장바구니 선택한 물품만 조회
    @Override
    @Transactional(readOnly = true)
    public GetCheckedCartOutDto getCheckedCart(String membersEmail) {
        // membersEmail로 cart 조회
        List<ProductInCart> productInCart = cartRepository.findBymembersEmailAndChecked(membersEmail, true);
        AtomicInteger totalCount = new AtomicInteger(0);

        // 상품을 브랜드별로 분류 -> key:브랜드이름, value: 상품dto리스트
        TreeMap<String, List<ProductDto>> byBrand = new TreeMap<>();
        productInCart.forEach(product ->{
            String brandName = product.getBrandName();
            // totalCount 증가
            totalCount.addAndGet(product.getCount());

            // productDto 생성
            ProductDto productDto = ProductDto.builder()
                    .productDetailId(product.getProductDetailId())
                    .count(product.getCount())
                    .checked(product.getChecked())
                    .productInCartId(product.getId())
                    .build();

            // 브랜드 이름에 맞춰서 저장
            List<ProductDto> productDtoList;
            if (byBrand.containsKey(brandName) == true) {
                productDtoList = byBrand.get(brandName);
                productDtoList.add(productDto);
            } else {
                productDtoList = new ArrayList<>();
                productDtoList.add(productDto);
            }
            byBrand.put(brandName, productDtoList);
        });

        // GetCheckedCartOutDto 생성 및 return
        return new GetCheckedCartOutDto(byBrand, totalCount.get());
    }
}
