package com.chernoivan.books.rating.dto.userrole;

import com.chernoivan.books.rating.domain.enums.UserRoleType;
import lombok.Data;

@Data
public class UserRolePutDTO {
    private UserRoleType userType;
}
