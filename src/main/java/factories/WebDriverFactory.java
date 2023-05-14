package factories;

import data.BrowserData;
import exceptions.BrowserNotSupportedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public final class WebDriverFactory implements IWebDriverFactory {
    private final String browserName = System.getProperty("browser", "chrome");

    @Override
    public WebDriver createDriver() throws BrowserNotSupportedException {
        switch (BrowserData.valueOf(browserName.toUpperCase())) {
            case CHROME:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                return new ChromeDriver(chromeOptions);
            default:
                throw new BrowserNotSupportedException(browserName);
        }
    }

}