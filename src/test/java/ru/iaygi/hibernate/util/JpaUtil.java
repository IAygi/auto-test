package ru.iaygi.hibernate.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import ru.iaygi.common.AppConfig;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JpaUtil {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory() {
        try {
            log.info("Creating EntityManagerFactory...");
            AppConfig config = new AppConfig("application");
            String dbUrl = config.getString("db.url");
            String dbUsername = config.getString("db.username");
            String dbPassword = config.getString("db.password");

            Map<String, String> persistence = new HashMap<>();
            persistence.put("jakarta.persistence.jdbc.url", dbUrl);
            persistence.put("jakarta.persistence.jdbc.user", dbUsername);
            persistence.put("jakarta.persistence.jdbc.password", dbPassword);

            return Persistence.createEntityManagerFactory("testPU", persistence);
        } catch (Exception e) {
            log.error("Failed to create EntityManagerFactory", e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static EntityManager getEntityManager() {
        log.debug("Creating EntityManager...");
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public static void close() {
        log.info("Closing EntityManagerFactory...");
        ENTITY_MANAGER_FACTORY.close();
    }
}
