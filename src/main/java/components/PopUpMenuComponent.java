package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PopUpMenuComponent extends AbsComponent{
    private String popUpMenuComponentButtonSelector = "#react-burger-menu-btn";
    private String logOutButtonSelector = "#logout_sidebar_link";

    private String resetAppStateButtonSelector = "#reset_sidebar_link";
    private String closePopupMenuButtonSelector = "#react-burger-cross-btn";
    public PopUpMenuComponent(WebDriver driver) {
        super(driver);
    }

    public void logOut() {
        $(By.cssSelector(popUpMenuComponentButtonSelector)).click();
        waiter.waitForVisibility(By.cssSelector(logOutButtonSelector));
        $(By.cssSelector(logOutButtonSelector)).click();
    }

    public void resetAppState() {
        $(By.cssSelector(popUpMenuComponentButtonSelector)).click();
        waiter.waitForVisibility(By.cssSelector(resetAppStateButtonSelector));
        $(By.cssSelector(resetAppStateButtonSelector));
        $(By.cssSelector(closePopupMenuButtonSelector)).click();
    }
}
