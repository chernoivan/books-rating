package com.chernoivan.books.rating.dto.applicationuser;

import com.chernoivan.books.rating.domain.enums.AccessLevelType;
import com.chernoivan.books.rating.domain.enums.UserRoleType;
import lombok.Data;

@Data
public class ApplicationUserPutDTO {
    private String username;
    private String email;
    private AccessLevelType access;
    private UserRoleType userType;
}
