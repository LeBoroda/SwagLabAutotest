package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

public class LoginCredentialsBlockComponent extends AbsComponent{
    private String usernameLocator   = "//div[@id='login_credentials']";
    private String passwordLocator= "//div[@class='login_password']";
    public LoginCredentialsBlockComponent(WebDriver driver) {
        super(driver);
    }

    public List<String> getPasswords() {
        String passwords = $(By.xpath("//div[@class='login_password']")).getText();
        String[] passwordsArray = passwords.split("\n");
        List<String> result = new ArrayList<>();
        for(int i=1; i<passwordsArray.length; i++) {
            result.add(passwordsArray[i]);
        }
        return result;
    }

    public List<String> getUserNames() {
        String usernames = $(By.xpath("//div[@id='login_credentials']")).getText();
        String[] userNameArray = usernames.split("\n");
        List<String> result = new ArrayList<>();
        for(int i=1; i<userNameArray.length; i++) {
            result.add(userNameArray[i]);
        }
        return result;
    }


}
