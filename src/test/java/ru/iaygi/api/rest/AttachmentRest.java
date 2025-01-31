package ru.iaygi.api.rest;

import io.qameta.allure.Step;
import ru.iaygi.api.specification.RestExecutor;

import static io.restassured.http.ContentType.JSON;

public class AttachmentRest {

    private static final String BASE_URL = "https://demoqa.com";

    @Step("Получить все книги")
    public RestExecutor getAllBooks() {
        RestExecutor request = new RestExecutor(BASE_URL)
                .contentType(JSON);
        request.get("/BookStore/v1/Books");

        return request;
    }
}
