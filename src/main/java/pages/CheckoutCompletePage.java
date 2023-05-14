package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class CheckoutCompletePage extends AbsPage {

    private final By backToCatalogueButtonSelector = By.cssSelector("#back-to-products");
    private final By successMessageSelector = By.cssSelector(".complete-header");

    public CheckoutCompletePage(final WebDriver driver) {
        super(driver, "/checkout-complete.html");
    }

    public void goToCatalogue() {
        Assertions.assertEquals("Thank you for your order!", $(successMessageSelector).getText());
        $(backToCatalogueButtonSelector).click();
    }

}