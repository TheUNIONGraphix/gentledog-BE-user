package gentledog.members.wish;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gentledog.members.wish.cart.application.CartServiceImpl;
import gentledog.members.wish.cart.dtos.in.AddProductInDto;
import gentledog.members.wish.cart.entity.ProductInCart;
import gentledog.members.wish.cart.infrastructure.CartRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ApplicationModuleTest(ApplicationModuleTest.BootstrapMode.ALL_DEPENDENCIES)
public class CartServiceTest {

    /**
     * 테스트 전 셋팅
     * - Entity 주입
     */
    // 테스트코드 실행시마다 새로운 값을 주입받기 위해서 final을 사용하지 않는다
    private CartServiceImpl cartService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    // Entity 주입 -> @BeforeEach를 사용해서 초기화 하는것을 권장
    // 1. @BeforeAll은 static만 적용이 되고
    // 2. 각 테스트 메서드마다 독립성을 보장하기위해서 사용한다
    // 3. TestWatcher를 사용하여 기능을 확장할 수 있다
    @BeforeEach
    public void setup() {
        System.out.println("test Start");
        cartService = new CartServiceImpl(cartRepository, modelMapper, jpaQueryFactory);
    }

    @AfterEach
    public void testEnd() {
        System.out.println("test end");
    }


    /**
     * Cart 테스트
     * 1. 장바구니에 상품 추가
     * 2. 장바구니 조회
     * 3. 체크 선택/취소
     * 4. 장바구니 상품 삭제
     * 5. 장바구니 상품 수량 변경
     * 6. 장바구니 선택한 물품만 조회
     */


    /**
     * 1. Given : 테스트에 사용할 변수 등 주어지는 값
     * 2. When : 실제 테스트할 메서드 실행
     * 3. Then : 결과 확인
     */
    // 1. 장바구니에 상품 추가
    @Test
    public void addProduct() {
        // given
        AddProductInDto addProductInDto = AddProductInDto.builder()
                .productDetailId(123L)
                .count(10)
                .checked(true)
                .brandName("test")
                .build();
        String membersEmail = "test@test";

        // when
        cartService.addProduct(addProductInDto, membersEmail);
        ProductInCart productInCart = cartRepository.findById(123L)
                .orElseThrow();
        ProductInCart answer = ProductInCart.builder()
                .id(123L)
                .count(10)
                .checked(true)
                .brandName("test")
                .build();

        // then
        Assertions.assertThat(productInCart).isEqualTo(answer);
    }

}
