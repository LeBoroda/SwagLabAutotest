package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CatalogueTileComponent extends AbsComponent{
    private String itemImgSelector = ".inventory_item_img";
    private String itemNameSelector = ".inventory_item_name";
    private String itemDescriptionSelector = ".inventory_item_desc";
    private String itemPriceSelector = ".inventory_item_price";
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
        String elementId = $(By.cssSelector(String.format(String.format("%s div[class='inventory_item_img'] a", tileSelector)))).getCssValue("id");
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(elementId);
        if(matcher.find()){
            return matcher.group();
        } else {
            return null;
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
