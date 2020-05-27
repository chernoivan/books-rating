package com.chernoivan.books.rating.service;

import com.chernoivan.books.rating.domain.Author;
import com.chernoivan.books.rating.dto.author.AuthorCreateDTO;
import com.chernoivan.books.rating.dto.author.AuthorPatchDTO;
import com.chernoivan.books.rating.dto.author.AuthorPutDTO;
import com.chernoivan.books.rating.dto.author.AuthorReadDTO;
import com.chernoivan.books.rating.repository.AuthorRepository;
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
@Sql(statements = "delete from author", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    public void testGetAuthor() {
        Author author = createAuthor();

        AuthorReadDTO read = authorService.getAuthor(author.getId());
        Assertions.assertThat(read).isEqualToComparingFieldByField(author);
    }

    @Test
    public void testCreateAuthor() {
        AuthorCreateDTO create = new AuthorCreateDTO();
        create.setFirstName("samurai");

        AuthorReadDTO read = authorService.createAuthor(create);
        Assertions.assertThat(create).isEqualToComparingFieldByField(read);
        Assert.assertNotNull(read.getId());

        Author author = authorRepository.findById(read.getId()).get();
        Assertions.assertThat(read).isEqualToComparingFieldByField(author);
    }

    @Test
    public void testPatchAuthor() {
        Author author = createAuthor();

        AuthorPatchDTO patch = new AuthorPatchDTO();
        patch.setLastName("sam");
        patch.setFirstName("samsam");
        patch.setAuthorRating(4.1);
        patch.setBiography("qwerty");
        patch.setDateOfBirth(LocalDate.now());

        AuthorReadDTO read = authorService.patchAuthor(author.getId(), patch);

        Assertions.assertThat(patch).isEqualToComparingFieldByField(read);

        author = authorRepository.findById(read.getId()).get();
        Assertions.assertThat(author).isEqualToIgnoringGivenFields(read, "authorBooks");
    }

    @Test
    public void testPatchAuthorEmptyPatch() {
        Author author = createAuthor();

        AuthorPatchDTO patch = new AuthorPatchDTO();
        AuthorReadDTO read = authorService.patchAuthor(author.getId(), patch);

        Assert.assertNotNull(read.getAuthorRating());
        Assert.assertNotNull(read.getBiography());
        Assert.assertNotNull(read.getDateOfBirth());
        Assert.assertNotNull(read.getFirstName());
        Assert.assertNotNull(read.getLastName());

        inTransaction(() -> {
            Author authorAfterUpdate = authorRepository.findById(read.getId()).get();

            Assert.assertNotNull(authorAfterUpdate.getAuthorRating());
            Assert.assertNotNull(authorAfterUpdate.getBiography());
            Assert.assertNotNull(authorAfterUpdate.getDateOfBirth());
            Assert.assertNotNull(authorAfterUpdate.getFirstName());
            Assert.assertNotNull(authorAfterUpdate.getLastName());
            Assertions.assertThat(author).isEqualToIgnoringGivenFields(authorAfterUpdate, "authorBooks");
        });
    }

    @Test
    public void testPutUser() {
        Author author = createAuthor();

        AuthorPutDTO put = new AuthorPutDTO();
        put.setLastName("sam");

        AuthorReadDTO read = authorService.updateAuthor(author.getId(), put);
        Assertions.assertThat(put).isEqualToComparingFieldByField(read);

        author = authorRepository.findById(read.getId()).get();
        Assertions.assertThat(author).isEqualToIgnoringGivenFields(read, "authorBooks");
    }

    @Test
    public void testDeleteAuthor() {
        Author author = createAuthor();

        authorService.deleteAuthor(author.getId());
        Assert.assertFalse(authorRepository.existsById(author.getId()));
    }

    private Author createAuthor() {
        Author author = new Author();
        author.setFirstName("samurai");
        author.setDateOfBirth(LocalDate.now());
        author.setLastName("hello");
        author.setBiography("hello World");
        author.setAuthorRating(10.0);
        return authorRepository.save(author);
    }
    private void inTransaction(Runnable runnable) {
        transactionTemplate.executeWithoutResult(status -> {
            runnable.run();
        });
    }
}
