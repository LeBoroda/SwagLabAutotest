import exceptions.BrowserNotSupportedException;
import factories.WebDriverFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.MainPage;

public class SwagLabLoginTest {
    private WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void init() throws BrowserNotSupportedException {
        driver = new WebDriverFactory().createDriver();
    }

    @AfterEach
    public void close() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

    @Test
    public void testSwagLabLogin() {
        new MainPage(driver)
                .open();
    }
}
