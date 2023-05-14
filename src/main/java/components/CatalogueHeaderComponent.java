package components;

import logging.ILoggable;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public final class CatalogueHeaderComponent extends AbsComponent implements ILoggable {

    private final By headerTitleSelector = By.cssSelector(".title");
    private final By shoppingCartButtonSelector = By.cssSelector(".shopping_cart_link");
    private final By itemsInCartCounterSelector = By.cssSelector(".shopping_cart_badge");

    public CatalogueHeaderComponent(final WebDriver driver) {
        super(driver);
    }

    public WebElement checkHeaderVisibility() {
        return $(headerTitleSelector);
    }

    public CatalogueHeaderComponent checkCartIcon(final int addedItems) {
        int cartIconNumber = Integer.parseInt($(itemsInCartCounterSelector).getText());
        Assertions.assertEquals(addedItems, cartIconNumber);
        return this;
    }

    public CatalogueHeaderComponent checkProblemCartIcon(final int addedItems) {
        int cartIconNumber = Integer.parseInt($(itemsInCartCounterSelector).getText());
        Assertions.assertNotEquals(addedItems, cartIconNumber);
        return this;
    }

    public void goToCart() {
        $(shoppingCartButtonSelector).click();
    }

    public int getCartIconNumber() {
        return Integer.parseInt($(itemsInCartCounterSelector).getText());
    }

}