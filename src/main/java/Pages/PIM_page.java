package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PIM_page {

    private WebDriver driver;
    private Comman_Actions comman; // Declares an instance of the common actions utility class

    // Locators for elements in the PIM page
    private By navBarEl = By.xpath("//span[@class=\"oxd-text oxd-text--span oxd-main-menu-item--name\"]");
    private By addUserBt = By.xpath("//button[@class=\"oxd-button oxd-button--medium oxd-button--secondary\"]");
    private By addUserEmpFirstName = By.xpath("//input[@class=\"oxd-input oxd-input--active orangehrm-firstname\"]");
    private By addUserEmpLastName = By.xpath("//input[@class=\"oxd-input oxd-input--active orangehrm-lastname\"]");
    private By addUserCreatLoginData = By.xpath("//span[@class=\"oxd-switch-input oxd-switch-input--active --label-right\"]");
    private By addUserCreatTextBoxInList = By.xpath("//input[@class=\"oxd-input oxd-input--active\"]");
    private By addUserSaveBt = By.xpath("//button[@class=\"oxd-button oxd-button--medium oxd-button--secondary orangehrm-left-space\"]");
    private By successSaveMsg = By.xpath("//div[@class=\"oxd-toast oxd-toast--success oxd-toast-container--toast\"]");
    private By newUserData = By.xpath("//div[@class=\"orangehrm-edit-employee-imagesection\"]");

    // Constructor to initialize WebDriver and common actions
    public PIM_page(WebDriver driver) {
        this.driver = driver;
        comman = new Comman_Actions(driver);
    }

    // Method to create a new user
    public void createNewUser(String username, String password) {
        comman.getMenuList(navBarEl); // Waits and retrieves the navigation bar list
        comman.menus.get(1).click(); // Clicks on the "PIM" option in the navigation bar
        comman.wait(addUserBt); // Waits until the "Add User" button is clickable
        driver.findElement(addUserBt).click(); // Clicks the "Add User" button

        // Enters user details
        comman.wait(addUserEmpFirstName); // Waits until the "First Name" input is visible
        driver.findElement(addUserEmpFirstName).sendKeys("Zaher"); // Enters first name
        driver.findElement(addUserEmpLastName).sendKeys("Only"); // Enters last name

        // Creates login credentials for the new user
        comman.wait(addUserCreatLoginData); // Waits until the "Create Login" switch is clickable
        driver.findElement(addUserCreatLoginData).click(); // Toggles the switch to enable login credentials
        comman.wait(addUserCreatTextBoxInList); // Waits for the login credentials input fields to be visible
        comman.getMenuList(addUserCreatTextBoxInList); // Retrieves the login input fields
        comman.menus.get(2).sendKeys(username); // Enters the username
        comman.menus.get(3).sendKeys(password); // Enters the password
        comman.menus.get(4).sendKeys(password); // Confirms the password

        driver.findElement(addUserSaveBt).click(); // Clicks the "Save" button to create the user
        comman.wait(successSaveMsg); // Waits for the success message to be displayed
        comman.wait(newUserData); // Waits for the new user data to be displayed
    }

    // Method to check if the new user is saved
    public boolean isUserSaved() {
        return driver.findElement(newUserData).isDisplayed(); // Returns true if the new user data is displayed
    }
}
