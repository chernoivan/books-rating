package com.chernoivan.movie.ratings.repository;

import com.chernoivan.movie.ratings.domain.ApplicationUser;
import com.chernoivan.movie.ratings.domain.Assessment;
import com.chernoivan.movie.ratings.domain.enums.AccessLevelType;
import com.chernoivan.movie.ratings.domain.enums.AssessmentType;
import com.chernoivan.movie.ratings.domain.enums.UserRoleType;
import com.chernoivan.movie.ratings.service.ApplicationUserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(statements = {
        "delete from assessment",
        "delete from application_user"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
public class AssessmentRepositoryTest {
    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Test
    public void testAssessment() {
        ApplicationUser username = createUser();
        Assessment c = new Assessment();
        c.setUser(username);
        c = assessmentRepository.save(c);
        assertNotNull(c.getId());
        assertTrue(assessmentRepository.findById(c.getId()).isPresent());
    }

    @Test
    public void testCreatedAtIsSet() {
        ApplicationUser applicationUser = createUser();
        Assessment assessment = createAssessment(applicationUser);

        Instant createdAtBeforeReload = assessment.getCreatedAt();
        Assert.assertNotNull(createdAtBeforeReload);
        assessment = assessmentRepository.findById(assessment.getId()).get();

        Instant createdAtAfterReload = assessment.getCreatedAt();
        Assert.assertNotNull(createdAtAfterReload);
        Assert.assertEquals(createdAtBeforeReload, createdAtAfterReload);
    }

    @Test
    public void testUpdatedAtIsSet() {
        ApplicationUser applicationUser = createUser();
        Assessment assessment = createAssessment(applicationUser);

        Instant updatedAtBeforeReload = assessment.getUpdatedAt();
        Assert.assertNotNull(updatedAtBeforeReload);
        assessment = assessmentRepository.findById(assessment.getId()).get();

        Instant updatedAtAfterReload = assessment.getUpdatedAt();
        Assert.assertNotNull(updatedAtAfterReload);
        Assert.assertEquals(updatedAtBeforeReload, updatedAtAfterReload);
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
