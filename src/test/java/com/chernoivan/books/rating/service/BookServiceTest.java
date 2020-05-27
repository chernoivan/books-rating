package com.chernoivan.books.rating.service;

import com.chernoivan.books.rating.Application;
import com.chernoivan.books.rating.domain.ApplicationUser;
import com.chernoivan.books.rating.domain.Assessment;
import com.chernoivan.books.rating.domain.Book;
import com.chernoivan.books.rating.domain.enums.AccessLevelType;
import com.chernoivan.books.rating.domain.enums.AssessmentType;
import com.chernoivan.books.rating.domain.enums.BookStatus;
import com.chernoivan.books.rating.domain.enums.UserRoleType;
import com.chernoivan.books.rating.dto.applicationuser.ApplicationUserPutDTO;
import com.chernoivan.books.rating.dto.applicationuser.ApplicationUserReadDTO;
import com.chernoivan.books.rating.dto.book.BookCreateDTO;
import com.chernoivan.books.rating.dto.book.BookPatchDTO;
import com.chernoivan.books.rating.dto.book.BookPutDTO;
import com.chernoivan.books.rating.dto.book.BookReadDTO;
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

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(statements = {
        "delete from assessment",
        "delete from book",
        "delete from application_user" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BookServiceTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Test
    public void testGetBook() {
        Book book = createBook();

        BookReadDTO readDTO = bookService.getBook(book.getId());
        Assertions.assertThat(readDTO).isEqualToComparingFieldByField(book);
    }

    @Test
    public void testCreateBook() {
        BookCreateDTO create = new BookCreateDTO();
        create.setBookRating(5.0);
        create.setBookStatus(BookStatus.UNRELEASED);
        create.setTitle("Hello");

        BookReadDTO read = bookService.createBook(create);
        Assertions.assertThat(create).isEqualToComparingFieldByField(read);
        Assert.assertNotNull(read.getId());

        Book book = bookRepository.findById(read.getId()).get();
        Assertions.assertThat(read).isEqualToComparingFieldByField(book);
    }

    @Test
    public void testPatchBook() {
        Book book = createBook();

        BookPatchDTO patch = new BookPatchDTO();
        patch.setBookRating(5.0);
        patch.setInfo("bla bla bla");
        patch.setBookStatus(BookStatus.UNRELEASED);
        patch.setTitle("Hello");
        patch.setReleaseDate(LocalDate.now());

        BookReadDTO read = bookService.patchBook(book.getId(), patch);

        Assertions.assertThat(patch).isEqualToComparingFieldByField(read);

        book = bookRepository.findById(read.getId()).get();
        Assertions.assertThat(book).isEqualToIgnoringGivenFields(read, "items", "authors", "bookGenres");

    }

    @Test
    public void testPatchBookEmptyPatch() {
        Book book = createBook();

        BookPatchDTO patch = new BookPatchDTO();
        BookReadDTO read = bookService.patchBook(book.getId(), patch);

        Assert.assertNotNull(read.getBookRating());
        Assert.assertNotNull(read.getBookStatus());
        Assert.assertNotNull(read.getTitle());
        Assert.assertNotNull(read.getInfo());
        Assert.assertNotNull(read.getReleaseDate());

        inTransaction(() -> {
            Book bookAfterUpdate = bookRepository.findById(read.getId()).get();

            Assert.assertNotNull(bookAfterUpdate.getBookRating());
            Assert.assertNotNull(bookAfterUpdate.getBookStatus());
            Assert.assertNotNull(bookAfterUpdate.getInfo());
            Assert.assertNotNull(bookAfterUpdate.getTitle());
            Assert.assertNotNull(bookAfterUpdate.getReleaseDate());
            Assertions.assertThat(book).isEqualToIgnoringGivenFields(bookAfterUpdate, "items", "bookGenres", "authors");
        });
    }

    @Test
    public void testPutUser() {
        Book book = createBook();

        BookPutDTO put = new BookPutDTO();
        put.setBookRating(5.0);
        put.setTitle("1234");

        BookReadDTO read = bookService.updateBook(book.getId(), put);

        Assertions.assertThat(put).isEqualToComparingFieldByField(read);

        book = bookRepository.findById(read.getId()).get();
        Assertions.assertThat(book).isEqualToIgnoringGivenFields(read, "items", "bookGenres", "authors");
    }

    @Test
    public void testUpdateRating() {
        Book book = createBook1();
        ApplicationUser applicationUser = createUser();

        createAssessment(applicationUser, book, 7);
        createAssessment(applicationUser, book, 9);

        bookService.updateAverageMark(book.getId());
        book = bookRepository.findById(book.getId()).get();
        Assert.assertEquals(8.0, book.getBookRating(), Double.MIN_NORMAL);
    }

    private Assessment createAssessment(ApplicationUser applicationUser, Book book, Integer rating) {
        Assessment assessment = new Assessment();
        assessment.setAssessmentText("great movie");
        assessment.setAssessmentType(AssessmentType.FILM_ASSESSMENT);
        assessment.setLikesCount(23);
        assessment.setRating(rating);
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

    private Book createBook1() {
        Book book = new Book();
        book.setBookRating(5.0);
        book.setBookStatus(BookStatus.UNRELEASED);
        book.setInfo("bla bla bla");
        book.setTitle("Hello Word");
        book.setReleaseDate(LocalDate.now());
        return bookRepository.save(book);
    }

    private Book createBook() {
        Book book = new Book();
        book.setBookRating(5.0);
        book.setBookStatus(BookStatus.UNRELEASED);
        book.setInfo("bla bla bla");
        book.setTitle("Hello Word");
        book.setReleaseDate(LocalDate.now());
        return bookRepository.save(book);
    }

    private void inTransaction(Runnable runnable) {
        transactionTemplate.executeWithoutResult(status -> {
            runnable.run();
        });
    }
}
