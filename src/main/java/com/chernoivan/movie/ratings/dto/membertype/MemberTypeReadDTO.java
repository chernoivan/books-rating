package com.chernoivan.movie.ratings.dto.membertype;

import com.chernoivan.movie.ratings.domain.enums.CreativeMemberType;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class MemberTypeReadDTO {
    private UUID id;
    private CreativeMemberType creativeMemberType;

    private Instant createdAt;
    private Instant updatedAt;
}
