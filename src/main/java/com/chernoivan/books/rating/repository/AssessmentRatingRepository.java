package com.chernoivan.books.rating.repository;

import com.chernoivan.books.rating.domain.AssessmentRating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AssessmentRatingRepository extends CrudRepository<AssessmentRating, UUID>,
        AssessmentRatingRepositoryCustom {
    List<AssessmentRating> findByUserIdOrderById(UUID userId);

    @Query("select v from AssessmentRating v where v.user.id = :user and v.assessment.id = :assessment"
            + " and v.likeStatus = :likeStatus")
    List<AssessmentRating> findByLikeStatus(
            UUID user, UUID assessment, Boolean likeStatus);

}
