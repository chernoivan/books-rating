package com.chernoivan.books.rating.security;

import com.chernoivan.books.rating.domain.ApplicationUser;
import com.chernoivan.books.rating.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = applicationUserRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " is not found!");
        }
        return new UserDetailsImpl(user);
    }
}
