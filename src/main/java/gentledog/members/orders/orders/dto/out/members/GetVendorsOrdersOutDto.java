package gentledog.members.orders.orders.dto.out.members;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetVendorsOrdersOutDto {

    private Long nextGroupId;
    private List<GetOrdersSummaryOutDto> ordersSummaryList;

}
