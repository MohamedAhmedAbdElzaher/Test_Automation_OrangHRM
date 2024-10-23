package Pages;

import org.openqa.selenium.By; // Import for locating elements using different locators (like By.xpath, By.id, etc.).
import org.openqa.selenium.WebDriver; // Import for controlling the browser.
import org.openqa.selenium.WebElement; // Import for representing an HTML element.
import org.openqa.selenium.support.ui.ExpectedConditions; // Import for waiting conditions.
import org.openqa.selenium.support.ui.FluentWait; // Import for FluentWait functionality.

import java.time.Duration; // Import for handling time-related operations.
import java.util.List; // Import for List to hold WebElement collections.

public class Comman_Actions {

    private WebDriver driver; // WebDriver instance to interact with the browser.

    public FluentWait Fwait; // FluentWait instance to manage dynamic waits.
    public List<WebElement> menus; // List to store menu elements.

    // Constructor to initialize WebDriver.
    public Comman_Actions(WebDriver driver) {
        this.driver = driver;
    }

    // Method to implement FluentWait on a given locator.
    public void wait(By locator) {
        Fwait = new FluentWait(driver); // Initialize FluentWait with WebDriver.
        Fwait.withTimeout(Duration.ofSeconds(15)); // Set the maximum wait time to 15 seconds.
        Fwait.pollingEvery(Duration.ofSeconds(1)); // Set polling frequency to 1 second.
        Fwait.until(ExpectedConditions.elementToBeClickable(locator)); // Wait until the element becomes clickable.
    }

    // Method to get a list of menu items (WebElements) located by a specific locator.
    public List<WebElement> getMenuList(By locator) {
        wait(locator); // Wait until the element is ready before fetching.
        menus = driver.findElements(locator); // Find and store the elements matching the locator.
        return menus; // Return the list of elements.
    }
}
