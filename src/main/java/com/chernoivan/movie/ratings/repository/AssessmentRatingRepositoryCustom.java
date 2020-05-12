package com.chernoivan.movie.ratings.repository;

import com.chernoivan.movie.ratings.domain.AssessmentRating;
import com.chernoivan.movie.ratings.dto.assessmentrating.AssessmentRatingFilter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentRatingRepositoryCustom {
    List<AssessmentRating> findByFilter(AssessmentRatingFilter filter);
}
