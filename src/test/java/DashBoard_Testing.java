import Pages.Admin_page; // Import Admin page class for admin operations.
import Pages.DashBoard_Page; // Import Dashboard page class for dashboard operations.
import Pages.Login_Page; // Import Login page class for login operations.
import Pages.PIM_page; // Import PIM page class for personal information management operations.
import io.qameta.allure.Allure; // Import Allure library for reporting.
import io.qameta.allure.Attachment; // Import Allure's Attachment annotation for adding attachments like screenshots.
import org.openqa.selenium.*; // Import all Selenium WebDriver components.
import org.openqa.selenium.chrome.ChromeDriver; // Import ChromeDriver to control the Chrome browser.
import org.testng.Assert; // Import TestNG's Assert for assertions in tests.
import org.testng.annotations.AfterClass; // Define a method to be executed after all tests in the class.
import org.testng.annotations.BeforeClass; // Define a method to be executed before any tests in the class.
import org.testng.annotations.DataProvider; // Allow external data feeding for test methods (not used in this class).
import org.testng.annotations.Test; // Define test methods for TestNG.
import org.testng.asserts.SoftAssert; // Import TestNG's SoftAssert for multiple assertions without stopping the test.

import java.io.ByteArrayInputStream; // Import ByteArrayInputStream for reading screenshot data.
import java.util.Arrays; // Import Arrays class for working with arrays.
import java.util.List; // Import List interface to handle lists of elements.

public class DashBoard_Testing {

    WebDriver driver; // WebDriver instance to control browser.
    Login_Page loginPage; // Instance of Login_Page for login-related actions.
    DashBoard_Page dashboardPage; // Instance of DashBoard_Page for dashboard-related actions.
    SoftAssert softAssert = new SoftAssert(); // SoftAssert instance for flexible assertions.
    private Admin_page adminPage; // Private instance of Admin_page for admin-related actions.
    private PIM_page pimPage; // Private instance of PIM_page for user creation.

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver(); // Initialize ChromeDriver to use Chrome browser.
        driver.manage().window().maximize(); // Maximize the browser window.
        driver.get("https://opensource-demo.orangehrmlive.com/"); // Open the OrangeHRM demo website.
        loginPage = new Login_Page(driver); // Initialize Login_Page with WebDriver.
        dashboardPage = new DashBoard_Page(driver); // Initialize DashBoard_Page.
        adminPage = new Admin_page(driver); // Initialize Admin_page.
        pimPage = new PIM_page(driver); // Initialize PIM_page.
    }

    // Test to verify if dashboard loads after successful login.
    @Test(testName = "dashboard is accessible after successful login", priority = 1)
    public void DashBoardLoading() {
        loginPage.login_Page("Admin", "admin123"); // Login with admin credentials.
        Assert.assertTrue(dashboardPage.isDashboardLoaded()); // Assert if dashboard is loaded.
    }

    // Test to verify if Admin's dashboard contains 7 menu items.
    @Test(testName = "Verify Admin's dashboard", priority = 2, dependsOnMethods = "DashBoardLoading")
    public void VerifyAdminDashboard() {
        dashboardPage.getMenuList(); // Get list of menu items.
        Assert.assertEquals(dashboardPage.menuListSize(), 7); // Assert there are 7 menu items.
    }

    // Test to verify key functionalities on Admin's dashboard.
    @Test(testName = "Verify the key functionalities for the Admin Account", priority = 3, dependsOnMethods = "VerifyAdminDashboard")
    public void VerifyAdminDashboardFunctionality() {
        List<String> expectedStrings = Arrays.asList("My Actions", "Quick Launch", "Employee Distribution by Sub Unit",
                "Employee Distribution by Location", "Employees on Leave Today",
                "Buzz Latest Posts", "Time at Work"); // Expected dashboard items.

        for (String expected : expectedStrings) {
            Assert.assertTrue(dashboardPage.isElementsContainsProvidedText(expected), "Expected text not found: " + expected);
        } // Assert that all expected functionalities are present.
    }

    // Test to verify if customize button is loaded on Admin's dashboard.
    @Test(testName = "Verify customize of dashboard view for the Admin Account", priority = 3, dependsOnMethods = "VerifyAdminDashboard")
    public void VerifyCustomizeOfDashboardAdminView() {
        try {
            Assert.assertTrue(dashboardPage.isCustomizButtonLoded()); // Assert that the customize button is loaded.
        } catch (AssertionError e) {
            Allure.addAttachment("Dashboard does not have customize button after login With User:Admin & Pass:admin123",
                    new ByteArrayInputStream(saveScreenshotPNG(driver))); // Attach screenshot if element not found.
            throw e;
        }
    }

    // Test to verify if User's dashboard is accessible after successful login.
    @Test(testName = "dashboard is accessible after successful login With User Account", priority = 4)
    public void UserDashBoardLoading() {
        adminPage.deletAllUsers(); // Delete all users as Admin.
        pimPage.createNewUser("User12", "user123"); // Create a new user.
        loginPage.logOut(); // Logout as Admin.
        loginPage.login_Page("User12", "user123"); // Login with user credentials.
        Assert.assertTrue(dashboardPage.isDashboardLoaded()); // Assert if dashboard is loaded for the user.
    }

    // Test to verify if User's dashboard contains 4 menu items.
    @Test(testName = "Verify User's dashboard", priority = 5, dependsOnMethods = "UserDashBoardLoading")
    public void VerifyUserDashboard() {
        dashboardPage.getMenuList(); // Get list of menu items.
        try {
            Assert.assertEquals(dashboardPage.menuListSize(), 4); // Assert that there are 4 menu items.
        } catch (AssertionError e) {
            Allure.addAttachment("Dashboard does not have correct tools after login With User:user12 & Pass:user123",
                    new ByteArrayInputStream(saveScreenshotPNG(driver))); // Attach screenshot if assertion fails.
            throw e;
        }
    }

    // Test to verify key functionalities on User's dashboard.
    @Test(testName = "Verify the key functionalities for the User Account", priority = 6, dependsOnMethods = "VerifyUserDashboard")
    public void VerifyUserDashboardFunctionality() {
        List<String> expectedStrings = Arrays.asList("My Actions", "Quick Launch", "Buzz Latest Posts", "Time at Work"); // Expected dashboard items for the user.

        for (String expected : expectedStrings) {
            Assert.assertTrue(dashboardPage.isElementsContainsProvidedText(expected), "Expected text not found: " + expected);
        } // Assert that all expected functionalities are present for the user.
    }

    // Test to verify if customize button is loaded on User's dashboard.
    @Test(testName = "Verify customize of dashboard view for the User Account", priority = 5, dependsOnMethods = "UserDashBoardLoading")
    public void VerifyCustomizeOfDashboardUserView() {
        try {
            Assert.assertEquals(dashboardPage.isCustomizButtonLoded(), true, "Dashboard does not have customize button after login With User:Admin & Pass:admin123");
        } catch (AssertionError e) {
            Allure.addAttachment("Dashboard does not have customize button after login With User:Admin & Pass:admin123",
                    new ByteArrayInputStream(saveScreenshotPNG(driver))); // Attach screenshot if element not found.
            throw e;
        }
    }

    // Method to take a screenshot and attach it to Allure reports.
    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshotPNG(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES); // Return screenshot as byte array.
    }

    @AfterClass
    public void teardown() {
        driver.quit(); // Close the browser after tests.
    }
}
