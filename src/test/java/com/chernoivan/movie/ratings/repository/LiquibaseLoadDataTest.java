package com.chernoivan.movie.ratings.repository;


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
        "delete from application_user",
        "delete from assessment"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class LiquibaseLoadDataTest {
    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private AssessmentRatingRepository assessmentRatingRepository;

    @Test
    public void testDataLoader() {
        Assert.assertTrue(applicationUserRepository.count() > 0);
        Assert.assertTrue(assessmentRepository.count() > 0);
        Assert.assertTrue(assessmentRatingRepository.count() > 0);
    }
}
