package gentledog.members.review.review.dto;

import lombok.Getter;

@Getter
public class ReviewRequest {

    private Long productId;
    private Long membersId;
    private String content;
    private Integer rating;
    private Long dogId;
    private Long productDetailId;

}
