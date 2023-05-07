package components;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class LoginBlockComponent extends AbsComponent {

    private String usernameFieldIdSelector = "user-name";
    private String passwordFIeldIdSelector = "password";
    private String loginButtonIdSelector = "login-button";
    private String errorMessageClassSelector = "error-message-container";
    private String errorButtonClassSelector = "error-button";
    private List<String> badPasswords = Arrays.asList("", " ", "xx");
    private List<String> badUsernames = Arrays.asList("xx", " ", "");
    private LoginCredentialsBlockComponent lcb = new LoginCredentialsBlockComponent(driver);
    private List<String> usernames = lcb.getUserNames();
    private List<String> passwords = lcb.getPasswords();
    private String username;
    private String password = passwords.get(0);

    public LoginBlockComponent(WebDriver driver) {
        super(driver);
    }

    private WebElement usernameField = $(By.id(usernameFieldIdSelector));
    private WebElement passwordField = $(By.id(passwordFIeldIdSelector));
    private WebElement loginButton = $(By.id(loginButtonIdSelector));

    public LoginBlockComponent runBadPasswordLoginTest() {
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
        usernameField = $(By.id(usernameFieldIdSelector));
        usernameField.clear();
        usernameField.sendKeys("mamba");
        passwordField = $(By.id(passwordFIeldIdSelector));
        passwordField.clear();
        loginButton = $(By.id(loginButtonIdSelector));
        loginButton.click();
        Assertions.assertTrue(waiter.waitForCondition(ExpectedConditions.visibilityOf($(By.className(errorMessageClassSelector)))));
        $(By.className(errorButtonClassSelector)).click();
        driver.navigate().refresh();
        usernameField = $(By.id(usernameFieldIdSelector));
        usernameField.clear();
        passwordField = $(By.id(passwordFIeldIdSelector));
        passwordField.clear();
        passwordField.sendKeys("namba5");
        loginButton = $(By.id(loginButtonIdSelector));
        loginButton.click();
        Assertions.assertTrue(waiter.waitForCondition(ExpectedConditions.visibilityOf($(By.className(errorMessageClassSelector)))));
        $(By.className(errorButtonClassSelector)).click();
        return this;
    }

    public LoginBlockComponent runLockedOutUserLoginTest() {
        username = usernames.get(1);
        logIn(username, password);
        Assertions.assertEquals("Epic sadface: Sorry, this user has been locked out.", $(By.className(errorMessageClassSelector)).getText());
        $(By.className(errorButtonClassSelector)).click();
        return this;
    }

    public LoginBlockComponent runStandardUserLoginTest() {
        standardUserLogin();
        Assertions.assertTrue(waiter.waitForCondition(ExpectedConditions.visibilityOf(new CatalogueHeaderComponent(driver).checkHeaderVisibility())));
        new PopUpMenuComponent(driver).logOut();
        return this;
    }

    public LoginBlockComponent runProblemUserLoginTest() {
        problemUserLogin();
        Assertions.assertTrue(waiter.waitForCondition(ExpectedConditions.visibilityOf(new CatalogueHeaderComponent(driver).checkHeaderVisibility())));
        new PopUpMenuComponent(driver).logOut();
        return this;
    }

    public LoginBlockComponent runGlitchUserLoginTest() {
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

    private void logIn(String username, String password) {
        usernameField = $(By.id(usernameFieldIdSelector));
        usernameField.clear();
        usernameField.sendKeys(username);
        passwordField = $(By.id(passwordFIeldIdSelector));
        passwordField.clear();
        passwordField.sendKeys(password);
        loginButton = $(By.id(loginButtonIdSelector));
        loginButton.click();
    }

}
