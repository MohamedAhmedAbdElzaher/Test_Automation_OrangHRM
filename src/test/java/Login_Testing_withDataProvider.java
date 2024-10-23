import Pages.Admin_page;
import Pages.DashBoard_Page;
import Pages.Login_Page;
import Pages.PIM_page;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.ByteArrayInputStream;

public class Login_Testing_withDataProvider {

    private WebDriver driver; // WebDriver instance for browser automation
    private Login_Page loginPage; // Page object for the login page
    private DashBoard_Page dashboardPage; // Page object for the dashboard page
    private Admin_page adminPage; // Page object for the admin page
    private PIM_page pimPage; // Page object for the PIM page

    // Setup method to initialize WebDriver and page objects before any tests run
    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver(); // Initialize ChromeDriver
        driver.manage().window().maximize(); // Maximize the browser window
        driver.get("https://opensource-demo.orangehrmlive.com/"); // Navigate to the login page
        loginPage = new Login_Page(driver); // Create instance of Login_Page
        dashboardPage = new DashBoard_Page(driver); // Create instance of DashBoard_Page
        adminPage = new Admin_page(driver); // Create instance of Admin_page
        pimPage = new PIM_page(driver); // Create instance of PIM_page
    }

    // Test method to verify successful login and user creation
    @Test()
    public void testLogin() {
        // Log in with valid credentials
        loginPage.login_Page("Admin", "admin123");
        // Assert that the dashboard is loaded after login
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard is not displayed after login");

        // Delete all existing users before creating new ones
        adminPage.deletAllUsers();

        // Array of user credentials to create new users
        String[][] element = {
                {"User1", "user123"},
                {"User15", "user123"},
                {"User16", "user1234"}
        };

        // Loop through the array to create each user
        for (int i = 0; i < element.length; i++) {
            pimPage.createNewUser(element[i][0], element[i][1]); // Create new user with username and password
        }

        // Log out after the operations
        loginPage.logOut();
    }

    // Test method to verify login with multiple data sets using DataProvider
    @Test(dataProvider = "dataProvider")
    public void testLoginDataProvider(String User, String Password, boolean status) {
        // Clear any existing input in username and password fields
        loginPage.clearLoginUsername();
        loginPage.clearLoginPassword();

        // Attempt to login with provided credentials
        loginPage.login_Page(User, Password);

        try {
            // Assert that the dashboard's loaded state matches the expected status
            Assert.assertEquals(dashboardPage.isDashboardLoaded(), status, "Dashboard is not displayed after login");
        } catch (AssertionError e) {
            // Capture a screenshot on failure for Allure reporting
            Allure.addAttachment("Dashboard is not displayed after login With " + " User:" + User + " Pass:" + Password, new ByteArrayInputStream(saveScreenshotPNG(driver)));
            throw e; // Rethrow the exception after logging
        }

        // Log out after each test case
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

    // DataProvider for feeding testLoginDataProvider method with various user credentials
    @DataProvider
    public Object[][] dataProvider() {
        return new Object[][]{
                {"User1", "user123", true}, // Valid user
                {"User15", "user123", true}, // Valid user
                {"", "user123", false}, // Empty username
                {"User15", "", false}, // Empty password
                {"", "", false}, // Both username and password empty
                {"User16", "user1234", true} // Valid user with different credentials
        };
    }
}
