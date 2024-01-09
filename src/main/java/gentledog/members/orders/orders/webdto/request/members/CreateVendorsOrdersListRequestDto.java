package gentledog.members.orders.orders.webdto.request.members;

import gentledog.members.orders.orders.dto.in.members.CreateOrdersDetailInDto;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateVendorsOrdersListRequestDto {

    private String vendorEmail;
    private String brandName;
    private String brandLogoImageUrl;
    private String membersName;
    private String membersPhoneNumber;
    private String ordersRequestMessage;
    private Integer ordersType;
    private Long dogId;
    private Integer deliveryFee;
    private Integer totalPrice;

    private List<CreateOrdersDetailRequestDto> ordersDetailList;

}
