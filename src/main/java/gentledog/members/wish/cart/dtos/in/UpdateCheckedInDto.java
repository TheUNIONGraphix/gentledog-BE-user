package gentledog.members.wish.cart.dtos.in;

import gentledog.members.wish.cart.dtos.CheckedDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCheckedInDto {
    private List<CheckedDto> changedCheckedList;
}
