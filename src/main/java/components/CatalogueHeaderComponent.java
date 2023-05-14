package components;

import logging.ILoggable;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public final class CatalogueHeaderComponent extends AbsComponent implements ILoggable {

    private final String headerTitleSelector = ".title";
    private final String sortButtonSelector = "product_sort_container";
    private final String shoppingCartButtonSelector = ".shopping_cart_link";
    private final String itemsInCartCounterSelector = ".shopping_cart_badge";

    public CatalogueHeaderComponent(final WebDriver driver) {
        super(driver);
    }

    public WebElement checkHeaderVisibility() {
        return $(By.cssSelector(headerTitleSelector));
    }

    public CatalogueHeaderComponent checkCartIcon(final int addedItems) {
        int cartIconNumber = Integer.parseInt($(By.cssSelector(itemsInCartCounterSelector)).getText());
        Assertions.assertEquals(addedItems, cartIconNumber);
        return this;
    }

    public CatalogueHeaderComponent checkProblemCartIcon(final int addedItems) {
        int cartIconNumber = Integer.parseInt($(By.cssSelector(itemsInCartCounterSelector)).getText());
        Assertions.assertNotEquals(addedItems, cartIconNumber);
        return this;
    }

    public void goToCart() {
        $(By.cssSelector(shoppingCartButtonSelector)).click();
    }

    public int getCartIconNumber() {
        return Integer.parseInt($(By.cssSelector(itemsInCartCounterSelector)).getText());
    }

}