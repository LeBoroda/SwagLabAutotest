package pages;

import components.CatalogueTileComponent;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

public class ItemPage extends AbsPage {
    private String itemPicSelector = ".inventory_details_img";
    private String itemNameSelector = ".inventory_details_name";
    private String itemDescriptionSelector = ".inventory_details_desc";
    private String itemPriceSelector = ".inventory_details_price";
    private String returnToCatalogueButtonSelector = "#back-to-products";

    public ItemPage(WebDriver driver, String itemId) {
        super(driver, "/inventory-item.html?id=" + itemId);
    }

    public List<String> getItemPageInfo() {
        List<String> itemPageInfo = new ArrayList<>();
        itemPageInfo.add($(By.cssSelector(itemPicSelector)).getText());
        itemPageInfo.add($(By.cssSelector(itemNameSelector)).getText());
        itemPageInfo.add($(By.cssSelector(itemDescriptionSelector)).getText());
        itemPageInfo.add($(By.cssSelector(itemPriceSelector)).getText());
        return itemPageInfo;
    }

    public void returnToCatalogue() {
        $(By.cssSelector(returnToCatalogueButtonSelector)).click();
    }

    public void compareInfo(CatalogueTileComponent catalogueTileComponent) {
        Assertions.assertEquals(catalogueTileComponent.getItemImageLink(), $(By.cssSelector(itemPicSelector)).getText());
        Assertions.assertEquals(catalogueTileComponent.getItemName(), $(By.cssSelector(itemNameSelector)).getText());
        Assertions.assertEquals(catalogueTileComponent.getItemDescription(), $(By.cssSelector(itemDescriptionSelector)).getText());
        Assertions.assertEquals(catalogueTileComponent.getItemPrice(), $(By.cssSelector(itemPriceSelector)).getText());
        returnToCatalogue();
    }

    public void compareWrongInfo(CatalogueTileComponent catalogueTileComponent) {
        Assertions.assertNotSame(catalogueTileComponent.getItemImageLink(), $(By.cssSelector(itemPicSelector)).getText());
        Assertions.assertNotEquals(catalogueTileComponent.getItemName(), $(By.cssSelector(itemNameSelector)).getText());
        Assertions.assertNotEquals(catalogueTileComponent.getItemDescription(), $(By.cssSelector(itemDescriptionSelector)).getText());
        Assertions.assertNotEquals(catalogueTileComponent.getItemPrice(), $(By.cssSelector(itemPriceSelector)).getText());
        returnToCatalogue();
    }

}
