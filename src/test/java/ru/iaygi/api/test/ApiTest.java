package ru.iaygi.api.test;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.ResourceLock;
import ru.iaygi.annotation.Priority;
import ru.iaygi.api.helper.AttachmentHelper;
import ru.iaygi.dto.book.Response;
import ru.iaygi.exception.CustomException;

import java.io.FileReader;
import java.io.IOException;

import static io.qameta.allure.Allure.step;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.parallel.ResourceAccessMode.READ_WRITE;
import static ru.iaygi.api.specification.Conditions.statusCode;
import static ru.iaygi.common.PriorityValues.HIGH;
import static ru.iaygi.common.ResourceLockValues.BASE_READ_WRITE;

@Owner("Aygi.IG")
@Tags({@Tag("api_test"), @Tag("test_tag")})
@Epic("Вложения")
@Feature("AttachmentController")
@Story("BE")
@Link(name = "Controller", url = "url")
@ResourceLock(value = BASE_READ_WRITE, mode = READ_WRITE)
public class ApiTest extends AttachmentHelper {

    private Response response;

    @BeforeEach
    public void prepare() {
    }

    @AfterEach
    public void clear() {
    }

    @Test
    @Owner("Aygi.IG")
    @Priority(HIGH)
    @DisplayName("test")
    @Description("test")
    void getBooksList() {
        step("Получить список книг", () -> {
            response = attachmentRest.getAllBooks().shouldHave(statusCode(200)).getResponseAs(Response.class);
        });

        step("Проверить наличие книги", () -> {
            booksAssertion.checkResponse(response);
        });
    }

    @Test
    void testReadFileThrowsException() {
        Exception exception = assertThrows(IOException.class, () -> {
            new FileReader("non_existing_file.txt");
        });

        assertTrue(exception.getMessage().contains("non_existing_file"));
        System.out.println("=========== getMessage = " + exception.getMessage());
        assertTrue(exception.getMessage().contains("non_existing_file"));
    }

    @Test
    void excTwo() {
        try {
            textException(0);
        } catch (CustomException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testJenkins() {
        String testJson = System.getProperty("test_json", "{}");
        System.out.println("================= >>>> testJson = " + testJson);
    }

    private void textException(int num) throws CustomException {
        if (num == 0) {
            throw new CustomException("Капец!");
        } else {
            System.out.println("All good!");
        }
    }
}
