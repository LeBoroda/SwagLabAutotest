package pages;

import org.openqa.selenium.WebDriver;
import pageobject.PageObject;

public abstract class AbsPage extends PageObject {
    private final String baseUrl = System.getProperty("base.url","https://www.saucedemo.com/");
    private String path;

    public AbsPage(WebDriver driver, String path) {
        super(driver);
        this.path = path;
    }

    public void open() {
        if (!path.startsWith("/")) {
            path += "/";
        }
        driver.get(baseUrl.replaceAll("\\/$","")+path);
    }

}
