package com.chernoivan.books.rating.repository;

import com.chernoivan.books.rating.domain.ApplicationUser;
import com.chernoivan.books.rating.domain.enums.AccessLevelType;
import com.chernoivan.books.rating.domain.enums.UserRoleType;
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
@Sql(statements = "delete from application_user", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
public class ApplicationUserRepositoryTest {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Test
    public void testSave() {
        ApplicationUser c = new ApplicationUser();
        c = applicationUserRepository.save(c);
        assertNotNull(c.getId());
        assertTrue(applicationUserRepository.findById(c.getId()).isPresent());
    }

    @Test
    public void testCreatedAtIsSet() {
        ApplicationUser user = createUser();

        Instant createdAtBeforeReload = user.getCreatedAt();
        Assert.assertNotNull(createdAtBeforeReload);
        user = applicationUserRepository.findById(user.getId()).get();

        Instant createdAtAfterReload = user.getCreatedAt();
        Assert.assertNotNull(createdAtAfterReload);
        Assert.assertEquals(createdAtBeforeReload, createdAtAfterReload);
    }

    @Test
    public void testUpdatedAtIsSet() {
        ApplicationUser user = createUser();

        Instant updatedAtBeforeReload = user.getUpdatedAt();
        Assert.assertNotNull(updatedAtBeforeReload);
        user = applicationUserRepository.findById(user.getId()).get();

        Instant updatedAtAfterReload = user.getUpdatedAt();
        Assert.assertNotNull(updatedAtAfterReload);
        Assert.assertEquals(updatedAtBeforeReload, updatedAtAfterReload);
    }

    private ApplicationUser createUser() {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername("Alex");
        applicationUser.setEmail("alexchernoivan@gmail.com");
        applicationUser.setAccess(AccessLevelType.FULL_ACCESS);
        applicationUser.setUserType(UserRoleType.MODERATOR);
        return applicationUserRepository.save(applicationUser);
    }
}
