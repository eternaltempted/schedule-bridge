package com.eternaltempted.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

/**
 * Configures time-related dependencies used by the application.
 */
@Configuration
public class TimeConfiguration {

    /**
     * Provides the application's system clock.
     * @return a clock using the system's default time zone
     */
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
