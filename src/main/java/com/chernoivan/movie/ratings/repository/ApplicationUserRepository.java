package com.chernoivan.movie.ratings.repository;

import com.chernoivan.movie.ratings.domain.ApplicationUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, UUID> {
}