package gentledog.members.orders.orders.webdto.out.members;

import gentledog.members.orders.orders.dto.out.members.VendorsOrderSummaryOutResponseDto;
import lombok.Getter;

import java.util.List;
@Getter
public class VendorsOrderInfoOutWebDto {

    private Long nextGroupId;
    private List<VendorsOrderSummaryOutResponseDto> vendorsOrderSummaryOutResponseDtoList;

}
