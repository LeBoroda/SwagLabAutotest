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
    private final By subtotalPriceSelector = By.cssSelector(".summary_subtotal_label");
    private final By subtotalTaxSelector = By.cssSelector(".summary_tax_label");
    private final By totalAmountSelector = By.cssSelector(".summary_total_label");
    private final By finishButtonSelector = By.cssSelector("#finish");

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
        double subtotalPrice = getPriceFromString($(subtotalPriceSelector).getText());
        Assertions.assertEquals(cartItemsTotalAmount, subtotalPrice);
        double taxes = getPriceFromString($(subtotalTaxSelector).getText());
        double totalAmount = getPriceFromString($(totalAmountSelector).getText());
        Assertions.assertEquals(totalAmount, (subtotalPrice + taxes));
        $(finishButtonSelector).click();
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