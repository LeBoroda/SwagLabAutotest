import components.LoginBlockComponent;
import exceptions.BrowserNotSupportedException;
import factories.WebDriverFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.CataloguePage;
import pages.MainPage;

public class SwagLabTest {
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
        new LoginBlockComponent(driver)
                .runEmptyFieldLoginTest()
                .runBadPasswordLoginTest()
                .runBadUsernameLoginTest()
                .runLockedOutUserLoginTest()
                .runStandardUserLoginTest()
                .runProblemUserLoginTest()
                .runGlitchUserLoginTest();
    }

    @Test
    public void testSwagLabCatalogue() {
        new MainPage(driver)
                .open();
        new CataloguePage(driver)
                .runStandardCatalogueTest()
                .runGlitchCatalogueTest()
                .runProblemCatalogueTest();
    }

    @Test
    public void testItemCheckout() {
        new MainPage(driver)
                .open();
        new CataloguePage(driver)
                .runStandardCheckoutTest()
                .runGlitchCheckoutTest()
                .runProblemCheckoutTest();
    }

}