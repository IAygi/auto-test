package ru.iaygi.api.test;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.ResourceLock;
import ru.iaygi.annotation.Priority;
import ru.iaygi.api.helper.AttachmentHelper;
import ru.iaygi.extension.BeforeEachCallbackLifecycle;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.parallel.ResourceAccessMode.READ_WRITE;
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

    public static boolean isCreate = false;
    private static boolean isDelete = true;

    @BeforeEach
    public void prepare() {
    }

    @AfterEach
    public void clear() {
    }

    @Test
    @Owner("Aygi.IG")
    @Priority(HIGH)
    @ExtendWith(BeforeEachCallbackLifecycle.class)
    @DisplayName("test")
    @Description("test")
    void getList() {
        step("Получить список", () -> {
        });
    }
}