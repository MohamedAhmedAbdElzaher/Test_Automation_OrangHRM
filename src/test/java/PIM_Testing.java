import Pages.Admin_page;
import Pages.DashBoard_Page;
import Pages.Login_Page;
import Pages.PIM_page;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.apache.maven.surefire.shared.lang3.RandomStringUtils; // Library for generating random strings
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;

public class PIM_Testing {

    private WebDriver driver; // WebDriver instance for browser automation
    private Login_Page loginPage; // Page object for the login page
    private DashBoard_Page dashboardPage; // Page object for the dashboard page
    private Admin_page adminPage; // Page object for the admin page
    private PIM_page pimpage; // Page object for the PIM page

    // Setup method to initialize WebDriver and page objects before any tests run
    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver(); // Initialize ChromeDriver
        driver.manage().window().maximize(); // Maximize the browser window
        driver.get("https://opensource-demo.orangehrmlive.com/"); // Navigate to the login page
        loginPage = new Login_Page(driver); // Create instance of Login_Page
        dashboardPage = new DashBoard_Page(driver); // Create instance of DashBoard_Page
        adminPage = new Admin_page(driver); // Create instance of Admin_page
        pimpage = new PIM_page(driver); // Create instance of PIM_page
    }

    // Test method to clear users and create a new user
    @Test()
    public void testClearUsers() {
        // Log in with valid credentials
        loginPage.login_Page("Admin", "admin123");
        try {
            // Assert that the dashboard is loaded after login
            Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard is not displayed after login");
        } catch (AssertionError e) {
            // Capture a screenshot and log an error message if the assertion fails
            Allure.addAttachment("Dashboard is not displayed after login With " + " User:Admin Pass:admin123", new ByteArrayInputStream(saveScreenshotPNG(driver)));
            throw e; // Rethrow the exception
        }

        // Generate random username and password for the new user
        String user = RandomStringUtils.randomAlphanumeric(15);
        String pass = RandomStringUtils.randomAlphanumeric(15);
        // Create a new user with the generated username and password
        pimpage.createNewUser(user, pass);

        try {
            // Assert that the new user is saved successfully
            Assert.assertTrue(pimpage.isUserSaved(), "New user is not saved");
        } catch (AssertionError e) {
            // Capture a screenshot and log an error message if the assertion fails
            Allure.addAttachment("Can't create new user with " + " User:" + user + " Pass:" + pass, new ByteArrayInputStream(saveScreenshotPNG(driver)));
            throw e; // Rethrow the exception
        }

        // Log out after the operations
        loginPage.logOut();
    }

    // Method to capture a screenshot for Allure reporting
    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshotPNG(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES); // Capture and return screenshot as byte array
    }

    // Cleanup method to close the browser after all tests
    @AfterTest
    void closeBrowser() throws InterruptedException {
        Thread.sleep(2000); // Optional: Adds delay for test visibility
        driver.quit(); // Close the browser
    }
}
