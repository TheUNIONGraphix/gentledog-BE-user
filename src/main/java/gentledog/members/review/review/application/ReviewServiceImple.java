package gentledog.members.review.review.application;

import gentledog.members.review.review.dto.RequestDogIds;
import gentledog.members.review.review.dto.ReviewRequest;
import gentledog.members.review.review.entity.ReviewEntity;
import gentledog.members.review.review.infrastructure.ReviewRepository;
import gentledog.members.review.review.response.ProductIdsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImple implements ReviewService {

    private final ReviewRepository reviewRepository;


    @Override
    public void createReview(ReviewRequest reviewRequest) {
        ReviewEntity reviewEntity =
                ReviewEntity.createReview(
                        reviewRequest.getProductId(),
                        reviewRequest.getMembersId(),
                        reviewRequest.getContent(),
                        reviewRequest.getRating(),
                        reviewRequest.getDogId(),
                        reviewRequest.getProductDetailId()
                );

        reviewRepository.save(reviewEntity);

    }

    @Override
    public ProductIdsDto getProductIds(RequestDogIds requestDogIds) {
        List<Long> productIdsList = new ArrayList<>(); // Initialize the list

        requestDogIds.getDogIds().forEach(dogId -> {
            List<Long> productDetailList = reviewRepository.findByDogId(dogId);
            log.info("productDetailList: {}", productDetailList);

            productDetailList.stream()
                    .filter(Objects::nonNull) // Filter out null values
                    .forEach(productDetailId -> productIdsList.add(productDetailId));

        });

        log.info("productIdsList: {}", productIdsList);

        ProductIdsDto productIdsDto =
                ProductIdsDto.formProductIdsDto(
                        productIdsList.stream().distinct().collect(Collectors.toList()));

        return productIdsDto;
    }
}
