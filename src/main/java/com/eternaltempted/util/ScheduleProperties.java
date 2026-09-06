package com.eternaltempted.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ScheduleProperties {

    private static final Properties properties = new Properties();

    public ScheduleProperties() {
        loadProperties();
    }

    public void loadProperties() {
        try (InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("schedule.properties")
        ) {

            if (inputStream == null) {
                throw new RuntimeException("schedule.properties not found");
            }

            properties.load(inputStream);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to load schedule properties",
                    e
            );
        }
    }

    public String getGroup() {
        return properties.getProperty("group");
    }

    public String getStudent() {
        return properties.getProperty("student");
    }

}
