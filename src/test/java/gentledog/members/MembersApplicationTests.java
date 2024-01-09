package gentledog.members;

import gentledog.members.global.common.util.RedisUtil;
import gentledog.members.orders.orders.webdto.request.members.CreateOrdersRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.Modulithic;

@Modulithic
@SpringBootTest
class MembersApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void contextLoads() {
    }

    @Test
    public void redisTest() throws Exception {
        //given
        String email = "test@test.com";
        String code = "aaa111";

        //when
        redisUtil.createDataExpire(email, code, 60 * 60L);

        //then
        Assertions.assertTrue(redisUtil.existData("test@test.com"));
        Assertions.assertFalse(redisUtil.existData("test1@test.com"));
        Assertions.assertEquals(redisUtil.getData(email), "aaa111");

    }

    // 1. 주문 생성
//    @Test
//    public void createOrder() {
//        //given
//        String membersEmail = "y@naver.com";
//        CreateOrdersRequestDto createOrdersRequestDto = new CreateOrdersRequestDto();
//        createOrdersRequestDto.setRecipientAddress("서울시 강남구");
//        createOrdersRequestDto.setRecipientPhoneNumber("010-1234-5678");
//        createOrdersRequestDto.setEntrancePassword("1234");
//        createOrdersRequestDto.setDeliveryRequestMessage("부재시 전화주세요");
//
//        //when
//        BaseResponse<GetOrdersNumberResponseDto> response = membersOrderController.createOrder(membersEmail, createOrdersRequestDto);
//
//        //then
//        Assertions.assertEquals(response.getData().getOrdersNumber(), 1L);
//
//
//    }

}
