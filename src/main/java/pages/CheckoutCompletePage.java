package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class CheckoutCompletePage extends AbsPage {

    private final String backToCatalogueButtonSelector = "#back-to-products";
    private final String successMessageSelector = ".complete-header";

    public CheckoutCompletePage(final WebDriver driver) {
        super(driver, "/checkout-complete.html");
    }

    public void goToCatalogue() {
        Assertions.assertEquals("Thank you for your order!", $(By.cssSelector(successMessageSelector)).getText());
        $(By.cssSelector(backToCatalogueButtonSelector)).click();
    }

}