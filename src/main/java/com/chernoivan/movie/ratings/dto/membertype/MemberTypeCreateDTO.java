package com.chernoivan.movie.ratings.dto.membertype;

import com.chernoivan.movie.ratings.domain.enums.CreativeMemberType;
import lombok.Data;

@Data
public class MemberTypeCreateDTO {
    private CreativeMemberType creativeMemberType;
}
