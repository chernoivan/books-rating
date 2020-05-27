package com.chernoivan.books.rating.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpdateRatingOfBooksJob {

    @Scheduled(cron = "${update.rating.of.books.job.cron}")
    public void updateRatingOfBooks() {
        log.info("Job started");

        log.info("Job finished");
    }
}
