package com.chernoivan.books.rating.security;

import com.chernoivan.books.rating.domain.ApplicationUser;
import com.chernoivan.books.rating.repository.ApplicationUserRepository;
import com.chernoivan.books.rating.repository.UserRoleRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.IterableUtil;
import org.bitbucket.brunneng.br.Configuration;
import org.bitbucket.brunneng.br.RandomObjectGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserDetailsServiceImplTest {
    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private RandomObjectGenerator flatGenerator;

    @Test
    public void testLoadUserByUsername() {
        ApplicationUser user = transactionTemplate.execute(status -> {
            ApplicationUser au = generateFlatEntityWithoutId(ApplicationUser.class);
            au.setUserRoles(new ArrayList<>(IterableUtil.toCollection(userRoleRepository.findAll())));
            return applicationUserRepository.save(au);
        });

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        Assert.assertEquals(user.getEmail(), userDetails.getUsername());
        Assert.assertEquals(user.getEncodedPassword(), userDetails.getPassword());
        Assert.assertTrue(userDetails.getAuthorities().isEmpty());
        Assertions.assertThat(userDetails.getAuthorities())
                .extracting("authority").containsExactlyInAnyOrder(
                        user.getUserRoles().stream().map(ur -> ur.getUserType().toString()).toArray());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testUserNotFound() {
        userDetailsService.loadUserByUsername("wrong name");
    }

    private ApplicationUser generateFlatEntityWithoutId(Class<ApplicationUser> applicationUserClass) {

        Configuration c = new Configuration();
        c.setFlatMode(true);
        flatGenerator = new RandomObjectGenerator(c);

        ApplicationUser applicationUser;
        applicationUser = flatGenerator.generateRandomObject(applicationUserClass);
        applicationUser.setId(null);
        return applicationUser;
    }
}
