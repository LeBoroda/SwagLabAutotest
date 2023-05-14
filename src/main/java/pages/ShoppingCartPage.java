package pages;

import components.CatalogueHeaderComponent;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public final class ShoppingCartPage extends AbsPage {
    private final String cartItemSelector = ".cart_item";
    private final String checkoutButton = "#checkout";
    private final CatalogueHeaderComponent catalogueHeaderComponent = new CatalogueHeaderComponent(driver);

    public ShoppingCartPage(final WebDriver driver) {
        super(driver, "/cart.html");
    }

    public void checkCart() {
        List<WebElement> cartItemsList = $$(By.cssSelector(cartItemSelector));
        Assertions.assertEquals(catalogueHeaderComponent.getCartIconNumber(), cartItemsList.size());
        $(By.cssSelector(checkoutButton)).click();
        new CheckoutStepOnePage(driver).getBuyerInfo();
    }

    public void checkProblemCart() {
        List<WebElement> cartItemsList = $$(By.cssSelector(cartItemSelector));
        Assertions.assertEquals(catalogueHeaderComponent.getCartIconNumber(), cartItemsList.size());
        $(By.cssSelector(checkoutButton)).click();
        new CheckoutStepOnePage(driver).getProblemBuyerInfo();
    }

}