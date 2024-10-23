package Pages;

import org.openqa.selenium.*; // Import for WebDriver, WebElement, and other Selenium classes.
import org.openqa.selenium.support.ui.ExpectedConditions; // Import for explicit wait conditions.
import org.openqa.selenium.support.ui.FluentWait; // Import for FluentWait.
import java.time.Duration; // Import for handling duration in FluentWait.
import java.util.List; // Import for List to handle multiple WebElements.

public class DashBoard_Page {

    WebDriver driver; // Instance of WebDriver to interact with the browser.
    private List<WebElement> menus; // List to store dashboard menu elements.
    private Comman_Actions comman; // Instance of custom utility class "Comman_Actions" for common actions.

    // Locators to find dashboard elements.
    private By dashboard = By.xpath("//div[@class=\"orangehrm-dashboard-widget-name\"]");
    private By customizeBt = By.xpath("//div[@class=\"orangehrm-customizeBt\"]");

    // Constructor to initialize the WebDriver and Comman_Actions instance.
    public DashBoard_Page(WebDriver driver) {
        this.driver = driver;
        comman = new Comman_Actions(driver); // Initializes "Comman_Actions" with the WebDriver instance.
    }

    // Method to check if the dashboard is loaded by waiting for a specific element.
    public boolean isDashboardLoaded() {
        try {
            comman.wait(dashboard); // Waits until the dashboard element is clickable.
        } catch (TimeoutException e) {
            return false; // Returns false if a timeout occurs.
        }
        return driver.findElement(dashboard).isDisplayed(); // Returns true if the dashboard element is displayed.
    }

    // Method to get the list of menu items from the dashboard.
    public List<WebElement> getMenuList() {
        comman.wait(dashboard); // Waits until the dashboard element is clickable.
        menus = driver.findElements(dashboard); // Finds all elements with the locator and stores them in the menu list.
        return menus; // Returns the list of menu elements.
    }

    // Method to return the size of the menu list.
    public int menuListSize() {
        return this.menus.size(); // Returns the size of the menus list.
    }

    // Method to check if any element in the dashboard contains the provided text.
    public boolean isElementsContainsProvidedText(String text) {
        List<WebElement> elements = driver.findElements(dashboard); // Finds all elements matching the dashboard locator.
        return elements.stream().anyMatch(e -> e.getText().trim().equals(text)); // Checks if any element's text matches the provided text.
    }

    // Method to check if the customize button is loaded on the dashboard.
    public boolean isCustomizButtonLoded() {
        comman.wait(dashboard); // Waits until the dashboard element is clickable.
        try {
            driver.findElement(customizeBt).isDisplayed(); // Checks if the customize button is displayed.
        } catch (NoSuchElementException e) {
            return false; // Returns false if the customize button is not found.
        }
        return driver.findElement(customizeBt).isDisplayed(); // Returns true if the customize button is displayed.
    }

}
