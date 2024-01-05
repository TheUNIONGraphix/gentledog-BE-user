package gentledog.members.review.review.application;


import gentledog.members.review.review.dto.RequestDogIds;
import gentledog.members.review.review.dto.ReviewRequest;
import gentledog.members.review.review.response.ProductIdsDto;

public interface ReviewService {

    void createReview(ReviewRequest reviewRequest);
    ProductIdsDto getProductIds(RequestDogIds requestDogIds);


}
