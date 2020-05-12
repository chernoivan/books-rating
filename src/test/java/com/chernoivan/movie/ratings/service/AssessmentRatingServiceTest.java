package com.chernoivan.movie.ratings.service;

import com.chernoivan.movie.ratings.domain.ApplicationUser;
import com.chernoivan.movie.ratings.domain.Assessment;
import com.chernoivan.movie.ratings.domain.AssessmentRating;
import com.chernoivan.movie.ratings.domain.enums.AccessLevelType;
import com.chernoivan.movie.ratings.domain.enums.AssessmentType;
import com.chernoivan.movie.ratings.domain.enums.UserRoleType;
import com.chernoivan.movie.ratings.dto.assessmentrating.*;
import com.chernoivan.movie.ratings.exception.EntityNotFoundException;
import com.chernoivan.movie.ratings.repository.ApplicationUserRepository;
import com.chernoivan.movie.ratings.repository.AssessmentRatingRepository;
import com.chernoivan.movie.ratings.repository.AssessmentRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(statements = {
        "delete from assessment_rating",
        "delete from assessment",
        "delete from application_user"
        },
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AssessmentRatingServiceTest {
    @Autowired
    private AssessmentRatingRepository assessmentRatingRepository;

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private AssessmentRatingService assessmentRatingService;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Test
    public void testGetAssessmentRating() {
        ApplicationUser applicationUser = createUser();
        Assessment assessment = createAssessment(applicationUser);
        AssessmentRating assessmentRating = createAssessmentRating(applicationUser, assessment);

        AssessmentRatingReadDTO readDTO = assessmentRatingService.getAssessmentRating(assessmentRating.getId());
        Assertions.assertThat(readDTO).isEqualToIgnoringGivenFields(assessmentRating, "user", "assessment");
    }

    @Test
    public void testGetAssessmentRatingExtended() {
        ApplicationUser applicationUser = createUser();
        Assessment assessment = createAssessment(applicationUser);
        AssessmentRating assessmentRating = createAssessmentRating(applicationUser, assessment);

        AssessmentRatingReadExtendedDTO readDTO = assessmentRatingService
                .getAssessmentRatingExtended(assessmentRating.getId());
        Assertions.assertThat(readDTO).isEqualToIgnoringGivenFields(assessmentRating, "user", "assessment");
        Assertions.assertThat(readDTO.getUser()).isEqualToIgnoringGivenFields(applicationUser);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetAssessmentRatingWringId() {
        assessmentRatingService.getAssessmentRating(UUID.randomUUID());
    }


    @Test
    public void testCreateAssessmentRating() {
        ApplicationUser applicationUser = createUser();
        Assessment assessment = createAssessment(applicationUser);
        AssessmentRatingCreateDTO create = new AssessmentRatingCreateDTO();
        create.setUser(applicationUser.getId());
        create.setAssessment(assessment.getId());
        create.setLikeStatus(true);

        AssessmentRatingReadDTO read = assessmentRatingService.createAssessmentRating(create);
        Assertions.assertThat(create).isEqualToComparingFieldByField(read);
        Assert.assertNotNull(read.getId());

        AssessmentRating assessmentRating = assessmentRatingRepository.findById(read.getId()).get();
        Assertions.assertThat(read).isEqualToIgnoringGivenFields(assessmentRating, "user", "assessment");
    }

    @Test
    public void testCreateAssessmentRatingExtended() {
        ApplicationUser applicationUser = createUser();
        Assessment assessment = createAssessment(applicationUser);
        AssessmentRatingCreateDTO create = new AssessmentRatingCreateDTO();
        create.setUser(applicationUser.getId());
        create.setAssessment(assessment.getId());
        create.setLikeStatus(true);

        AssessmentRatingReadExtendedDTO read = assessmentRatingService.createAssessmentRatingExtended(create);
        Assertions.assertThat(create).isEqualToIgnoringGivenFields(read, "user", "assessment", "film");
        Assert.assertNotNull(read.getId());

        AssessmentRating assessmentRating = assessmentRatingRepository.findById(read.getId()).get();
        Assertions.assertThat(read).isEqualToIgnoringGivenFields(assessmentRating, "user", "assessment", "film");
    }

    @Test
    public void testPutAssessmentRating() {
        ApplicationUser applicationUser = createUser();
        Assessment assessment = createAssessment(applicationUser);
        AssessmentRating assessmentRating = createAssessmentRating(applicationUser, assessment);

        AssessmentRatingPutDTO put = new AssessmentRatingPutDTO();
        put.setLikeStatus(true);
        put.setUser(applicationUser.getId());
        put.setAssessment(assessment.getId());
        AssessmentRatingReadDTO read = assessmentRatingService.updateAssessmentRating(assessmentRating.getId(), put);

        Assertions.assertThat(put).isEqualToIgnoringGivenFields(assessmentRating, "user", "assessment");

        assessmentRating = assessmentRatingRepository.findById(read.getId()).get();
        Assertions.assertThat(assessmentRating).isEqualToIgnoringGivenFields(assessmentRating, "user");
    }

    @Test
    public void testPatchAssessmentRating() {
        ApplicationUser user = createUser();
        Assessment assessment = createAssessment(user);
        AssessmentRating assessmentRating = createAssessmentRating(user, assessment);

        AssessmentRatingPatchDTO patch = new AssessmentRatingPatchDTO();
        patch.setLikeStatus(true);
        patch.setUser(user.getId());
        patch.setAssessment(assessment.getId());
        AssessmentRatingReadDTO read = assessmentRatingService.patchAssessmentRating(assessmentRating.getId(), patch);

        Assertions.assertThat(patch)
                .isEqualToIgnoringGivenFields(assessmentRating, "user", "assessment");

        assessmentRating = assessmentRatingRepository.findById(read.getId()).get();
        Assertions.assertThat(assessmentRating)
                .isEqualToIgnoringGivenFields(assessmentRating, "user");
    }

    @Test
    public void testPatchAssessmentRatingEmptyPatch() {
        ApplicationUser user = createUser();
        Assessment assessment = createAssessment(user);
        AssessmentRating assessmentRating = createAssessmentRating(user, assessment);

        AssessmentRatingPatchDTO patch = new AssessmentRatingPatchDTO();
        AssessmentRatingReadDTO read = assessmentRatingService.patchAssessmentRating(assessmentRating.getId(), patch);

        Assert.assertNotNull(read.getLikeStatus());
        Assert.assertNotNull(read.getUser());

        AssessmentRating assessmentRatingAfterUpdate = assessmentRatingRepository.findById(read.getId()).get();

        Assert.assertNotNull(assessmentRatingAfterUpdate.getLikeStatus());
        Assert.assertNotNull(assessmentRatingAfterUpdate.getUser());

        Assertions.assertThat(assessmentRating).isEqualToIgnoringGivenFields(assessmentRatingAfterUpdate, "user", "assessment");
    }

    @Test
    public void testDeleteAssessmentRating() {
        ApplicationUser user = createUser();
        Assessment assessment = createAssessment(user);
        AssessmentRating assessmentRating = createAssessmentRating(user, assessment);

        assessmentRatingService.deleteAssessmentRating(assessmentRating.getId());
        Assert.assertFalse(assessmentRatingRepository.existsById(assessmentRating.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteAssessmentRatingNonFound() {
        assessmentRatingService.deleteAssessmentRating(UUID.randomUUID());
    }

    @Test
    public void testGetAssessmentRatingWithEmptyFilter() {
        ApplicationUser au1 = createUser();
        Assessment a1 = createAssessment(au1);
        ApplicationUser au2 = createUser();
        Assessment a2 = createAssessment(au2);

        AssessmentRating v1 = createAssessmentRating(au1, a1);
        AssessmentRating v2 = createAssessmentRating(au1, a2);
        AssessmentRating v3 = createAssessmentRating(au2, a2);

        AssessmentRatingFilter filter = new AssessmentRatingFilter();
        Assertions.assertThat(assessmentRatingService.getAssessmentRatings(filter)).extracting("id")
                .containsExactlyInAnyOrder(v1.getId(), v2.getId(), v3.getId());
    }

    @Test
    public void testGetAssessmentRatingByApplicationUser() {
        ApplicationUser au1 = createUser();
        Assessment a1 = createAssessment(au1);
        ApplicationUser au2 = createUser();
        Assessment a2 = createAssessment(au2);

        AssessmentRating v1 = createAssessmentRating(au1, a1);
        AssessmentRating v2 = createAssessmentRating(au1, a2);
        AssessmentRating v3 = createAssessmentRating(au2, a2);

        AssessmentRatingFilter filter = new AssessmentRatingFilter();
        filter.setUserId(au1.getId());
        Assertions.assertThat(assessmentRatingService.getAssessmentRatings(filter)).extracting("id")
                .containsExactlyInAnyOrder(v1.getId(), v2.getId());
    }

    @Test
    public void testGetAssessmentRatingByAssessment() {
        ApplicationUser au1 = createUser();
        Assessment a1 = createAssessment(au1);
        ApplicationUser au2 = createUser();
        Assessment a2 = createAssessment(au2);

        AssessmentRating v1 = createAssessmentRating(au1, a1);
        AssessmentRating v2 = createAssessmentRating(au1, a2);
        AssessmentRating v3 = createAssessmentRating(au2, a2);

        AssessmentRatingFilter filter = new AssessmentRatingFilter();
        filter.setAssessmentId(a1.getId());
        Assertions.assertThat(assessmentRatingService.getAssessmentRatings(filter)).extracting("id")
                .containsExactlyInAnyOrder(v1.getId());
    }

    @Test
    public void testGetAssessmentRatingByLikeStatus() {
        ApplicationUser au1 = createUser();
        Assessment a1 = createAssessment(au1);
        ApplicationUser au2 = createUser();
        Assessment a2 = createAssessment(au2);

        AssessmentRating v1 = createAssessmentRating(au1, a1);
        AssessmentRating v2 = createAssessmentRating(au1, a2);
        AssessmentRating v3 = createAssessmentRating(au2, a2);

        AssessmentRatingFilter filter = new AssessmentRatingFilter();
        filter.setLikeStatus(true);
        Assertions.assertThat(assessmentRatingService.getAssessmentRatings(filter)).extracting("id")
                .containsExactlyInAnyOrder(v1.getId(), v2.getId(), v3.getId());
    }

    @Test
    public void testGetAssessmentRatingByAllFilters() {
        ApplicationUser au1 = createUser();
        Assessment a1 = createAssessment(au1);
        ApplicationUser au2 = createUser();
        Assessment a2 = createAssessment(au2);

        AssessmentRating v1 = createAssessmentRating(au1, a1);
        AssessmentRating v2 = createAssessmentRating(au1, a2);
        AssessmentRating v3 = createAssessmentRating(au2, a2);

        AssessmentRatingFilter filter = new AssessmentRatingFilter();
        filter.setUserId(au1.getId());
        filter.setAssessmentId(a2.getId());
        filter.setLikeStatus(true);
        Assertions.assertThat(assessmentRatingService.getAssessmentRatings(filter)).extracting("id")
                .containsExactlyInAnyOrder(v2.getId());
    }

    private AssessmentRating createAssessmentRating(ApplicationUser applicationUser, Assessment assessment) {
        AssessmentRating assessmentRating = new AssessmentRating();
        assessmentRating.setUser(applicationUser);
        assessmentRating.setAssessment(assessment);
        assessmentRating.setLikeStatus(true);
        return assessmentRatingRepository.save(assessmentRating);
    }

    private ApplicationUser createUser() {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername("Alex");
        applicationUser.setEmail("alexchernoivan@gmail.com");
        applicationUser.setAccess(AccessLevelType.FULL_ACCESS);
        applicationUser.setUserType(UserRoleType.MODERATOR);
        return applicationUserRepository.save(applicationUser);
    }

    private Assessment createAssessment(ApplicationUser applicationUser) {
        Assessment assessment = new Assessment();
        assessment.setRating(8);
        assessment.setLikesCount(null);
        assessment.setAssessmentType(AssessmentType.FILM_ASSESSMENT);
        assessment.setAssessmentText("Super movie!");
        assessment.setUser(applicationUser);
        return assessmentRepository.save(assessment);
    }
}
