import Pages.DashBoard_Page;
import Pages.Login_Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Login_Testing {

    private WebDriver driver;
    private Login_Page loginPage;
    private DashBoard_Page dashboardPage;

    // Sets up WebDriver and initializes pages before each test
    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/");
        loginPage = new Login_Page(driver);
        dashboardPage = new DashBoard_Page(driver);
    }

    // Tests successful login
    @Test
    public void testLogin() {
        loginPage.login_Page("Admin", "admin123");  // Login with valid credentials
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard is not displayed after login");
    }

    // Tests failed login with incorrect password
    @Test
    public void testLogin2() {
        loginPage.login_Page("Admin", "admin1234");  // Login with invalid password
        Assert.assertTrue(loginPage.cantLogin(), "Login attempt succeeded but should have failed");
    }

    // Tests failed login with incorrect username
    @Test
    public void testLogin3() {
        loginPage.login_Page("Adminn", "admin123");  // Login with invalid username
        Assert.assertTrue(loginPage.cantLogin(), "Login attempt succeeded but should have failed");
    }

    // Tests required field validation for password field
    @Test
    public void testLogin4() {
        loginPage.enterLoginUsername("Adminn");  // Entering only username
        loginPage.clickLoginButton();  // Click login without entering password
        Assert.assertTrue(loginPage.requiredField(), "Required field validation failed for password");
    }

    // Tests required field validation for username field
    @Test
    public void testLogin5() {
        loginPage.enterLoginPassword("admin123");  // Entering only password
        loginPage.clickLoginButton();  // Click login without entering username
        Assert.assertTrue(loginPage.requiredField(), "Required field validation failed for username");
    }

    // Cleans up WebDriver instance after each test
    @AfterMethod
    public void closeBrowser() throws InterruptedException {
        Thread.sleep(2000);  // Adding delay for test visibility (not recommended in real tests)
        driver.quit();  // Closes the browser
    }
}
