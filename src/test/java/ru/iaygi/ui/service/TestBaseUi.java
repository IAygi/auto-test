package ru.iaygi.ui.service;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static ru.iaygi.ui.data.TestData.*;

public class TestBaseUi {

    private final static String BROWSER_VERSION_MOON = "124.0.2478.109-6";
    private final static String BROWSER_VERSION_LOCAL = "124";
    private static RemoteWebDriver driver;

    @Step("Настройка конфигурации браузера")
    public static void initDriver(boolean useSelenoid) throws Exception {
        SelenideLogger.addListener("allure", new AllureSelenide());

        EdgeOptions options = new EdgeOptions();
        Configuration.baseUrl = "";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 8000;

        if (!useSelenoid) {
            Configuration.holdBrowserOpen = true;
            Configuration.browser = "edge";
            Configuration.browserCapabilities = options;
        }

        if (useSelenoid) {
            options.setCapability("browserVersion", BROWSER_VERSION_MOON);
            options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
            options.setCapability("selenoid:options", new HashMap<String, Object>() {
                {
                    put("name", "NCRM UI test -> " +
                            DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
                    put("sessionTimeout", "30m");
                    put("enableVNC", enableVNC);
                    put("screenResolution", "1920x1080x24");
                    put("env", new ArrayList<String>() {
                        {
                            add("TZ=UTC");
                        }
                    });
                    put("labels", new HashMap<String, Object>() {
                        {
                            put("manual", "true");
                        }
                    });
                    put("enableVideo", enableVideo);
                }
            });

            Map<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_setting_values.clipboard", 1);
            options.setExperimentalOption("prefs", prefs);

            if (options.getCapability("browserVersion").equals(BROWSER_VERSION_MOON)) {
                driver = new RemoteWebDriver(new URL(SELENOID), options);
            } else {
                driver = new RemoteWebDriver(new URL(SELENOID_LOCAL), options);
            }

            driver.manage().window().setSize(new Dimension(1920, 1080));
            WebDriverRunner.setWebDriver(driver);
        }
    }

    @Step("Закрытие драйвера")
    public static void closeDriver(boolean useSelenoid) {
        if (useSelenoid) {
            driver.quit();
        }
    }
}
