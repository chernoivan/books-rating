package com.chernoivan.books.rating.service;

import com.chernoivan.books.rating.domain.Book;
import com.chernoivan.books.rating.domain.enums.BookStatus;
import com.chernoivan.books.rating.dto.book.BookCreateDTO;
import com.chernoivan.books.rating.dto.book.BookPatchDTO;
import com.chernoivan.books.rating.dto.book.BookReadDTO;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(statements = "delete from book", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BookServiceTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    public void testGetFilm() {
        Book book = createFilm();

        BookReadDTO readDTO = bookService.getBook(book.getId());
        Assertions.assertThat(readDTO).isEqualToComparingFieldByField(book);
    }

    @Test
    public void testCreateFilm() {
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
    public void testPatchFilm() {
        Book book = createFilm();

        BookPatchDTO patch = new BookPatchDTO();
        patch.setBookRating(5.0);
        patch.setInfo("bla bla bla");
        patch.setBookStatus(BookStatus.UNRELEASED);
        patch.setTitle("Hello");

        BookReadDTO read = bookService.patchBook(book.getId(), patch);

        Assertions.assertThat(patch).isEqualToComparingFieldByField(read);

        book = bookRepository.findById(read.getId()).get();
        Assertions.assertThat(book).isEqualToIgnoringGivenFields(read, "items", "authors", "bookGenres");

    }

    private Book createFilm() {
        Book book = new Book();
        book.setBookRating(5.0);
        book.setBookStatus(BookStatus.UNRELEASED);
        book.setInfo("bla bla bla");
        book.setTitle("Hello Word");
        return bookRepository.save(book);
    }

    private void inTransaction(Runnable runnable) {
        transactionTemplate.executeWithoutResult(status -> {
            runnable.run();
        });
    }
}
