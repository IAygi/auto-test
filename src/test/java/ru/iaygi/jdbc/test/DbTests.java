package ru.iaygi.jdbc.test;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import ru.iaygi.jdbc.object.DbMethods;
import ru.iaygi.jdbc.service.DbConnect;

import java.sql.ResultSet;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.CRITICAL;

@Disabled
@Severity(CRITICAL)
@Tag("db_test")
@Epic("Users")
@Feature("Работа с пользователями через БД")
public class DbTests extends DbConnect {

    private ResultSet resultSet;
    private DbMethods dbMethods = new DbMethods();

    @BeforeEach
    void prepare() {

    }

    @AfterEach
    void cleanup() {
    }

    //    @Test
    void getTest() {
        step("Сделать SQL запрос на получение покупателей", () -> {

        });
    }
}
