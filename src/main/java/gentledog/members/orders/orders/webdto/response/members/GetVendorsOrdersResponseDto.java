package gentledog.members.orders.orders.webdto.response.members;

import gentledog.members.orders.orders.dto.out.members.GetOrdersSummaryOutDto;
import lombok.Getter;

import java.util.List;
@Getter
public class GetVendorsOrdersResponseDto {

    private Long nextGroupId;
    private List<GetOrdersSummaryOutDto> ordersSummaryList;

}
