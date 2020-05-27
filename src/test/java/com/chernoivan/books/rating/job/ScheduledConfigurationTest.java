package com.chernoivan.books.rating.job;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(ScheduledConfigurationTest.ScheduledTestConfig.class)
@ActiveProfiles("test")
public class ScheduledConfigurationTest {
    @Test
    public void testSpringContentUpAndRunning() {
        log.info("@Scheduled configurations are OK");
    }

    @EnableScheduling
    static class ScheduledTestConfig {
    }

}
