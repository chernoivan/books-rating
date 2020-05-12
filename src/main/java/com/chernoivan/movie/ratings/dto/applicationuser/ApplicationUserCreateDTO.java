package com.chernoivan.movie.ratings.dto.applicationuser;

import com.chernoivan.movie.ratings.domain.enums.AccessLevelType;
import com.chernoivan.movie.ratings.domain.enums.UserRoleType;
import lombok.Data;

@Data
public class ApplicationUserCreateDTO {

    private String username;
    private String email;
    private AccessLevelType access;
    private UserRoleType userType;
}
