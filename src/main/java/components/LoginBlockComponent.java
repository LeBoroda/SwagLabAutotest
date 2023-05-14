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

    private final String usernameFieldIdSelector = "user-name";
    private final String passwordFieldIdSelector = "password";
    private final String loginButtonIdSelector = "login-button";
    private final String errorMessageClassSelector = "error-message-container";
    private final String errorButtonClassSelector = "error-button";
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

    private WebElement usernameField = $(By.id(usernameFieldIdSelector));
    private WebElement passwordField = $(By.id(passwordFieldIdSelector));
    private WebElement loginButton = $(By.id(loginButtonIdSelector));

    public LoginBlockComponent runBadPasswordLoginTest() {
        log().info("Running login test with wrong password");
        for (String username : usernames) {
            for (String badPassword : badPasswords) {
                logIn(username, badPassword);
                Assertions.assertTrue(waiter.waitForCondition(ExpectedConditions.visibilityOf($(By.className(errorMessageClassSelector)))));
                $(By.className(errorButtonClassSelector)).click();
            }
        }
        return this;
    }

    public LoginBlockComponent runBadUsernameLoginTest() {
        log().info("Running login test with wrong username");
        for (String badUsername : badUsernames) {
            for (String password : passwords) {
                logIn(badUsername, password);
                Assertions.assertTrue(waiter.waitForCondition(ExpectedConditions.visibilityOf($(By.className(errorMessageClassSelector)))));
                $(By.className(errorButtonClassSelector)).click();
            }
        }
        return this;
    }

    public LoginBlockComponent runEmptyFieldLoginTest() {
        log().info("Running login test with empty fields");
        usernameField = $(By.id(usernameFieldIdSelector));
        usernameField.clear();
        usernameField.sendKeys("mamba");
        passwordField = $(By.id(passwordFieldIdSelector));
        passwordField.clear();
        loginButton = $(By.id(loginButtonIdSelector));
        loginButton.click();
        Assertions.assertTrue(waiter.waitForCondition(ExpectedConditions.visibilityOf($(By.className(errorMessageClassSelector)))));
        $(By.className(errorButtonClassSelector)).click();
        driver.navigate().refresh();
        usernameField = $(By.id(usernameFieldIdSelector));
        usernameField.clear();
        passwordField = $(By.id(passwordFieldIdSelector));
        passwordField.clear();
        passwordField.sendKeys("namba5");
        loginButton = $(By.id(loginButtonIdSelector));
        loginButton.click();
        Assertions.assertTrue(waiter.waitForCondition(ExpectedConditions.visibilityOf($(By.className(errorMessageClassSelector)))));
        $(By.className(errorButtonClassSelector)).click();
        return this;
    }

    public LoginBlockComponent runLockedOutUserLoginTest() {
        log().info("Running login test for locked out user");
        username = usernames.get(1);
        logIn(username, password);
        Assertions.assertEquals("Epic sadface: Sorry, this user has been locked out.", $(By.className(errorMessageClassSelector)).getText());
        $(By.className(errorButtonClassSelector)).click();
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
        usernameField = $(By.id(usernameFieldIdSelector));
        usernameField.clear();
        usernameField.sendKeys(username);
        passwordField = $(By.id(passwordFieldIdSelector));
        passwordField.clear();
        passwordField.sendKeys(password);
        loginButton = $(By.id(loginButtonIdSelector));
        loginButton.click();
    }

}