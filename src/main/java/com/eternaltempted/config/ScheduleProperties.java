package com.eternaltempted.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class ScheduleProperties {

    private static final Logger log = LoggerFactory.getLogger(ScheduleProperties.class);
    private static final Properties properties = new Properties();
    private static final String CONFIGURATION_FILE = "schedule.properties";

    public ScheduleProperties() {
        loadProperties();
    }

    public void loadProperties() {

        log.info(
                "Loading configuration from '{}'...",
                CONFIGURATION_FILE
        );

        try (InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream(CONFIGURATION_FILE)
        ) {

            if (inputStream == null) {
                log.error(
                        "Configuration file '{}' not found in classpath",
                        CONFIGURATION_FILE
                );
                throw new IllegalStateException(
                        "Configuration file missing: " + CONFIGURATION_FILE
                );
            }

            log.debug(
                    "Successfully loaded configuration from {}",
                    CONFIGURATION_FILE
            );
            properties.load(inputStream);
        } catch (IOException exception) {
            log.error(
                    "Failed to read configuration file {}",
                    CONFIGURATION_FILE, exception
            );
            throw new RuntimeException(
                    "Failed to load schedule properties",
                    exception
            );
        }
    }

    public String getGroup() {

        String group = properties.getProperty("group");

        if (group == null || group.isBlank()) {
            throw new IllegalStateException(
                    "Group is missing in configuration file: " + CONFIGURATION_FILE
            );
        }

        return group;
    }

    public String getStudent() {
        String student = properties.getProperty("student");

        if (student == null || student.isBlank()) {
            throw new IllegalStateException(
                    "Student is missing in configuration file: " + CONFIGURATION_FILE
            );
        }

        return student;
    }

}
