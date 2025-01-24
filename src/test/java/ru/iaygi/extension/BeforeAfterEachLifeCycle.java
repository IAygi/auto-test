package ru.iaygi.extension;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

@Slf4j
public class BeforeAfterEachLifeCycle implements BeforeEachCallback, AfterEachCallback {

    private Class<?> currentClass;

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        log.debug("BeforeAfterEachLifecycle beforeEach extension");

        currentClass = extensionContext.getRequiredTestClass();
        currentClass.getField("isCreate").setBoolean(boolean.class, true);
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        log.debug("BeforeAfterEachLifecycle afterEach extension");

        currentClass.getField("isCreate").setBoolean(boolean.class, false);
    }
}
