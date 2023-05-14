package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import waiters.CommonWaiter;

import java.util.List;

public abstract class PageObject {
    protected WebDriver driver;
    protected CommonWaiter waiter;

    public PageObject(final WebDriver driver) {
        this.driver = driver;
        waiter = new CommonWaiter(driver);
        PageFactory.initElements(driver, this);
    }

    protected WebElement $(final By by) {
        return driver.findElement(by);
    }

    protected List<WebElement> $$(final By by) {
        return driver.findElements(by);
    }

}