package com.chernoivan.books.rating.dto.applicationuser;

import com.chernoivan.books.rating.domain.enums.AccessLevelType;
import lombok.Data;


@Data
public class ApplicationUserPatchDTO {
    private String username;
    private String email;
    private String encodedPassword;
    private AccessLevelType access;
}
