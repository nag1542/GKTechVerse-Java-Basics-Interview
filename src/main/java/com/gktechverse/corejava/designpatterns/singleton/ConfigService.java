package com.gktechverse.corejava.designpatterns.singleton;

import java.util.Properties;

/**
 * Enum singleton. Preferred approach in Java.
 */
public enum ConfigService {
    INSTANCE;

    private final Properties config = loadConfig();

    public String get(String key) {
        return config.getProperty(key);
    }

    private Properties loadConfig() {
        Properties properties = new Properties();
        properties.setProperty("db.url", "jdbc:postgresql://localhost:5432/interviewdb");
        properties.setProperty("feature.singleton.demo", "enabled");
        return properties;
    }
}
