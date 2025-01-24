package ru.iaygi.extension;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

@Slf4j
public class BeforeEachCallbackLifecycle implements BeforeEachCallback {

    private boolean isCreate;

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        log.debug("BeforeEachCallbackLifecycle beforeEach extension");

        try {
            Class<?> currentClass = extensionContext.getRequiredTestClass();
            currentClass.getField("isCreate").setBoolean(isCreate, true);
        } catch (Exception e) {
            log.error("BeforeEachCallbackLifecycle error: NoSuchFieldException -> {}", e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }
}