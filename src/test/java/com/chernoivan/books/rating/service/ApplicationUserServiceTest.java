package com.chernoivan.books.rating.service;

import com.chernoivan.books.rating.domain.ApplicationUser;
import com.chernoivan.books.rating.domain.enums.AccessLevelType;
import com.chernoivan.books.rating.domain.enums.UserRoleType;
import com.chernoivan.books.rating.dto.applicationuser.ApplicationUserCreateDTO;
import com.chernoivan.books.rating.dto.applicationuser.ApplicationUserPatchDTO;
import com.chernoivan.books.rating.dto.applicationuser.ApplicationUserPutDTO;
import com.chernoivan.books.rating.dto.applicationuser.ApplicationUserReadDTO;
import com.chernoivan.books.rating.exception.EntityNotFoundException;
import com.chernoivan.books.rating.repository.ApplicationUserRepository;
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


import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(statements = {
        "delete from assessment",
        "delete from assessment_rating",
        "delete from application_user"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ApplicationUserServiceTest {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private ApplicationUserService applicationUserService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    public void testGetUser() {
        ApplicationUser applicationUser = createUser();

        ApplicationUserReadDTO readDTO = applicationUserService.getUser(applicationUser.getId());
        Assertions.assertThat(readDTO).isEqualToComparingFieldByField(applicationUser);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetUserWrongId() {
        applicationUserService.getUser(UUID.randomUUID());
    }

    @Test
    public void testCreateUser() {
        ApplicationUserCreateDTO create = new ApplicationUserCreateDTO();
        create.setUsername("Alex");
        create.setEmail("alexchernoivan@gmail.com");
        create.setAccess(AccessLevelType.FULL_ACCESS);

        ApplicationUserReadDTO read = applicationUserService.createUser(create);
        Assertions.assertThat(create).isEqualToComparingFieldByField(read);
        Assert.assertNotNull(read.getId());

        ApplicationUser applicationUser = applicationUserRepository.findById(read.getId()).get();
        Assertions.assertThat(read).isEqualToComparingFieldByField(applicationUser);
    }

    @Test
    public void testPatchUser() {
        ApplicationUser applicationUser = createUser();

        ApplicationUserPatchDTO patch = new ApplicationUserPatchDTO();
        patch.setUsername("Stepan");
        patch.setEmail("newemail@gmail.com");
        patch.setAccess(AccessLevelType.FULL_ACCESS);

        ApplicationUserReadDTO read = applicationUserService.patchUser(applicationUser.getId(), patch);

        Assertions.assertThat(patch).isEqualToComparingFieldByField(read);

        applicationUser = applicationUserRepository.findById(read.getId()).get();
        Assertions.assertThat(applicationUser).isEqualToIgnoringGivenFields(read, "items", "assessments", "userRoles");
    }

    @Test
    public void testPutUser() {
        ApplicationUser applicationUser = createUser();

        ApplicationUserPutDTO put = new ApplicationUserPutDTO();
        put.setUsername("Stepan");
        put.setAccess(AccessLevelType.FULL_ACCESS);

        ApplicationUserReadDTO read = applicationUserService.updateUser(applicationUser.getId(), put);

        Assertions.assertThat(put).isEqualToComparingFieldByField(read);

        applicationUser = applicationUserRepository.findById(read.getId()).get();
        Assertions.assertThat(applicationUser).isEqualToIgnoringGivenFields(read, "items", "assessments", "userRoles");
    }

    @Test
    public void testPatchUserEmptyPatch() {
        ApplicationUser applicationUser = createUser();

        ApplicationUserPatchDTO patch = new ApplicationUserPatchDTO();
        ApplicationUserReadDTO read = applicationUserService.patchUser(applicationUser.getId(), patch);

        Assert.assertNotNull(read.getUsername());
        Assert.assertNotNull(read.getEmail());
        Assert.assertNotNull(read.getAccess());

        inTransaction(() -> {
            ApplicationUser applicationUserAfterUpdate = applicationUserRepository.findById(read.getId()).get();

            Assert.assertNotNull(applicationUserAfterUpdate.getUsername());
            Assert.assertNotNull(applicationUserAfterUpdate.getEmail());
            Assert.assertNotNull(applicationUserAfterUpdate.getAccess());
            Assertions.assertThat(applicationUser).isEqualToIgnoringGivenFields(applicationUserAfterUpdate, "items", "assessments", "userRoles");
        });
    }

    @Test
    public void testDeleteUser() {
        ApplicationUser applicationUser = createUser();

        applicationUserService.deleteUser(applicationUser.getId());
        Assert.assertFalse(applicationUserRepository.existsById(applicationUser.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteUserNonFound() {
        applicationUserService.deleteUser(UUID.randomUUID());
    }

    private ApplicationUser createUser() {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername("Alex");
        applicationUser.setEmail("alexchernoivan@gmail.com");
        applicationUser.setAccess(AccessLevelType.FULL_ACCESS);
        return applicationUserRepository.save(applicationUser);
    }

    private void inTransaction(Runnable runnable) {
        transactionTemplate.executeWithoutResult(status -> {
            runnable.run();
        });
    }
}
