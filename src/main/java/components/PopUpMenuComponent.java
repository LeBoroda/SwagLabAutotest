package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class PopUpMenuComponent extends AbsComponent {
    private final By popUpMenuComponentButtonSelector = By.cssSelector("#react-burger-menu-btn");
    private final By logOutButtonSelector = By.cssSelector("#logout_sidebar_link");
    private final By resetAppStateButtonSelector = By.cssSelector("#reset_sidebar_link");
    private final By closePopupMenuButtonSelector = By.cssSelector("#react-burger-cross-btn");

    public PopUpMenuComponent(final WebDriver driver) {
        super(driver);
    }

    public void logOut() {
        $(popUpMenuComponentButtonSelector).click();
        waiter.waitForVisibility(logOutButtonSelector);
        $(logOutButtonSelector).click();
    }

    public void resetAppState() {
        $(popUpMenuComponentButtonSelector).click();
        waiter.waitForVisibility(resetAppStateButtonSelector);
        $(resetAppStateButtonSelector);
        $(closePopupMenuButtonSelector).click();
    }

}