package com.chernoivan.books.rating.repository;


import com.chernoivan.books.rating.dto.userrole.UserRoleReadDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = "spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.xml")
@Sql(statements = {
        "delete from assessment_rating",
        "delete from assessment",
        "delete from application_user",
        "delete from user_role",
        "delete from book_genre",
        "delete from book",
        "delete from author"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class LiquibaseLoadDataTest {
    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private AssessmentRatingRepository assessmentRatingRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookGenreRepository bookGenreRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testDataLoader() {
        Assert.assertTrue(applicationUserRepository.count() > 0);
        Assert.assertTrue(assessmentRepository.count() > 0);
        Assert.assertTrue(assessmentRatingRepository.count() > 0);
        Assert.assertTrue(bookRepository.count() > 0);
        Assert.assertTrue(bookGenreRepository.count() > 0);
        Assert.assertTrue(authorRepository.count() > 0);
    }
}
