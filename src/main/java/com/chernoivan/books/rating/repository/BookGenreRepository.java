package com.chernoivan.books.rating.repository;

import com.chernoivan.books.rating.domain.BookGenre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookGenreRepository extends CrudRepository<BookGenre, UUID> {
}
