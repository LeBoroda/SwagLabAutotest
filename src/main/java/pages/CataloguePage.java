package pages;

import components.CatalogueHeaderComponent;
import components.CatalogueTileComponent;
import components.LoginBlockComponent;
import components.PopUpMenuComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import java.time.Duration;
import java.util.List;

public class CataloguePage extends AbsPage {

    private final String catalogueItemSelector = ".inventory_item";
    private final String tileInfoSelectorTemplate = "%s:nth-child(%d)";
    private final LoginBlockComponent loginBlockComponent = new LoginBlockComponent(driver);
    private final CatalogueTileComponent catalogueTileComponent = new CatalogueTileComponent(driver);
    private ItemPage itemPage;

    public CataloguePage(WebDriver driver) {
        super(driver, "/inventory.html");
    }

    public CataloguePage runStandardCatalogueTest() {
        loginBlockComponent.standardUserLogin();
        catalogueTest();
        return this;
    }

    public CataloguePage runGlitchCatalogueTest() {
        loginBlockComponent.glitchUserLogin();
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            catalogueTest();
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        }
        return this;
    }

    public void runProblemCatalogueTest() {
        loginBlockComponent.problemUserLogin();
        List<WebElement> catalogueItems = $$(By.cssSelector(catalogueItemSelector));
        assert catalogueItems.size() != 0;
        for (int i = 0; i < catalogueItems.size(); i++) {
            String tileInfoSelector = String.format(tileInfoSelectorTemplate, catalogueItemSelector, i + 1);
            catalogueTileComponent.getTileInfo(tileInfoSelector);
            String pagePath = catalogueTileComponent.getItemId(tileInfoSelector);
            catalogueTileComponent.goToItemPage(tileInfoSelector);
            itemPage = new ItemPage(driver, pagePath);
            itemPage.compareWrongInfo(catalogueTileComponent);
        }
        new PopUpMenuComponent(driver).logOut();
    }

    private void catalogueTest() {
        List<WebElement> catalogueItems = $$(By.cssSelector(catalogueItemSelector));
        assert catalogueItems.size() != 0;
        for (int i = 0; i < catalogueItems.size(); i++) {
            String tileInfoSelector = String.format(tileInfoSelectorTemplate, catalogueItemSelector, i + 1);
            catalogueTileComponent.getTileInfo(tileInfoSelector);
            String pagePath = catalogueTileComponent.getItemId(tileInfoSelector);
            catalogueTileComponent.goToItemPage(tileInfoSelector);
            itemPage = new ItemPage(driver, pagePath);
            itemPage.compareInfo(catalogueTileComponent);
        }
        new PopUpMenuComponent(driver).logOut();
    }

    public CataloguePage runStandardCheckoutTest() {
        loginBlockComponent.standardUserLogin();
        shoppingCartCheckout();
        new PopUpMenuComponent(driver)
                .logOut();
        return this;
    }

    public CataloguePage runGlitchCheckoutTest() {
        loginBlockComponent.glitchUserLogin();
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            shoppingCartCheckout();
            new PopUpMenuComponent(driver)
                    .logOut();
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        }
        return this;
    }

    public void runProblemCheckoutTest() {
        loginBlockComponent.problemUserLogin();
        List<WebElement> catalogueItems = $$(By.cssSelector(catalogueItemSelector));
        assert catalogueItems.size() != 0;
        int addedItems = 0;
        for (int i = 0; i < catalogueItems.size(); i++) {
            String tileInfoSelector = String.format(tileInfoSelectorTemplate, catalogueItemSelector, i + 1);
            catalogueTileComponent.getTileInfo(tileInfoSelector);
            String pagePath = catalogueTileComponent.getItemId(tileInfoSelector);

            if (catalogueTileComponent.addItemToCartFromCatalogue()) {
                addedItems++;
            }
//            catalogueTileComponent.goToItemPage(tileInfoSelector);
//            itemPage = new ItemPage(driver, pagePath);
//            itemPage.checkItemAddedToCart(addedItems);
        }
        new CatalogueHeaderComponent(driver)
                .checkProblemCartIcon(addedItems)
                .goToCart();
        new ShoppingCartPage(driver)
                .checkProblemCart();
    }


    public void shoppingCartCheckout() {
        new PopUpMenuComponent(driver).resetAppState();
        List<WebElement> catalogueItems = $$(By.cssSelector(catalogueItemSelector));
        assert catalogueItems.size() != 0;
        int addedItems = 0;
        for (int i = 0; i < catalogueItems.size(); i++) {
            String tileInfoSelector = String.format(tileInfoSelectorTemplate, catalogueItemSelector, i + 1);
            catalogueTileComponent.getTileInfo(tileInfoSelector);
            String pagePath = catalogueTileComponent.getItemId(tileInfoSelector);
            catalogueTileComponent.addItemToCartFromCatalogue();
            addedItems++;
            catalogueTileComponent.goToItemPage(tileInfoSelector);
            itemPage = new ItemPage(driver, pagePath);
            itemPage.checkItemAddedToCart(addedItems);
        }
        new CatalogueHeaderComponent(driver)
                .checkCartIcon(addedItems)
                .goToCart();
        new ShoppingCartPage(driver)
                .checkCart();
    }
}
