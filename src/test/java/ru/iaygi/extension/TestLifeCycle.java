package ru.iaygi.extension;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TestLifeCycle implements BeforeAllCallback, AfterAllCallback, ParameterResolver {

    private List<String> testValues;

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        log.debug("BsnUnitLifeCycle beforeAll extension");

        testValues = new ArrayList<>();
        testValues.add("one");
        testValues.add("two");
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        log.debug("BsnUnitLifeCycle afterAll extension");

        testValues.clear();
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
                                     ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == User.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext,
                                   ExtensionContext extensionContext) throws ParameterResolutionException {
        return new User(testValues);
    }
}