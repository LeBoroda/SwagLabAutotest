package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class CatalogueHeaderComponent extends AbsComponent{

    private String headerTitleClassSelector = "title";

    public CatalogueHeaderComponent(WebDriver driver) {
        super(driver);
    }

    public WebElement checkHeaderVisibility() {
        return $(By.className(headerTitleClassSelector));
    }
}

