package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PopUpMenuComponent extends AbsComponent{
    private String popUpMenuComponentButtonId = "react-burger-menu-btn";
    private String logOutButtonId = "logout_sidebar_link";
    public PopUpMenuComponent(WebDriver driver) {
        super(driver);
    }

    public void logOut() {
        $(By.id(popUpMenuComponentButtonId)).click();
        waiter.waitForVisibility(By.id(logOutButtonId));
        $(By.id(logOutButtonId)).click();
    }
}
