package pages;

import components.CatalogueTileComponent;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public final class ItemPage extends AbsPage {
    private final By itemPicSelector = By.cssSelector(".inventory_details_img");
    private final By itemNameSelector = By.cssSelector(".inventory_details_name");
    private final By itemDescriptionSelector = By.cssSelector(".inventory_details_desc");
    private final By itemPriceSelector = By.cssSelector(".inventory_details_price");
    private final By returnToCatalogueButtonSelector = By.cssSelector("#back-to-products");
    private final By removeItemFromCartButtonSelector = By.cssSelector("button[id*='remove']");
    private final By addItemToCartButtonSelector = By.cssSelector("button[id*='add-to-cart']");
    private final By shoppingCartBadgeSelector = By.cssSelector(".shopping_cart_badge");

    public ItemPage(final WebDriver driver, final String itemId) {
        super(driver, "/inventory-item.html?id=" + itemId);
    }

    public void returnToCatalogue() {
        $(returnToCatalogueButtonSelector).click();
    }

    public void compareInfo(final CatalogueTileComponent catalogueTileComponent) {
        Assertions.assertEquals(catalogueTileComponent.getItemImageLink(), $(itemPicSelector).getText());
        Assertions.assertEquals(catalogueTileComponent.getItemName(), $(itemNameSelector).getText());
        Assertions.assertEquals(catalogueTileComponent.getItemDescription(), $(itemDescriptionSelector).getText());
        Assertions.assertEquals(catalogueTileComponent.getItemPrice(), $(itemPriceSelector).getText());
        returnToCatalogue();
    }

    public void compareWrongInfo(final CatalogueTileComponent catalogueTileComponent) {
        Assertions.assertNotSame(catalogueTileComponent.getItemImageLink(), $(itemPicSelector).getText());
        Assertions.assertNotEquals(catalogueTileComponent.getItemName(), $(itemNameSelector).getText());
        Assertions.assertNotEquals(catalogueTileComponent.getItemDescription(), $(itemDescriptionSelector).getText());
        Assertions.assertNotEquals(catalogueTileComponent.getItemPrice(), $(itemPriceSelector).getText());
        returnToCatalogue();
    }

    public void checkItemAddedToCart(final int itemsInCart) {
        if ($(removeItemFromCartButtonSelector).getText().equals("Remove")) {
            Assertions.assertEquals(itemsInCart, Integer.parseInt($(shoppingCartBadgeSelector).getText()));
        } else {
            Assertions.assertTrue(waiter.waitForCondition(ExpectedConditions.visibilityOf($(addItemToCartButtonSelector))));
        }
        driver.navigate().back();
    }

}