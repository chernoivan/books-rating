package com.chernoivan.books.rating.service;

import com.chernoivan.books.rating.domain.ApplicationUser;
import com.chernoivan.books.rating.domain.Assessment;
import com.chernoivan.books.rating.domain.Book;
import com.chernoivan.books.rating.domain.enums.AccessLevelType;
import com.chernoivan.books.rating.domain.enums.AssessmentType;
import com.chernoivan.books.rating.domain.enums.UserRoleType;
import com.chernoivan.books.rating.dto.assessment.AssessmentCreateDTO;
import com.chernoivan.books.rating.dto.assessment.AssessmentPatchDTO;
import com.chernoivan.books.rating.dto.assessment.AssessmentPutDTO;
import com.chernoivan.books.rating.dto.assessment.AssessmentReadDTO;
import com.chernoivan.books.rating.exception.EntityNotFoundException;
import com.chernoivan.books.rating.repository.ApplicationUserRepository;
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
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(statements = {
        "delete from assessment",
        "delete from application_user",
        "delete from book"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AssessmentServiceTest {
    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private AssessmentService assessmentService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    public void testGetAssessment() {
        ApplicationUser applicationUser = createUser();
        Book book = createBook();
        Assessment assessment = createAssessment(applicationUser, book);

        AssessmentReadDTO readDTO = assessmentService.getAssessment(assessment.getId());
        Assertions.assertThat(assessment).isEqualToIgnoringGivenFields(readDTO, "user", "book", "items");
    }

    @Test
    public void testGetUserAssessments() {
        ApplicationUser applicationUser = createUser();
        Book book = createBook();
        Assessment assessment = createAssessment(applicationUser, book);
        Assessment assessment1 = createAssessment(applicationUser,book);

        List<AssessmentReadDTO> read = assessmentService.getAssessmentsByUser(applicationUser.getId());
        Assertions.assertThat(assessment).isEqualToIgnoringGivenFields(read.get(0), "user", "book", "items");
        Assertions.assertThat(assessment1).isEqualToIgnoringGivenFields(read.get(1), "user", "book", "items");
    }

    @Test
    public void testCreateAssessment() {
        ApplicationUser user = createUser();
        Book book = createBook();
        AssessmentCreateDTO create = new AssessmentCreateDTO();
        create.setAssessmentText("great movie");
        create.setAssessmentType(AssessmentType.FILM_ASSESSMENT);
        create.setLikesCount(21);
        create.setBookId(book.getId());
        create.setRating(8);
        create.setUserId(user.getId());

        AssessmentReadDTO read = assessmentService.createAssessment(user.getId(), create);
        Assertions.assertThat(create).isEqualToComparingFieldByField(read);
        Assert.assertNotNull(read.getId());

        Assessment assessment = assessmentRepository.findById(read.getId()).get();
        Assertions.assertThat(assessment).isEqualToIgnoringGivenFields(read, "user", "book", "items");
    }

    @Test
    public void testPatchAssessment() {
        ApplicationUser applicationUser = createUser();
        Book book = createBook();
        Assessment assessment = createAssessment(applicationUser, book);

        AssessmentPatchDTO patch = new AssessmentPatchDTO();
        patch.setAssessmentText("great movie");
        patch.setAssessmentType(AssessmentType.FILM_ASSESSMENT);
        patch.setLikesCount(21);
        patch.setRating(8);
        patch.setUserId(applicationUser.getId());
        patch.setBookId(book.getId());

        AssessmentReadDTO read = assessmentService.patchAssessment(applicationUser.getId(), assessment.getId(), patch);

        Assertions.assertThat(patch).isEqualToComparingFieldByField(read);

        assessment = assessmentRepository.findById(read.getId()).get();
        Assertions.assertThat(assessment).isEqualToIgnoringGivenFields(read, "items", "user", "book");
    }

    @Test
    public void testPutAssessment() {
        ApplicationUser applicationUser = createUser();
        Book book = createBook();
        Assessment assessment = createAssessment(applicationUser, book);

        AssessmentPutDTO put = new AssessmentPutDTO();
        put.setAssessmentType(AssessmentType.FILM_ASSESSMENT);
        put.setRating(8);
        put.setBookId(book.getId());
        put.setUserId(applicationUser.getId());
        AssessmentReadDTO read = assessmentService.updateAssessment(applicationUser.getId(), assessment.getId(), put);

        Assertions.assertThat(put).isEqualToComparingFieldByField(read);

        assessment = assessmentRepository.findById(read.getId()).get();
        Assertions.assertThat(assessment).isEqualToIgnoringGivenFields(read, "items", "user", "book");
    }

    @Test
    public void testPatchAssessmentEmptyPatch() {
        ApplicationUser applicationUser = createUser();
        Book book = createBook();
        Assessment assessment = createAssessment(applicationUser, book);

        AssessmentPatchDTO patch = new AssessmentPatchDTO();
        AssessmentReadDTO read = assessmentService.patchAssessment(applicationUser.getId(), assessment.getId(), patch);

        Assert.assertNotNull(read.getAssessmentText());
        Assert.assertNotNull(read.getAssessmentType());
        Assert.assertNotNull(read.getLikesCount());
        Assert.assertNotNull(read.getRating());
        Assert.assertNotNull(read.getUserId());


        inTransaction(() -> {
            Assessment assessmentAfterUpdate = assessmentRepository.findById(read.getId()).get();

            Assert.assertNotNull(assessmentAfterUpdate.getAssessmentText());
            Assert.assertNotNull(assessmentAfterUpdate.getAssessmentType());
            Assert.assertNotNull(assessmentAfterUpdate.getLikesCount());
            Assert.assertNotNull(assessmentAfterUpdate.getRating());
            Assert.assertNotNull(assessmentAfterUpdate.getUser());

            Assertions.assertThat(assessment).isEqualToIgnoringGivenFields(assessmentAfterUpdate, "items", "user", "book");
        });
    }

    @Test
    public void testDeleteAssessment() {
        ApplicationUser applicationUser = createUser();
        Book book = createBook();
        Assessment assessment = createAssessment(applicationUser, book);

        assessmentService.deleteAssessment(applicationUser.getId(), assessment.getId());
        Assert.assertFalse(assessmentRepository.existsById(assessment.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteAssessmentNotFound() {
        assessmentService.deleteAssessment(UUID.randomUUID(), UUID.randomUUID());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetAssessmentWrongId() {
        assessmentService.getAssessment(UUID.randomUUID());
    }

    @Test()
    public void testGetAssessmentByUserWrongId() {
        assessmentService.getAssessmentsByUser(UUID.randomUUID());
    }

    private Book createBook() {
        Book book = new Book();
        return bookRepository.save(book);
    }

    private Assessment createAssessment(ApplicationUser applicationUser, Book book) {
        Assessment assessment = new Assessment();
        assessment.setAssessmentText("great movie");
        assessment.setAssessmentType(AssessmentType.FILM_ASSESSMENT);
        assessment.setLikesCount(23);
        assessment.setRating(8);
        assessment.setUser(applicationUser);
        assessment.setBook(book);
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
