package com.chernoivan.books.rating.repository;

import com.chernoivan.books.rating.domain.Book;
import com.chernoivan.books.rating.domain.enums.BookStatus;
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
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(statements = "delete from book", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testSave() {
        Book f = new Book();
        f = bookRepository.save(f);
        assertNotNull(f.getId());
        assertTrue(bookRepository.findById(f.getId()).isPresent());
    }

    @Test
    public void testCreatedAtIsSet() {
        Book book = createFilm();

        Instant createdAtBeforeReload = book.getCreatedAt();
        Assert.assertNotNull(createdAtBeforeReload);
        book = bookRepository.findById(book.getId()).get();

        Instant createdAtAfterReload = book.getCreatedAt();
        Assert.assertNotNull(createdAtAfterReload);
        Assert.assertEquals(createdAtBeforeReload, createdAtAfterReload);
    }

    @Test
    public void testUpdatedAtIsSet() {
        Book book = createFilm();

        Instant updatedAtBeforeReload = book.getUpdatedAt();
        Assert.assertNotNull(updatedAtBeforeReload);
        book = bookRepository.findById(book.getId()).get();

        Instant updatedAtAfterReload = book.getUpdatedAt();
        Assert.assertNotNull(updatedAtAfterReload);
        Assert.assertEquals(updatedAtBeforeReload, updatedAtAfterReload);
    }

    private Book createFilm() {
        Book book = new Book();
        book.setBookRating(5.0);
        book.setBookStatus(BookStatus.UNRELEASED);
        book.setInfo("bla bla bla");
        book.setTitle("Hello Word");
        return bookRepository.save(book);
    }
}
