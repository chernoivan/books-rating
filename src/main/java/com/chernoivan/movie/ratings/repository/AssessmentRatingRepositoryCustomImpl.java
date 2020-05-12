package com.chernoivan.movie.ratings.repository;

import com.chernoivan.movie.ratings.domain.AssessmentRating;
import com.chernoivan.movie.ratings.dto.assessmentrating.AssessmentRatingFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class AssessmentRatingRepositoryCustomImpl implements AssessmentRatingRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AssessmentRating> findByFilter(AssessmentRatingFilter filter) {
        StringBuilder sb = new StringBuilder();
        sb.append("select v from AssessmentRating v where 1=1");
        if (filter.getLikeStatus() != null) {
            sb.append(" and v.likeStatus = :likeStatus");
        }
        if (filter.getAssessmentId() != null) {
            sb.append(" and v.assessment.id = :assessmentId");
        }
        if (filter.getUserId() != null) {
            sb.append(" and v.user.id = :userId");
        }
        TypedQuery<AssessmentRating> query = entityManager.createQuery(sb.toString(), AssessmentRating.class);

        if (filter.getUserId() != null) {
            query.setParameter("userId", filter.getUserId());
        }
        if (filter.getAssessmentId() != null) {
            query.setParameter("assessmentId", filter.getAssessmentId());
        }
        if (filter.getLikeStatus() != null) {
            query.setParameter("likeStatus", filter.getLikeStatus());
        }
        return query.getResultList();
    }
}
