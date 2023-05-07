package components;

import logging.ILoggable;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CatalogueTileComponent extends AbsComponent implements ILoggable {
    private final String itemImgSelector = ".inventory_item_img";
    private final String itemNameSelector = ".inventory_item_name";
    private final String itemDescriptionSelector = ".inventory_item_desc";
    private final String itemPriceSelector = ".inventory_item_price";
    private final String itemIdSelector = "%s div[class='inventory_item_img'] a";
    private final String addItemToCartSelector = "button[id*='add-to-cart']";
    private final String removeItemFromCartSelector = "button[id*='remove']";
    private String itemImageLink;
    private String itemName;
    private String itemDescription;
    private String itemPrice;

    public CatalogueTileComponent(WebDriver driver) {
        super(driver);
    }

    public void getTileInfo(String tileSelector) {
        itemImageLink = $(By.cssSelector(String.format(String.format("%s %s", tileSelector, itemImgSelector)))).getText();
        itemName = $(By.cssSelector(String.format(String.format("%s %s", tileSelector, itemNameSelector)))).getText();
        itemDescription = $(By.cssSelector(String.format(String.format("%s %s", tileSelector, itemDescriptionSelector)))).getText();
        itemPrice = $(By.cssSelector(String.format(String.format("%s %s", tileSelector, itemPriceSelector)))).getText();
    }

    public void goToItemPage(String tileSelector) {
        $(By.cssSelector(String.format(String.format("%s %s", tileSelector, itemNameSelector)))).click();
    }

    public String getItemId(String tileSelector) {
        String elementId = $(By.cssSelector(String.format(String.format(itemIdSelector, tileSelector)))).getCssValue("id");
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(elementId);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return null;
        }
    }

    public boolean addItemToCartFromCatalogue() {
        WebElement itemButton = $(By.cssSelector(addItemToCartSelector));
        String buttonText = itemButton.getText();
        if (buttonText.equals("Add to cart")) {
            Assertions.assertEquals("Add to cart", itemButton.getText());
            itemButton.click();
            waiter.waitForVisibility(By.cssSelector(removeItemFromCartSelector));
            Assertions.assertEquals("Remove", $(By.cssSelector(removeItemFromCartSelector)).getText());
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
