package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public final class CheckoutStepOnePage extends AbsPage {

    private final String continueButtonSelector = ".submit-button";
    private final String errorMessageContainerSelector = ".error-message-container";
    private final String errorButtonSelector = ".error-button";
    private final String firstNameFieldSelector = "#first-name";
    private final String lastNameFieldSelector = "#last-name";
    private final String postalCodeFieldSelector = "#postal-code";

    public CheckoutStepOnePage(final WebDriver driver) {
        super(driver, "/checkout-step-one.html");
    }

    public void getBuyerInfo() {
        WebElement continueButton = $(By.cssSelector(continueButtonSelector));
        continueButton.click();
        Assertions.assertEquals("Error: First Name is required", $(By.cssSelector(errorMessageContainerSelector)).getText());
        $(By.cssSelector(errorButtonSelector)).click();
        $(By.cssSelector(firstNameFieldSelector)).sendKeys("User");
        continueButton.click();
        Assertions.assertEquals("Error: Last Name is required", $(By.cssSelector(errorMessageContainerSelector)).getText());
        $(By.cssSelector(errorButtonSelector)).click();
        $(By.cssSelector(lastNameFieldSelector)).sendKeys("Name");
        continueButton.click();
        Assertions.assertEquals("Error: Postal Code is required", $(By.cssSelector(errorMessageContainerSelector)).getText());
        $(By.cssSelector(errorButtonSelector)).click();
        $(By.cssSelector(postalCodeFieldSelector)).sendKeys("35800");
        continueButton.click();
        new CheckoutStepTwoPage(driver).checkAmountAndFinish();
    }

    public void getProblemBuyerInfo() {
        WebElement continueButton = $(By.cssSelector(continueButtonSelector));
        continueButton.click();
        Assertions.assertEquals("Error: First Name is required", $(By.cssSelector(errorMessageContainerSelector)).getText());
        $(By.cssSelector(errorButtonSelector)).click();
        $(By.cssSelector(firstNameFieldSelector)).sendKeys("User");
        continueButton.click();
        Assertions.assertEquals("Error: Last Name is required", $(By.cssSelector(errorMessageContainerSelector)).getText());
        $(By.cssSelector(errorButtonSelector)).click();
        $(By.cssSelector(lastNameFieldSelector)).sendKeys("Name");
        continueButton.click();
        Assertions.assertEquals("Error: Last Name is required", $(By.cssSelector(errorMessageContainerSelector)).getText());
    }
}
