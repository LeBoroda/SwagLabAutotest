package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShoppingCartItemComponent extends AbsComponent{

    private String cartItemPriceSelectorTemplate = ".cart_list :nth-child(%d)%s .inventory_item_price";
    public ShoppingCartItemComponent(WebDriver driver) {
        super(driver);
    }

    public double getItemPrice(int itemNumber, String selector) {
        String stringPrice = $(By.cssSelector(String.format(cartItemPriceSelectorTemplate, itemNumber, selector))).getText();
        double price = extractPrice(stringPrice);
        return price;
    }
    private double extractPrice(String stringPrice) {
        Pattern pattern = Pattern.compile("\\d+\\.\\d+");
        Matcher matcher = pattern.matcher(stringPrice);
        if (matcher.find()) {
            String doubleString = matcher.group();
            Double doubleValue = Double.parseDouble(doubleString);
            return doubleValue;
        } else {
            return 0.0;
        }
    }
}
