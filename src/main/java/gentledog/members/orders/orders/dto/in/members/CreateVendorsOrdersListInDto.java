package gentledog.members.orders.orders.dto.in.members;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateVendorsOrdersListInDto {

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

    private List<CreateOrdersDetailInDto> ordersDetailList;

}
