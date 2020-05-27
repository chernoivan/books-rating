package com.chernoivan.books.rating.repository;

import com.chernoivan.books.rating.domain.ApplicationUser;
import com.chernoivan.books.rating.domain.Author;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(statements = "delete from author", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testSave() {
        Author author = new Author();
        author = authorRepository.save(author);
        assertNotNull(author.getId());
        assertTrue(authorRepository.findById(author.getId()).isPresent());
    }

    @Test
    public void testCreatedAtIsSet() {
        Author author = createAuthor();

        Instant createdAtBeforeReload = author.getCreatedAt();
        Assert.assertNotNull(createdAtBeforeReload);
        author = authorRepository.findById(author.getId()).get();

        Instant createdAtAfterReload = author.getCreatedAt();
        Assert.assertNotNull(createdAtAfterReload);
        Assert.assertEquals(createdAtBeforeReload, createdAtAfterReload);
    }

    @Test
    public void testUpdatedAtIsSet() {
        Author author = createAuthor();

        Instant updatedAtBeforeReload = author.getUpdatedAt();
        Assert.assertNotNull(updatedAtBeforeReload);
        author = authorRepository.findById(author.getId()).get();

        Instant updatedAtAfterReload = author.getUpdatedAt();
        Assert.assertNotNull(updatedAtAfterReload);
        Assert.assertEquals(updatedAtBeforeReload, updatedAtAfterReload);
    }

    private Author createAuthor() {
        Author author = new Author();
        author.setFirstName("Alex");
        author.setLastName("Qwerty");
        return authorRepository.save(author);
    }
}
