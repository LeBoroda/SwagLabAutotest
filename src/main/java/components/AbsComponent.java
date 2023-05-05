package components;

import org.openqa.selenium.WebDriver;
import pageobject.PageObject;

public abstract class AbsComponent extends PageObject {

    public AbsComponent(WebDriver driver) {
        super(driver);
    }
}
