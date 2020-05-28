package com.chernoivan.books.rating.dto.userrole;

import com.chernoivan.books.rating.domain.enums.UserRoleType;
import lombok.Data;

import java.util.UUID;

@Data
public class UserRoleReadDTO {
    private UUID id;
    private UserRoleType userType;
}
