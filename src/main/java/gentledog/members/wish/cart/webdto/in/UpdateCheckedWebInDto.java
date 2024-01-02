package gentledog.members.wish.cart.webdto.in;

import gentledog.members.wish.cart.dtos.CheckedDto;
import lombok.Getter;

import java.util.List;

@Getter
public class UpdateCheckedWebInDto {
    private List<CheckedDto> changedCheckedList;
}
