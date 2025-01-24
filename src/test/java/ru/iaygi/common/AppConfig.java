package ru.iaygi.common;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class AppConfig {
    private final Config config;

    public AppConfig(String resourceName) {
        this.config = ConfigFactory.load(resourceName);
    }

    public String getString(String key) {
        return config.getString(key);
    }

    public int getInt(String key) {
        return config.getInt(key);
    }

    public boolean getBoolean(String key) {
        return config.getBoolean(key);
    }
}
