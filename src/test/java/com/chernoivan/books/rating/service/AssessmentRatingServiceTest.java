package com.chernoivan.books.rating.service;

import com.chernoivan.books.rating.domain.ApplicationUser;
import com.chernoivan.books.rating.domain.Assessment;
import com.chernoivan.books.rating.domain.AssessmentRating;
import com.chernoivan.books.rating.domain.Book;
import com.chernoivan.books.rating.domain.enums.AccessLevelType;
import com.chernoivan.books.rating.domain.enums.AssessmentType;
import com.chernoivan.books.rating.dto.assessmentrating.*;
import com.chernoivan.books.rating.exception.EntityNotFoundException;
import com.chernoivan.books.rating.repository.ApplicationUserRepository;
import com.chernoivan.books.rating.repository.AssessmentRatingRepository;
import com.chernoivan.books.rating.repository.AssessmentRepository;
import com.chernoivan.books.rating.repository.BookRepository;
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
        "delete from application_user",
        "delete from book"
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

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testGetAssessmentRating() {
        ApplicationUser applicationUser = createUser();
        Book book = createBook();
        Assessment assessment = createAssessment(applicationUser, book);
        AssessmentRating assessmentRating = createAssessmentRating(applicationUser, assessment);

        AssessmentRatingReadDTO readDTO = assessmentRatingService.getAssessmentRating(assessmentRating.getId());
        Assertions.assertThat(assessmentRating).isEqualToIgnoringGivenFields(readDTO, "user", "assessment");
    }

    @Test
    public void testGetAssessmentRatingExtended() {
        ApplicationUser applicationUser = createUser();
        Book book = createBook();
        Assessment assessment = createAssessment(applicationUser, book);
        AssessmentRating assessmentRating = createAssessmentRating(applicationUser, assessment);

        AssessmentRatingReadExtendedDTO readDTO = assessmentRatingService
                .getAssessmentRatingExtended(assessmentRating.getId());
        Assertions.assertThat(assessmentRating).isEqualToIgnoringGivenFields(readDTO, "user", "assessment");
        Assertions.assertThat(readDTO.getUserId()).isEqualToIgnoringGivenFields(applicationUser);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetAssessmentRatingWringId() {
        assessmentRatingService.getAssessmentRating(UUID.randomUUID());
    }


    @Test
    public void testCreateAssessmentRating() {
        ApplicationUser applicationUser = createUser();
        Book book = createBook();
        Assessment assessment = createAssessment(applicationUser, book);
        AssessmentRatingCreateDTO create = new AssessmentRatingCreateDTO();
        create.setUserId(applicationUser.getId());
        create.setAssessmentId(assessment.getId());
        create.setLikeStatus(true);

        AssessmentRatingReadDTO read = assessmentRatingService.createAssessmentRating(create);
        Assertions.assertThat(create).isEqualToComparingFieldByField(read);
        Assert.assertNotNull(read.getId());

        AssessmentRating assessmentRating = assessmentRatingRepository.findById(read.getId()).get();
        Assertions.assertThat(assessmentRating).isEqualToIgnoringGivenFields(read, "user", "assessment");
    }

    @Test
    public void testCreateAssessmentRatingExtended() {
        ApplicationUser applicationUser = createUser();
        Book book = createBook();
        Assessment assessment = createAssessment(applicationUser, book);
        AssessmentRatingCreateDTO create = new AssessmentRatingCreateDTO();
        create.setUserId(applicationUser.getId());
        create.setAssessmentId(assessment.getId());
        create.setLikeStatus(true);

        AssessmentRatingReadExtendedDTO read = assessmentRatingService.createAssessmentRatingExtended(create);
        Assertions.assertThat(create).isEqualToIgnoringGivenFields(read, "userId", "assessmentId");
        Assert.assertNotNull(read.getId());

        AssessmentRating assessmentRating = assessmentRatingRepository.findById(read.getId()).get();
        Assertions.assertThat(assessmentRating).isEqualToIgnoringGivenFields(read, "user", "assessment");
    }

    @Test
    public void testPutAssessmentRating() {
        ApplicationUser applicationUser = createUser();
        Book book = createBook();
        Assessment assessment = createAssessment(applicationUser, book);
        AssessmentRating assessmentRating = createAssessmentRating(applicationUser, assessment);

        AssessmentRatingPutDTO put = new AssessmentRatingPutDTO();
        put.setLikeStatus(true);
        put.setUserId(applicationUser.getId());
        put.setAssessmentId(assessment.getId());
        AssessmentRatingReadDTO read = assessmentRatingService.updateAssessmentRating(assessmentRating.getId(), put);

        Assertions.assertThat(put).isEqualToIgnoringGivenFields(read, "user", "assessment");

        assessmentRating = assessmentRatingRepository.findById(read.getId()).get();
        Assertions.assertThat(assessmentRating).isEqualToIgnoringGivenFields(assessmentRating, "user");
    }

    @Test
    public void testPatchAssessmentRating() {
        ApplicationUser user = createUser();
        Book book = createBook();
        Assessment assessment = createAssessment(user, book);
        AssessmentRating assessmentRating = createAssessmentRating(user, assessment);

        AssessmentRatingPatchDTO patch = new AssessmentRatingPatchDTO();
        patch.setLikeStatus(true);
        patch.setUserId(user.getId());
        patch.setAssessmentId(assessment.getId());

        AssessmentRatingReadDTO read = assessmentRatingService.patchAssessmentRating(assessmentRating.getId(), patch);

        Assertions.assertThat(patch)
                .isEqualToIgnoringGivenFields(read, "user", "assessment");

        assessmentRating = assessmentRatingRepository.findById(read.getId()).get();
        Assertions.assertThat(assessmentRating)
                .isEqualToIgnoringGivenFields(read, "user", "assessment");
    }

    @Test
    public void testPatchAssessmentRatingEmptyPatch() {
        ApplicationUser user = createUser();
        Book book = createBook();
        Assessment assessment = createAssessment(user, book);
        AssessmentRating assessmentRating = createAssessmentRating(user, assessment);

        AssessmentRatingPatchDTO patch = new AssessmentRatingPatchDTO();
        AssessmentRatingReadDTO read = assessmentRatingService.patchAssessmentRating(assessmentRating.getId(), patch);

        Assert.assertNotNull(read.getLikeStatus());
        Assert.assertNotNull(read.getUserId());

        AssessmentRating assessmentRatingAfterUpdate = assessmentRatingRepository.findById(read.getId()).get();

        Assert.assertNotNull(assessmentRatingAfterUpdate.getLikeStatus());
        Assert.assertNotNull(assessmentRatingAfterUpdate.getUser());

        Assertions.assertThat(assessmentRating).isEqualToIgnoringGivenFields(assessmentRatingAfterUpdate, "user", "assessment");
    }

    @Test
    public void testDeleteAssessmentRating() {
        ApplicationUser user = createUser();
        Book book = createBook();
        Assessment assessment = createAssessment(user, book);
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
        Book book = createBook();
        Assessment a1 = createAssessment(au1, book);
        ApplicationUser au2 = createUser();
        Assessment a2 = createAssessment(au2, book);

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
        Book book = createBook();
        Assessment a1 = createAssessment(au1, book);
        ApplicationUser au2 = createUser();
        Assessment a2 = createAssessment(au2, book);

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
        Book book = createBook();
        Assessment a1 = createAssessment(au1, book);
        ApplicationUser au2 = createUser();
        Assessment a2 = createAssessment(au2, book);

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
        Book book = createBook();
        Assessment a1 = createAssessment(au1, book);
        ApplicationUser au2 = createUser();
        Assessment a2 = createAssessment(au2, book);

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
        Book book = createBook();
        Assessment a1 = createAssessment(au1, book);
        ApplicationUser au2 = createUser();
        Assessment a2 = createAssessment(au2, book);

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
        return applicationUserRepository.save(applicationUser);
    }

    private Assessment createAssessment(ApplicationUser applicationUser, Book book) {
        Assessment assessment = new Assessment();
        assessment.setRating(8);
        assessment.setLikesCount(null);
        assessment.setBook(book);
        assessment.setAssessmentType(AssessmentType.BOOK_ASSESSMENT);
        assessment.setAssessmentText("Super movie!");
        assessment.setUser(applicationUser);
        return assessmentRepository.save(assessment);
    }

    private Book createBook() {
        Book book = new Book();
        return bookRepository.save(book);
    }
}
