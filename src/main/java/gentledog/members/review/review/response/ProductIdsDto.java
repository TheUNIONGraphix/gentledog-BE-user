package gentledog.members.review.review.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductIdsDto {

    private List<Long> productIdsList;

    public static ProductIdsDto formProductIdsDto(List<Long> productIdsList){
        return ProductIdsDto.builder()
                .productIdsList(productIdsList)
                .build();
    }
}
