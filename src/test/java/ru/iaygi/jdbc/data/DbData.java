package ru.iaygi.jdbc.data;

public class DbData {
    public static final String DB_URL = System.getProperty("db_url", "jdbc:postgresql://url/name");
    public static final String DB_USER = System.getProperty("db_user", "postgres");
    public static final String DB_PASSWORD = System.getProperty("db_password", "postgres");
}
