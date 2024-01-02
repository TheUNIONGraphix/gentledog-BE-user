package gentledog.members.review.review.infrastructure;


import gentledog.members.review.review.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>{

    @Query(value = "SELECT product_id FROM `review`.review WHERE dog_id = :dogId AND rating >= 3 GROUP BY product_id; ", nativeQuery = true)
    List<Long> findByDogId(@Param("dogId") Long dogId);
}
