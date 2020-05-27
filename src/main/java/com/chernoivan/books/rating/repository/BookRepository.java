package com.chernoivan.books.rating.repository;

import com.chernoivan.books.rating.domain.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface BookRepository extends CrudRepository<Book, UUID> {

    @Query(" select m.id from Book m")
    Stream<UUID> getIdsOfBooks();
}

