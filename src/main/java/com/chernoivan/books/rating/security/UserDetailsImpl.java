package com.chernoivan.books.rating.security;

import com.chernoivan.books.rating.domain.ApplicationUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.stream.Collectors;

@Getter
@Setter
public class UserDetailsImpl extends org.springframework.security.core.userdetails.User {

    public UserDetailsImpl(ApplicationUser user) {
        super(user.getEmail(), user.getEncodedPassword(), user.getUserRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getUserType().toString())).collect(Collectors.toList()));
    }
}
