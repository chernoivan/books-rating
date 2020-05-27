package com.chernoivan.books.rating.service;

import com.chernoivan.books.rating.domain.Book;
import com.chernoivan.books.rating.domain.BookGenre;
import com.chernoivan.books.rating.domain.enums.BookGenres;
import com.chernoivan.books.rating.dto.bookgenre.BookGenreCreateDTO;
import com.chernoivan.books.rating.dto.bookgenre.BookGenrePatchDTO;
import com.chernoivan.books.rating.dto.bookgenre.BookGenrePutDTO;
import com.chernoivan.books.rating.dto.bookgenre.BookGenreReadDTO;
import com.chernoivan.books.rating.exception.EntityNotFoundException;
import com.chernoivan.books.rating.repository.BookGenreRepository;
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
        "delete from book_genre",
        "delete from book"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BookGenreServiceTest {

    @Autowired
    private BookGenreRepository bookGenreRepository;

    @Autowired
    private BookGenreService bookGenreService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    public void testGetGenres() {
        Book book = createBook();
        BookGenre bookGenre = createBookGenre(book);
        BookGenre bookGenre1 = createBookGenre(book);
        bookGenre1.setBookGenres(BookGenres.ADVENTURE);

        List<BookGenreReadDTO> read = bookGenreService.getBookGenres(book.getId());
        Assertions.assertThat(bookGenre).isEqualToIgnoringGivenFields(read.get(0), "book");
        Assertions.assertThat(bookGenre1).isEqualToIgnoringGivenFields(read.get(1), "book");
    }

    @Test
    public void testCreateBookGenre() {
        BookGenreCreateDTO create = new BookGenreCreateDTO();
        Book book = createBook();
        create.setBookId(book.getId());
        create.setBookGenres(BookGenres.ADVENTURE);

        BookGenreReadDTO read = bookGenreService.createBookGenre(book.getId(), create);
        Assertions.assertThat(create).isEqualToComparingFieldByField(read);
        Assert.assertNotNull(read.getId());

        BookGenre bookGenre = bookGenreRepository.findById(read.getId()).get();
        Assertions.assertThat(bookGenre).isEqualToIgnoringGivenFields(read, "book");
    }

    @Test
    public void testPatchBookGenre() {
        Book book = createBook();
        BookGenre bookGenre = createBookGenre(book);

        BookGenrePatchDTO patch = new BookGenrePatchDTO();
        patch.setBookId(book.getId());
        patch.setBookGenres(BookGenres.ADVENTURE);

        BookGenreReadDTO read = bookGenreService.patchBookGenre(book.getId(), bookGenre.getId(), patch);

        Assertions.assertThat(patch).isEqualToComparingFieldByField(read);

        bookGenre = bookGenreRepository.findById(read.getId()).get();
        Assertions.assertThat(bookGenre).isEqualToIgnoringGivenFields(read, "book");
    }

    @Test
    public void testPatchBookGenreEmptyPatch() {
        Book book = createBook();
        BookGenre bookGenre = createBookGenre(book);

        BookGenrePatchDTO patch = new BookGenrePatchDTO();
        BookGenreReadDTO read = bookGenreService.patchBookGenre(bookGenre.getBook().getId(), bookGenre.getId(), patch);

        Assert.assertNotNull(read.getBookId());
        Assert.assertNotNull(read.getBookGenres());

        inTransaction(() -> {
            BookGenre bookGenreAfterUpdate = bookGenreRepository.findById(read.getId()).get();

            Assert.assertNotNull(bookGenreAfterUpdate.getBook());
            Assert.assertNotNull(bookGenreAfterUpdate.getBookGenres());
            Assertions.assertThat(bookGenre).isEqualToIgnoringGivenFields(bookGenreAfterUpdate, "book");
        });
    }

    @Test
    public void testPutUser() {
        Book book = createBook();
        BookGenre bookGenre = createBookGenre(book);

        BookGenrePutDTO put = new BookGenrePutDTO();
        put.setBookId(book.getId());
        put.setBookGenres(BookGenres.ADVENTURE);

        BookGenreReadDTO read = bookGenreService.updateBookGenre(book.getId(), bookGenre.getId(), put);

        Assertions.assertThat(put).isEqualToComparingFieldByField(read);

        bookGenre = bookGenreRepository.findById(read.getId()).get();
        Assertions.assertThat(bookGenre).isEqualToIgnoringGivenFields(read, "book");
    }

    @Test
    public void testDeleteBookGenre() {
        Book book = createBook();
        BookGenre bookGenre = createBookGenre(book);

        bookGenreService.deleteFilmGenre(book.getId(), bookGenre.getId());
        Assert.assertFalse(bookGenreRepository.existsById(bookGenre.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteBookGenreNotFound() {
        bookGenreService.deleteFilmGenre(UUID.randomUUID(), UUID.randomUUID());
    }

    private BookGenre createBookGenre(Book book) {
        BookGenre bookGenre = new BookGenre();
        bookGenre.setBook(book);
        bookGenre.setBookGenres(BookGenres.ADVENTURE);
        return bookGenreRepository.save(bookGenre);
    }

    private Book createBook() {
        Book book = new Book();
        return bookRepository.save(book);
    }

    private void inTransaction(Runnable runnable) {
        transactionTemplate.executeWithoutResult(status -> {
            runnable.run();
        });
    }
}
