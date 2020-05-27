package com.chernoivan.books.rating.repository;

import com.chernoivan.books.rating.domain.Assessment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AssessmentRepository extends CrudRepository<Assessment, UUID> {

    List<Assessment> findByUserId(UUID userId);

    List<Assessment> findByIdAndUserId(UUID id, UUID userId);

    @Query("select avg(v.rating) from Assessment v where v.book.id = :bookId")
    Double calcAverageMarkOfBook(UUID bookId);

}
