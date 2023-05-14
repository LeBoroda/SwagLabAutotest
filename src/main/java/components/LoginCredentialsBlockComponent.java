package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class LoginCredentialsBlockComponent extends AbsComponent {
    private final By usernameLocator = By.xpath("//div[@id='login_credentials']");
    private final By passwordLocator = By.xpath("//div[@class='login_password']");

    public LoginCredentialsBlockComponent(final WebDriver driver) {
        super(driver);
    }

    public List<String> getPasswords() {
        String passwords = $(passwordLocator).getText();
        String[] passwordsArray = passwords.split("\n");
        return new ArrayList<>(Arrays.asList(passwordsArray).subList(1, passwordsArray.length));
    }

    public List<String> getUserNames() {
        String usernames = $(usernameLocator).getText();
        String[] userNameArray = usernames.split("\n");
        return new ArrayList<>(Arrays.asList(userNameArray).subList(1, userNameArray.length));
    }

}