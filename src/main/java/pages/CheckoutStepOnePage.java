package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public final class CheckoutStepOnePage extends AbsPage {

    private final By continueButtonSelector = By.cssSelector(".submit-button");
    private final By errorMessageContainerSelector = By.cssSelector(".error-message-container");
    private final By errorButtonSelector = By.cssSelector(".error-button");
    private final By firstNameFieldSelector = By.cssSelector("#first-name");
    private final By lastNameFieldSelector = By.cssSelector("#last-name");
    private final By postalCodeFieldSelector = By.cssSelector("#postal-code");

    public CheckoutStepOnePage(final WebDriver driver) {
        super(driver, "/checkout-step-one.html");
    }

    public void getBuyerInfo() {
        WebElement continueButton = $(continueButtonSelector);
        continueButton.click();
        Assertions.assertEquals("Error: First Name is required", $(errorMessageContainerSelector).getText());
        $(errorButtonSelector).click();
        $(firstNameFieldSelector).sendKeys("User");
        continueButton.click();
        Assertions.assertEquals("Error: Last Name is required", $(errorMessageContainerSelector).getText());
        $(errorButtonSelector).click();
        $(lastNameFieldSelector).sendKeys("Name");
        continueButton.click();
        Assertions.assertEquals("Error: Postal Code is required", $(errorMessageContainerSelector).getText());
        $(errorButtonSelector).click();
        $(postalCodeFieldSelector).sendKeys("35800");
        continueButton.click();
        new CheckoutStepTwoPage(driver).checkAmountAndFinish();
    }

    public void getProblemBuyerInfo() {
        WebElement continueButton = $(continueButtonSelector);
        continueButton.click();
        Assertions.assertEquals("Error: First Name is required", $(errorMessageContainerSelector).getText());
        $(errorButtonSelector).click();
        $(firstNameFieldSelector).sendKeys("User");
        continueButton.click();
        Assertions.assertEquals("Error: Last Name is required", $(errorMessageContainerSelector).getText());
        $(errorButtonSelector).click();
        $(lastNameFieldSelector).sendKeys("Name");
        continueButton.click();
        Assertions.assertEquals("Error: Last Name is required", $(errorMessageContainerSelector).getText());
    }

}