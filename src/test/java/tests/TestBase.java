package tests;

import attach.Attach;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.junit5.BrowserPerTestStrategyExtension;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.Browser;
import config.ConfigReader;
import config.BaseConfig;
import config.WebConfig;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

    @ExtendWith({BrowserPerTestStrategyExtension.class})
    public class TestBase {
        private static final WebConfig webConfig = ConfigReader.Instance.read();
        private static final BaseConfig baseConfig = new BaseConfig(webConfig);

        @BeforeAll
        public static void setUp() {
            SelenideLogger.addListener("allure", new AllureSelenide());
            baseConfig.webConfig();
            baseConfig.apiConfig();
        }

        @AfterEach
        void addAttachments() {
            Attach.screenshotAs("Last screenshot");
            Attach.pageSource();
            if (Configuration.browser.equals(Browser.CHROME.name())) {
                Attach.browserConsoleLogs();
            }
            if (baseConfig.varRemote()) {
                Attach.addVideo(baseConfig.getVideoStorageUrl());
            }
        }
}
