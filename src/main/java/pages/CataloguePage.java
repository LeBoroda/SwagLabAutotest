package pages;

import components.CatalogueHeaderComponent;
import components.CatalogueTileComponent;
import components.PopUpMenuComponent;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class CataloguePage extends AbsPage{

    private String catalogueItemSelector = ".inventory_item";

    public CataloguePage(WebDriver driver) {
        super(driver, "/inventory.html");
    }

    public void runStandardCatalogueTest() {
        List<WebElement> catalogueItems = $$(By.cssSelector(catalogueItemSelector));
        assert catalogueItems.size()!=0;
        for(int i=0; i < catalogueItems.size(); i++) {
            String tileInfoSelector = String.format("%s:nth-child(%d)", catalogueItemSelector, i+1);
            CatalogueTileComponent catalogueTileComponent = new CatalogueTileComponent(driver);
            catalogueTileComponent.getTileInfo(tileInfoSelector);
            String pagePath = catalogueTileComponent.getItemId(tileInfoSelector);
            catalogueTileComponent.goToItemPage(tileInfoSelector);
            ItemPage itemPage = new ItemPage(driver, pagePath);
            itemPage.compareInfo(catalogueTileComponent);
        }
        new PopUpMenuComponent(driver).logOut();
    }

    public void runGlitchCatalogueTest() {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            runStandardCatalogueTest();
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        }
    }

    public void runProblemCatalogueTest() {
        List<WebElement> catalogueItems = $$(By.cssSelector(catalogueItemSelector));
        assert catalogueItems.size()!=0;
        for(int i=0; i < catalogueItems.size(); i++) {
            String tileInfoSelector = String.format("%s:nth-child(%d)", catalogueItemSelector, i+1);
            CatalogueTileComponent catalogueTileComponent = new CatalogueTileComponent(driver);
            catalogueTileComponent.getTileInfo(tileInfoSelector);
            String pagePath = catalogueTileComponent.getItemId(tileInfoSelector);
            catalogueTileComponent.goToItemPage(tileInfoSelector);
            ItemPage itemPage = new ItemPage(driver, pagePath);
            itemPage.compareWrongInfo(catalogueTileComponent);
        }
        new PopUpMenuComponent(driver).logOut();
    }

}
