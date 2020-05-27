package com.chernoivan.books.rating.job;

import com.chernoivan.books.rating.repository.BookRepository;
import com.chernoivan.books.rating.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class UpdateRatingOfBooksJob {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Scheduled(cron = "${update.rating.of.books.job.cron}")
    public void updateRatingOfBooks() {
        log.info("Job started");

        bookRepository.getIdsOfBooks().forEach(bookId -> {
            try {
                bookService.updateAverageMark(bookId);
            } catch (Exception e) {
                log.error("Failed to update average mark for book: {}", bookId, e);
            }
        });

        log.info("Job finished");
    }
}
