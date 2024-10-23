import Pages.Admin_page; // Import Admin page class for admin operations.
import Pages.DashBoard_Page; // Import Dashboard page class for dashboard operations.
import Pages.Login_Page; // Import Login page class for login operations.
import io.qameta.allure.Allure; // Import Allure for reporting.
import io.qameta.allure.Attachment; // Import Allure's Attachment annotation for screenshots.
import org.openqa.selenium.OutputType; // Import Selenium OutputType for capturing screenshots.
import org.openqa.selenium.TakesScreenshot; // Import TakesScreenshot interface for taking screenshots.
import org.openqa.selenium.WebDriver; // Import WebDriver interface to control the browser.
import org.openqa.selenium.chrome.ChromeDriver; // Import ChromeDriver to control the Chrome browser.
import org.testng.Assert; // Import TestNG's Assert class for assertions.
import org.testng.annotations.AfterTest; // Define a method to be executed after all tests in the class.
import org.testng.annotations.BeforeTest; // Define a method to be executed before any tests in the class.
import org.testng.annotations.Test; // Define test methods for TestNG.

import java.io.ByteArrayInputStream; // Import ByteArrayInputStream for reading screenshot data.

public class Admin_Testing {

    private WebDriver driver; // WebDriver instance to control browser.
    private Login_Page loginPage; // Instance of Login_Page for login-related actions.
    private DashBoard_Page dashboardPage; // Instance of DashBoard_Page for dashboard-related actions.
    private Admin_page adminPage; // Instance of Admin_page for admin-related actions.

    // Method to set up the test environment.
    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver(); // Initialize ChromeDriver for using Chrome browser.
        driver.manage().window().maximize(); // Maximize the browser window.
        driver.get("https://opensource-demo.orangehrmlive.com/"); // Open the OrangeHRM demo website.
        loginPage = new Login_Page(driver); // Initialize Login_Page with WebDriver.
        dashboardPage = new DashBoard_Page(driver); // Initialize DashBoard_Page with WebDriver.
        adminPage = new Admin_page(driver); // Initialize Admin_page with WebDriver.
    }

    // Test method to verify user deletion functionality.
    @Test()
    public void testClearUsers() {
        loginPage.login_Page("Admin", "admin123"); // Login with admin credentials.

        // Verify if the dashboard is loaded after login.
        try {
            Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard is not displayed after login");
        } catch (AssertionError e) {
            // Attach screenshot if the dashboard is not displayed.
            Allure.addAttachment("Dashboard is not displayed after login With " + "User:Admin Pass:admin123",
                    new ByteArrayInputStream(saveScreenshotPNG(driver)));
            throw e;
        }

        adminPage.deletAllUsers(); // Delete all users from admin page.

        // Verify if the user is deleted successfully.
        try {
            Assert.assertTrue(adminPage.isUserDeleted(), "Can't delete user after login");
        } catch (AssertionError e) {
            // Attach screenshot if user deletion fails.
            Allure.addAttachment("Can't delete user after login With " + "User:Admin Pass:admin123",
                    new ByteArrayInputStream(saveScreenshotPNG(driver)));
            throw e;
        }

        loginPage.logOut(); // Logout after performing the operations.
    }

    // Method to capture and return a screenshot, attached to Allure reports.
    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshotPNG(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES); // Capture screenshot as byte array.
    }

    // Method to close the browser after tests.
    @AfterTest
    void closeBrowser() throws InterruptedException {
        Thread.sleep(2000); // Wait for 2 seconds before closing the browser.
        driver.quit(); // Close the browser and end the session.
    }
}
