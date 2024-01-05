package gentledog.members.review.review.presentation;

import gentledog.members.global.common.response.BaseResponse;
import gentledog.members.review.review.application.ReviewService;
import gentledog.members.review.review.dto.RequestDogIds;
import gentledog.members.review.review.dto.ReviewRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/review")
@Slf4j
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/create-review")
    public BaseResponse<?> createReview(@RequestBody ReviewRequest reviewRequest) {
        log.info("reviewRequest: {}", reviewRequest);
        reviewService.createReview(reviewRequest);

        return new BaseResponse<>();

    }

    @PostMapping("/find-review-dogId")
    public BaseResponse<?> findReview(@RequestBody RequestDogIds requestDogIds){
        log.info("findReview");
        return new BaseResponse<>(reviewService.getProductIds(requestDogIds));
    }
}
