package com.chernoivan.books.rating.repository;

import com.chernoivan.books.rating.domain.Book;
import com.chernoivan.books.rating.domain.BookGenre;
import com.chernoivan.books.rating.domain.enums.BookGenres;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(statements = "delete from book_genre", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
public class BookGenreRepositoryTest {

    @Autowired
    private BookGenreRepository bookGenreRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testSave() {
        BookGenre bookGenre = new BookGenre();
        Book book = createBook();
        bookGenre.setBook(book);
        bookGenre = bookGenreRepository.save(bookGenre);
        assertNotNull(bookGenre.getId());
        assertTrue(bookGenreRepository.findById(bookGenre.getId()).isPresent());
    }

    @Test
    public void testCreatedAtIsSet() {
        Book book = createBook();
        BookGenre bookGenre = createBookGenre(book);

        Instant createdAtBeforeReload = bookGenre.getCreatedAt();
        Assert.assertNotNull(createdAtBeforeReload);
        bookGenre = bookGenreRepository.findById(bookGenre.getId()).get();

        Instant createdAtAfterReload = bookGenre.getCreatedAt();
        Assert.assertNotNull(createdAtAfterReload);
        Assert.assertEquals(createdAtBeforeReload, createdAtAfterReload);
    }

    @Test
    public void testUpdatedAtIsSet() {
        Book book = createBook();
        BookGenre bookGenre = createBookGenre(book);

        Instant updatedAtBeforeReload = bookGenre.getUpdatedAt();
        Assert.assertNotNull(updatedAtBeforeReload);
        bookGenre = bookGenreRepository.findById(bookGenre.getId()).get();

        Instant updatedAtAfterReload = bookGenre.getUpdatedAt();
        Assert.assertNotNull(updatedAtAfterReload);
        Assert.assertEquals(updatedAtBeforeReload, updatedAtAfterReload);
    }

    private BookGenre createBookGenre(Book book) {
        BookGenre bookGenre = new BookGenre();
        book = createBook();
        bookGenre.setBook(book);
        bookGenre.setBookGenres(BookGenres.ADVENTURE);
        return bookGenreRepository.save(bookGenre);
    }

    private Book createBook() {
        Book book = new Book();
        return bookRepository.save(book);
    }

}
