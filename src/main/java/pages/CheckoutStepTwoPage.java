package pages;

import components.ShoppingCartItemComponent;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CheckoutStepTwoPage extends AbsPage {

    private final String cartItemSelector = ".cart_item";
    private final String subtotalPriceSelector = ".summary_subtotal_label";
    private final String subtotalTaxSelector = ".summary_tax_label";
    private final String totalAmountSelector = ".summary_total_label";
    private final String finishButtonSelector = "#finish";

    public CheckoutStepTwoPage(final WebDriver driver) {
        super(driver, "/checkout-step-two.html");
    }

    public void checkAmountAndFinish() {
        List<WebElement> cartItems = $$(By.cssSelector(cartItemSelector));
        ShoppingCartItemComponent shoppingCartItemComponent = new ShoppingCartItemComponent(driver);
        double cartItemsTotalAmount = 0.0;
        for (int i = 3; i < cartItems.size() + 3; i++) {
            cartItemsTotalAmount += shoppingCartItemComponent.getItemPrice(i, cartItemSelector);
        }
        double subtotalPrice = getPriceFromString($(By.cssSelector(subtotalPriceSelector)).getText());
        Assertions.assertEquals(cartItemsTotalAmount, subtotalPrice);
        double taxes = getPriceFromString($(By.cssSelector(subtotalTaxSelector)).getText());
        double totalAmount = getPriceFromString($(By.cssSelector(totalAmountSelector)).getText());
        Assertions.assertEquals(totalAmount, (subtotalPrice + taxes));
        $(By.cssSelector(finishButtonSelector)).click();
        new CheckoutCompletePage(driver).goToCatalogue();
    }

    private double getPriceFromString(final String stringPrice) {
        Pattern pattern = Pattern.compile("\\d+\\.\\d+");
        Matcher matcher = pattern.matcher(stringPrice);
        if (matcher.find()) {
            String doubleString = matcher.group();
            return Double.parseDouble(doubleString);
        } else {
            return 0.0;
        }
    }

}