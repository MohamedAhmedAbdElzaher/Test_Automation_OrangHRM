package Pages; // Defines the package "Pages" for this class, indicating the folder structure of the project.

import org.openqa.selenium.By; // Imports the By class from Selenium, used to locate web elements.
import org.openqa.selenium.WebDriver; // Imports the WebDriver interface, which is used to control the browser.

public class Admin_page { // Defines the public class "Admin_page" which represents the Admin page actions in the test.

    private WebDriver driver; // Declares a private instance of WebDriver to control the browser.
    private Comman_Actions comman; // Declares a private instance of a custom utility class "Comman_Actions" for common actions.

    // Declares locators to find elements on the Admin page using different strategies.
    private By navBarEl = By.xpath("//span[@class=\"oxd-text oxd-text--span oxd-main-menu-item--name\"]"); // Locator for the navigation bar.
    private By AdminSelectAllUsers = By.cssSelector("div.oxd-checkbox-wrapper"); // Locator for selecting all users using a checkbox.
    private By AdminUsersContenar = By.className("orangehrm-container"); // Locator for the container holding user data.
    private By AdminDeleteUsers = By.xpath("//button[@class=\"oxd-button oxd-button--medium oxd-button--label-danger orangehrm-horizontal-margin\"]"); // Locator for the delete button for users.
    private By AdminDeleteUsersYesBt = By.cssSelector(" button.oxd-button.oxd-button--medium.oxd-button--label-danger.orangehrm-button-margin"); // Locator for the confirmation button to delete users.
    private By successSaveMsg = By.xpath("//div[@class=\"oxd-toast oxd-toast--success oxd-toast-container--toast\"]"); // Locator for the success message after saving changes.

    // Constructor to initialize the Admin_page class.
    public Admin_page(WebDriver driver){
        this.driver = driver; // Assigns the passed WebDriver instance to the class's driver field.
        comman = new Comman_Actions(driver); // Initializes the "Comman_Actions" class with the WebDriver instance.
    }

    // Method to delete all users on the Admin page.
    public void deletAllUsers(){
        comman.getMenuList(navBarEl); // Calls a method from the "Comman_Actions" class to interact with the navigation bar.
        comman.menus.get(0).click(); // Selects the first menu item in the list.
        comman.wait(AdminUsersContenar); // Waits until the users' container element is visible.
        comman.getMenuList(AdminSelectAllUsers); // Fetches the checkbox element to select all users.
        comman.menus.get(0).click(); // Clicks the checkbox to select all users.
        driver.findElement(AdminDeleteUsers).click(); // Clicks the delete button.
        comman.wait(AdminDeleteUsersYesBt); // Waits until the "Yes" confirmation button is visible.
        driver.findElement(AdminDeleteUsersYesBt).click(); // Clicks the "Yes" confirmation button to delete the users.
    }

    // Method to check if users were successfully deleted.
    public boolean isUserDeleted() {
        comman.wait(successSaveMsg); // Waits until the success message is displayed.
        return driver.findElement(successSaveMsg).isDisplayed(); // Returns true if the success message is displayed, indicating users were deleted successfully.
    }
}
