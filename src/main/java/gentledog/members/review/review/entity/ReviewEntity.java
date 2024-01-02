package gentledog.members.review.review.entity;

import gentledog.members.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "review")
public class ReviewEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "members_id", nullable = false)
    private Long membersId;

    @Column(name = "content", nullable = false, length = 255)
    private String content;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "dog_id", nullable = false)
    private Long dogId;

    @Column(name = "product_detail_id", nullable = false)
    private Long productDetailId;

    public static ReviewEntity createReview(Long productId, Long membersId, String content, Integer rating, Long dogId, Long productDetailId) {
        return ReviewEntity.builder()
                .productId(productId)
                .membersId(membersId)
                .content(content)
                .rating(rating)
                .dogId(dogId)
                .productDetailId(productDetailId)
                .build();
    }



}
