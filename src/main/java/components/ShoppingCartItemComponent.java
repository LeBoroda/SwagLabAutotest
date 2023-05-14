package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ShoppingCartItemComponent extends AbsComponent {

    private final String cartItemPriceSelectorTemplate = ".cart_list :nth-child(%d)%s .inventory_item_price";

    public ShoppingCartItemComponent(final WebDriver driver) {
        super(driver);
    }

    public double getItemPrice(final int itemNumber, final String selector) {
        String stringPrice = $(By.cssSelector(String.format(cartItemPriceSelectorTemplate, itemNumber, selector))).getText();
        return extractPrice(stringPrice);
    }

    private double extractPrice(final String stringPrice) {
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