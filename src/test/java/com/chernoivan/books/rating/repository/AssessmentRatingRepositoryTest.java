package com.chernoivan.books.rating.repository;

import com.chernoivan.books.rating.domain.ApplicationUser;
import com.chernoivan.books.rating.domain.Assessment;
import com.chernoivan.books.rating.domain.AssessmentRating;
import com.chernoivan.books.rating.domain.enums.AccessLevelType;
import com.chernoivan.books.rating.domain.enums.AssessmentType;
import com.chernoivan.books.rating.domain.enums.UserRoleType;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(statements = {
        "delete from assessment_rating",
        "delete from assessment",
        "delete from application_user"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
public class AssessmentRatingRepositoryTest {
    @Autowired
    private AssessmentRatingRepository assessmentRatingRepository;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Test
    public void testSave() {
        AssessmentRating c = new AssessmentRating();
        c = assessmentRatingRepository.save(c);
        assertNotNull(c.getId());
        assertTrue(assessmentRatingRepository.findById(c.getId()).isPresent());
    }

    @Test
    public void testGetAssessmentRating() {
        ApplicationUser user1 = createUser();
        ApplicationUser user2 = createUser();
        Assessment assessment = createAssessment(user1);
        AssessmentRating v1 = createAssessmentRating(user1, assessment);
        createAssessmentRating(user2, assessment);
        AssessmentRating v2 = createAssessmentRating(user1, assessment);

        List<AssessmentRating> res = assessmentRatingRepository.findByUserIdOrderById(user1.getId());
        Assertions.assertThat(res).extracting(AssessmentRating::getId).containsExactlyInAnyOrder(v1.getId(), v2.getId());
    }

    @Test
    public void testGetByUserAndAssessment() {
        ApplicationUser user = createUser();
        Assessment a = createAssessment(user);
        AssessmentRating ar1 = createAssessmentRating(user, a);
        AssessmentRating ar2 = createAssessmentRating(user, a);

        List<AssessmentRating> res = assessmentRatingRepository.findByLikeStatus(user.getId(), a.getId(), true);
        Assertions.assertThat(res).extracting(AssessmentRating::getId).isEqualTo(Arrays.asList(ar1.getId(), ar2.getId()));
    }

    @Test
    public void testCreatedAtIsSet() {
        ApplicationUser user = createUser();
        Assessment assessment = createAssessment(user);
        AssessmentRating rating = createAssessmentRating(user, assessment);

        Instant createdAtBeforeReload = rating.getCreatedAt();
        Assert.assertNotNull(createdAtBeforeReload);
        rating = assessmentRatingRepository.findById(rating.getId()).get();

        Instant createdAtAfterReload = rating.getCreatedAt();
        Assert.assertNotNull(createdAtAfterReload);
        Assert.assertEquals(createdAtBeforeReload, createdAtAfterReload);
    }

    @Test
    public void testUpdatedAtIsSet() {
        ApplicationUser user = createUser();
        Assessment assessment = createAssessment(user);
        AssessmentRating rating = createAssessmentRating(user, assessment);

        Instant updatedAtBeforeReload = rating.getUpdatedAt();
        Assert.assertNotNull(updatedAtBeforeReload);
        rating = assessmentRatingRepository.findById(rating.getId()).get();

        Instant updatedAtAfterReload = rating.getCreatedAt();
        Assert.assertNotNull(updatedAtAfterReload);
        Assert.assertEquals(updatedAtBeforeReload, updatedAtAfterReload);
    }

    private AssessmentRating createAssessmentRating(ApplicationUser applicationUser, Assessment assessment) {
        AssessmentRating assessmentRating = new AssessmentRating();
        assessmentRating.setUser(applicationUser);
        assessmentRating.setAssessment(assessment);
        assessmentRating.setLikeStatus(true);
        return assessmentRatingRepository.save(assessmentRating);
    }

    private Assessment createAssessment(ApplicationUser applicationUser) {
        Assessment assessment = new Assessment();
        assessment.setAssessmentText("great movie");
        assessment.setAssessmentType(AssessmentType.FILM_ASSESSMENT);
        assessment.setLikesCount(23);
        assessment.setRating(8);
        assessment.setUser(applicationUser);
        return assessmentRepository.save(assessment);
    }

    private ApplicationUser createUser() {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername("Alex");
        applicationUser.setEmail("alexchernoivan@gmail.com");
        applicationUser.setAccess(AccessLevelType.FULL_ACCESS);
        applicationUser.setUserType(UserRoleType.MODERATOR);
        return applicationUserRepository.save(applicationUser);
    }

}
