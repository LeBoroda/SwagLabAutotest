package components;

import logging.ILoggable;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CatalogueTileComponent extends AbsComponent implements ILoggable {
    private final String itemImgSelectorTemplate = ".inventory_item_img";
    private final String itemNameSelectorTemplate = ".inventory_item_name";
    private final String itemDescriptionSelectorTemplate = ".inventory_item_desc";
    private final String itemPriceSelectorTemplate = ".inventory_item_price";
    private final String itemIdSelectorTemplate = "%s div[class='inventory_item_img'] a";
    private final By addItemToCartSelector = By.cssSelector("button[id*='add-to-cart']");
    private final By removeItemFromCartSelector = By.cssSelector("button[id*='remove']");
    private String itemImageLink;
    private String itemName;
    private String itemDescription;
    private String itemPrice;

    public CatalogueTileComponent(final WebDriver driver) {
        super(driver);
    }

    public void getTileInfo(final String tileSelector) {
        itemImageLink = $(By.cssSelector(String.format("%s %s", tileSelector, itemImgSelectorTemplate))).getText();
        itemName = $(By.cssSelector(String.format("%s %s", tileSelector, itemNameSelectorTemplate))).getText();
        itemDescription = $(By.cssSelector(String.format("%s %s", tileSelector, itemDescriptionSelectorTemplate))).getText();
        itemPrice = $(By.cssSelector(String.format("%s %s", tileSelector, itemPriceSelectorTemplate))).getText();
    }

    public void goToItemPage(final String tileSelector) {
        $(By.cssSelector(String.format(String.format("%s %s", tileSelector, itemNameSelectorTemplate)))).click();
    }

    public String getItemId(final String tileSelector) {
        String elementId = $(By.cssSelector(String.format(itemIdSelectorTemplate, tileSelector))).getCssValue("id");
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(elementId);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return null;
        }
    }

    public boolean addItemToCartFromCatalogue() {
        WebElement itemButton = $(addItemToCartSelector);
        String buttonText = itemButton.getText();
        if (buttonText.equals("Add to cart")) {
            Assertions.assertEquals("Add to cart", itemButton.getText());
            itemButton.click();
            waiter.waitForVisibility(removeItemFromCartSelector);
            Assertions.assertEquals("Remove", $(removeItemFromCartSelector).getText());
            return true;
        } else {
            log().info("Item is not added to cart");
            return false;
        }
    }

    public String getItemImageLink() {
        return itemImageLink;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getItemPrice() {
        return itemPrice;
    }

}