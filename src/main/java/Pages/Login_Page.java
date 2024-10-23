package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;

public class Login_Page {

    // Instance variables for WebDriver and Comman_Actions
    private WebDriver driver;
    private Comman_Actions comman;

    // Locators for the login page elements
    private By usernameBox = By.xpath("//input[@name=\"username\"]");
    private By passwordBox = By.xpath("//input[@name=\"password\"]");
    private By loginButton = By.xpath("//button[@type=\"submit\"]");
    private By invalidCredentials = By.xpath("//p[@class = \"oxd-text oxd-text--p oxd-alert-content-text\"]");
    private By requiredField = By.xpath("//span[@class=\"oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message\"]");
    private By dropdownMenu = By.xpath("//i[@class=\"oxd-icon bi-caret-down-fill oxd-userdropdown-icon\"]");
    private By dropdownMenuElement = By.xpath("//a[@class=\"oxd-userdropdown-link\"]");

    // Constructor to initialize WebDriver and Comman_Actions
    public Login_Page(WebDriver driver) {
        this.driver = driver;
        comman = new Comman_Actions(driver);
    }

    // Method to perform login by providing username and password
    public void login_Page(String username, String password) {
        comman.wait(loginButton); // Waits until login button is clickable
        driver.findElement(usernameBox).sendKeys(username); // Enters the username
        driver.findElement(passwordBox).sendKeys(password); // Enters the password
        driver.findElement(loginButton).click(); // Clicks the login button
    }

    // Method to enter only the username
    public void enterLoginUsername(String username) {
        comman.wait(usernameBox); // Waits until username field is clickable
        driver.findElement(usernameBox).sendKeys(username); // Enters the username
    }

    // Method to clear the username field
    public void clearLoginUsername() {
        comman.wait(usernameBox); // Waits until username field is clickable
        driver.findElement(usernameBox).sendKeys(Keys.CONTROL + "a"); // Selects all text in the username field
        driver.findElement(usernameBox).sendKeys(Keys.DELETE); // Deletes the selected text
    }

    // Method to enter only the password
    public void enterLoginPassword(String password) {
        comman.wait(passwordBox); // Waits until password field is clickable
        driver.findElement(passwordBox).sendKeys(password); // Enters the password
    }

    // Method to clear the password field
    public void clearLoginPassword() {
        comman.wait(passwordBox); // Waits until password field is clickable
        driver.findElement(passwordBox).sendKeys(Keys.CONTROL + "a"); // Selects all text in the password field
        driver.findElement(passwordBox).sendKeys(Keys.DELETE); // Deletes the selected text
    }

    // Method to click the login button
    public void clickLoginButton() {
        driver.findElement(loginButton).click(); // Clicks the login button
    }

    // Method to check if login failed due to invalid credentials
    public boolean cantLogin() {
        return driver.findElements(invalidCredentials).size() == 0; // Returns true if the invalid credentials message is displayed
    }

    // Method to check if a required field error is shown
    public boolean requiredField() {
        return driver.findElements(requiredField).size() == 1; // Returns true if a required field error is shown
    }

    // Method to log out of the account
    public void logOut() {
        try {
            driver.findElement(dropdownMenu).click(); // Clicks on the dropdown menu
            comman.getMenuList(dropdownMenuElement); // Gets the list of menu items in the dropdown
            comman.menus.get(comman.menus.size() - 1).click(); // Clicks the last menu item (logout)
        } catch (NoSuchElementException e) {
            // Exception handling if elements are not found
        }
    }
}
