package com.chernoivan.books.rating.repository;

import com.chernoivan.books.rating.domain.ApplicationUser;
import com.chernoivan.books.rating.domain.Assessment;
import com.chernoivan.books.rating.domain.Book;
import com.chernoivan.books.rating.domain.enums.AccessLevelType;
import com.chernoivan.books.rating.domain.enums.AssessmentType;
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

    @Autowired
    private BookRepository bookRepository;

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

    @Test
    public void testCalcAverageMark() {
        Book book = createBook();
        Book book1 = createBook();
        ApplicationUser user = createUser();

        createAssessmentForMark(user, book, 6);
        createAssessmentForMark(user, book, 8);
        createAssessmentForMark(user, book1, 10);

        Assert.assertEquals(7, assessmentRepository.calcAverageMarkOfBook(book.getId()), Double.MIN_NORMAL);
    }

    private Assessment createAssessment(ApplicationUser applicationUser) {
        Assessment assessment = new Assessment();
        assessment.setAssessmentText("great movie");
        assessment.setAssessmentType(AssessmentType.FILM_ASSESSMENT);
        assessment.setLikesCount(23);
        assessment.setUser(applicationUser);
        return assessmentRepository.save(assessment);
    }

    private Assessment createAssessmentForMark(ApplicationUser applicationUser, Book book, Integer rating) {
        Assessment assessment = new Assessment();
        assessment.setAssessmentText("great movie");
        assessment.setAssessmentType(AssessmentType.FILM_ASSESSMENT);
        assessment.setLikesCount(23);
        assessment.setUser(applicationUser);
        assessment.setBook(book);
        assessment.setRating(rating);
        return assessmentRepository.save(assessment);
    }

    private ApplicationUser createUser() {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername("Alex");
        applicationUser.setEmail("alexchernoivan@gmail.com");
        applicationUser.setAccess(AccessLevelType.FULL_ACCESS);
        return applicationUserRepository.save(applicationUser);
    }

    private Book createBook() {
        Book book = new Book();
        book.setTitle("Hello");
        return bookRepository.save(book);
    }
}
