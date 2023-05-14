package components;

import logging.ILoggable;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public final class LoginBlockComponent extends AbsComponent implements ILoggable {

    private final By usernameFieldSelector = By.cssSelector("#user-name");
    private final By passwordFieldSelector = By.cssSelector("#password");
    private final By loginButtonSelector = By.cssSelector("#login-button");
    private final By errorMessageSelector = By.cssSelector(".error-message-container");
    private final By errorButtonSelector = By.cssSelector(".error-button");
    private final List<String> badPasswords = Arrays.asList("", " ", "xx");
    private final List<String> badUsernames = Arrays.asList("xx", " ", "");
    private final LoginCredentialsBlockComponent lcb = new LoginCredentialsBlockComponent(driver);
    private final List<String> usernames = lcb.getUserNames();
    private final List<String> passwords = lcb.getPasswords();
    private String username;
    private final String password = passwords.get(0);

    public LoginBlockComponent(final WebDriver driver) {
        super(driver);
    }

    private WebElement usernameField = $(usernameFieldSelector);
    private WebElement passwordField = $(passwordFieldSelector);
    private WebElement loginButton = $(loginButtonSelector);

    public LoginBlockComponent runBadPasswordLoginTest() {
        log().info("Running login test with wrong password");
        for (String username : usernames) {
            for (String badPassword : badPasswords) {
                logIn(username, badPassword);
                Assertions.assertTrue(waiter.waitForCondition(ExpectedConditions.visibilityOf($(errorMessageSelector))));
                $(errorButtonSelector).click();
            }
        }
        return this;
    }

    public LoginBlockComponent runBadUsernameLoginTest() {
        log().info("Running login test with wrong username");
        for (String badUsername : badUsernames) {
            for (String password : passwords) {
                logIn(badUsername, password);
                Assertions.assertTrue(waiter.waitForCondition(ExpectedConditions.visibilityOf($(errorMessageSelector))));
                $(errorButtonSelector).click();
            }
        }
        return this;
    }

    public LoginBlockComponent runEmptyFieldLoginTest() {
        log().info("Running login test with empty fields");
        usernameField = $(usernameFieldSelector);
        usernameField.clear();
        usernameField.sendKeys("mamba");
        passwordField = $(passwordFieldSelector);
        passwordField.clear();
        loginButton = $(loginButtonSelector);
        loginButton.click();
        Assertions.assertTrue(waiter.waitForCondition(ExpectedConditions.visibilityOf($(errorMessageSelector))));
        $(errorButtonSelector).click();
        driver.navigate().refresh();
        usernameField = $(usernameFieldSelector);
        usernameField.clear();
        passwordField = $(passwordFieldSelector);
        passwordField.clear();
        passwordField.sendKeys("namba5");
        loginButton = $(loginButtonSelector);
        loginButton.click();
        Assertions.assertTrue(waiter.waitForCondition(ExpectedConditions.visibilityOf($(errorMessageSelector))));
        $(errorButtonSelector).click();
        return this;
    }

    public LoginBlockComponent runLockedOutUserLoginTest() {
        log().info("Running login test for locked out user");
        username = usernames.get(1);
        logIn(username, password);
        Assertions.assertEquals("Epic sadface: Sorry, this user has been locked out.", $(errorMessageSelector).getText());
        $(errorButtonSelector).click();
        return this;
    }

    public LoginBlockComponent runStandardUserLoginTest() {
        log().info("Running login test for standard user");
        standardUserLogin();
        Assertions.assertTrue(waiter.waitForCondition(ExpectedConditions.visibilityOf(new CatalogueHeaderComponent(driver).checkHeaderVisibility())));
        new PopUpMenuComponent(driver).logOut();
        return this;
    }

    public LoginBlockComponent runProblemUserLoginTest() {
        log().info("Running login test for problem user");
        problemUserLogin();
        Assertions.assertTrue(waiter.waitForCondition(ExpectedConditions.visibilityOf(new CatalogueHeaderComponent(driver).checkHeaderVisibility())));
        new PopUpMenuComponent(driver).logOut();
        return this;
    }

    public LoginBlockComponent runGlitchUserLoginTest() {
        log().info("Running login test for glitch performance user");
        glitchUserLogin();
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            Assertions.assertTrue(waiter.waitForCondition(ExpectedConditions.visibilityOf(new CatalogueHeaderComponent(driver).checkHeaderVisibility())));
            new PopUpMenuComponent(driver).logOut();
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        }
        return this;
    }

    public void standardUserLogin() {
        username = usernames.get(0);
        logIn(username, password);
    }

    public void problemUserLogin() {
        username = usernames.get(2);
        logIn(username, password);
    }

    public void glitchUserLogin() {
        username = usernames.get(3);
        logIn(username, password);
    }

    private void logIn(final String username, final String password) {
        usernameField = $(usernameFieldSelector);
        usernameField.clear();
        usernameField.sendKeys(username);
        passwordField = $(passwordFieldSelector);
        passwordField.clear();
        passwordField.sendKeys(password);
        loginButton = $(loginButtonSelector);
        loginButton.click();
    }

}