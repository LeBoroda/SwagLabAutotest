package pages;

import components.CatalogueTileComponent;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public final class ItemPage extends AbsPage {
    private final String itemPicSelector = ".inventory_details_img";
    private final String itemNameSelector = ".inventory_details_name";
    private final String itemDescriptionSelector = ".inventory_details_desc";
    private final String itemPriceSelector = ".inventory_details_price";
    private final String returnToCatalogueButtonSelector = "#back-to-products";
    private final String removeItemFromCartButtonSelector = "button[id*='remove']";
    private final String addItemToCartButtonSelector = "button[id*='add-to-cart']";
    private final String shoppingCartBadgeSelector = ".shopping_cart_badge";

    public ItemPage(final WebDriver driver, final String itemId) {
        super(driver, "/inventory-item.html?id=" + itemId);
    }

    public void returnToCatalogue() {
        $(By.cssSelector(returnToCatalogueButtonSelector)).click();
    }

    public void compareInfo(final CatalogueTileComponent catalogueTileComponent) {
        Assertions.assertEquals(catalogueTileComponent.getItemImageLink(), $(By.cssSelector(itemPicSelector)).getText());
        Assertions.assertEquals(catalogueTileComponent.getItemName(), $(By.cssSelector(itemNameSelector)).getText());
        Assertions.assertEquals(catalogueTileComponent.getItemDescription(), $(By.cssSelector(itemDescriptionSelector)).getText());
        Assertions.assertEquals(catalogueTileComponent.getItemPrice(), $(By.cssSelector(itemPriceSelector)).getText());
        returnToCatalogue();
    }

    public void compareWrongInfo(final CatalogueTileComponent catalogueTileComponent) {
        Assertions.assertNotSame(catalogueTileComponent.getItemImageLink(), $(By.cssSelector(itemPicSelector)).getText());
        Assertions.assertNotEquals(catalogueTileComponent.getItemName(), $(By.cssSelector(itemNameSelector)).getText());
        Assertions.assertNotEquals(catalogueTileComponent.getItemDescription(), $(By.cssSelector(itemDescriptionSelector)).getText());
        Assertions.assertNotEquals(catalogueTileComponent.getItemPrice(), $(By.cssSelector(itemPriceSelector)).getText());
        returnToCatalogue();
    }

    public void checkItemAddedToCart(final int itemsInCart) {
        if ($(By.cssSelector(removeItemFromCartButtonSelector)).getText().equals("Remove")) {
            Assertions.assertEquals(itemsInCart, Integer.parseInt($(By.cssSelector(shoppingCartBadgeSelector)).getText()));
        } else {
            Assertions.assertTrue(waiter.waitForCondition(ExpectedConditions.visibilityOf($(By.cssSelector(addItemToCartButtonSelector)))));
        }
        driver.navigate().back();
    }

}
