package com.chernoivan.movie.ratings.service;

import com.chernoivan.movie.ratings.domain.ApplicationUser;
import com.chernoivan.movie.ratings.domain.Assessment;
import com.chernoivan.movie.ratings.domain.enums.AccessLevelType;
import com.chernoivan.movie.ratings.domain.enums.AssessmentType;
import com.chernoivan.movie.ratings.domain.enums.UserRoleType;
import com.chernoivan.movie.ratings.dto.assessment.AssessmentCreateDTO;
import com.chernoivan.movie.ratings.dto.assessment.AssessmentPatchDTO;
import com.chernoivan.movie.ratings.dto.assessment.AssessmentPutDTO;
import com.chernoivan.movie.ratings.dto.assessment.AssessmentReadDTO;
import com.chernoivan.movie.ratings.repository.ApplicationUserRepository;
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
import org.springframework.transaction.support.TransactionTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(statements = {
        "delete from assessment",
        "delete from application_user"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AssessmentServiceTest {
    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private AssessmentService assessmentService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    public void testGetAssessment() {
        ApplicationUser applicationUser = createUser();
        Assessment assessment = createAssessment(applicationUser);

        AssessmentReadDTO readDTO = assessmentService.getAssessment(assessment.getId());
        Assertions.assertThat(readDTO).isEqualToIgnoringGivenFields(assessment, "user");
    }

    @Test
    public void testCreateAssessment() {
        ApplicationUser user = createUser();
        AssessmentCreateDTO create = new AssessmentCreateDTO();
        create.setAssessmentText("great movie");
        create.setAssessmentType(AssessmentType.FILM_ASSESSMENT);
        create.setLikesCount(21);
        create.setRating(8);
        create.setUser(user.getId());

        AssessmentReadDTO read = assessmentService.createAssessment(create);
        Assertions.assertThat(create).isEqualToComparingFieldByField(read);
        Assert.assertNotNull(read.getId());

        Assessment assessment = assessmentRepository.findById(read.getId()).get();
        Assertions.assertThat(read).isEqualToIgnoringGivenFields(assessment, "user");
    }

    @Test
    public void testPatchAssessment() {
        ApplicationUser applicationUser = createUser();
        Assessment assessment = createAssessment(applicationUser);

        AssessmentPatchDTO patch = new AssessmentPatchDTO();
        patch.setAssessmentText("great movie");
        patch.setAssessmentType(AssessmentType.FILM_ASSESSMENT);
        patch.setLikesCount(21);
        patch.setRating(8);
        patch.setUser(applicationUser.getId());

        AssessmentReadDTO read = assessmentService.patchAssessment(assessment.getId(), patch);

        Assertions.assertThat(patch).isEqualToComparingFieldByField(read);

        assessment = assessmentRepository.findById(read.getId()).get();
        Assertions.assertThat(assessment).isEqualToIgnoringGivenFields(read, "items", "user");
    }

    @Test
    public void testPutAssessment() {
        ApplicationUser applicationUser = createUser();
        Assessment assessment = createAssessment(applicationUser);

        AssessmentPutDTO put = new AssessmentPutDTO();
        put.setAssessmentType(AssessmentType.FILM_ASSESSMENT);
        put.setRating(8);
        put.setUser(applicationUser.getId());
        AssessmentReadDTO read = assessmentService.updateAssessment(assessment.getId(), put);

        Assertions.assertThat(put).isEqualToComparingFieldByField(read);

        assessment = assessmentRepository.findById(read.getId()).get();
        Assertions.assertThat(assessment).isEqualToIgnoringGivenFields(read, "items", "user");
    }

    @Test
    public void testPatchAssessmentEmptyPatch() {
        ApplicationUser applicationUser = createUser();
        Assessment assessment = createAssessment(applicationUser);

        AssessmentPatchDTO patch = new AssessmentPatchDTO();
        AssessmentReadDTO read = assessmentService.patchAssessment(assessment.getId(), patch);

        Assert.assertNotNull(read.getAssessmentText());
        Assert.assertNotNull(read.getAssessmentType());
        Assert.assertNotNull(read.getLikesCount());
        Assert.assertNotNull(read.getRating());
        Assert.assertNotNull(read.getUser());


        inTransaction(() -> {
            Assessment assessmentAfterUpdate = assessmentRepository.findById(read.getId()).get();

            Assert.assertNotNull(assessmentAfterUpdate.getAssessmentText());
            Assert.assertNotNull(assessmentAfterUpdate.getAssessmentType());
            Assert.assertNotNull(assessmentAfterUpdate.getLikesCount());
            Assert.assertNotNull(assessmentAfterUpdate.getRating());
            Assert.assertNotNull(assessmentAfterUpdate.getUser());

            Assertions.assertThat(assessment).isEqualToIgnoringGivenFields(assessmentAfterUpdate, "items", "user");
        });
    }

    @Test
    public void testDeleteAssessment() {
        ApplicationUser applicationUser = createUser();
        Assessment assessment = createAssessment(applicationUser);

        assessmentService.deleteAssessment(assessment.getId());
        Assert.assertFalse(assessmentRepository.existsById(assessment.getId()));
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

    private void inTransaction(Runnable runnable) {
        transactionTemplate.executeWithoutResult(status -> {
            runnable.run();
        });
    }
}
