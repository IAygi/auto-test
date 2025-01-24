package ru.iaygi.jdbc.service;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

import static ru.iaygi.jdbc.data.DbData.*;

@Slf4j
public class DbConnect {

    @Step("Create connection & statement")
    public Statement prepareToRequest() {
        log.debug("DB connection");

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            log.debug("Successfully connected to the database");
            return statement;
        } catch (SQLException e) {
            log.debug("Connection error!");
            throw new RuntimeException(e);
        }
    }

    @Step("SQL select")
    public ResultSet queryRequest(String sqlRequest) {
        log.debug("queryRequest " + sqlRequest);

        try (ResultSet resultSet = prepareToRequest().executeQuery(sqlRequest)) {
            log.debug("JDBC executeQuery successfully");
            return resultSet;
        } catch (SQLException e) {
            log.debug("JDBC executeQuery error!");
            throw new RuntimeException(e);
        }

    }

    @Step("SQL insert")
    public void executeRequest(String sqlRequest) {
        log.debug("executeRequest " + sqlRequest);

        try {
            prepareToRequest().execute(sqlRequest);
            log.debug("JDBC execute successfully");
        } catch (SQLException e) {
            log.debug("JDBC execute error!");
            throw new RuntimeException(e);
        }
    }
}
