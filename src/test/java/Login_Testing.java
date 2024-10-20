import Pages.DashBoard_Page;
import Pages.Login_Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Login_Testing {

    private WebDriver driver;
    private Login_Page loginPage;
    DashBoard_Page dashboardPage;


    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/");
        loginPage = new Login_Page(driver);
        dashboardPage = new DashBoard_Page(driver);

    }

    @Test
    public void testLogin() {
        loginPage.login_Page("Admin", "admin123");
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard is not displayed after login");

    }
    @Test
    public void testLogin2() {
        loginPage.login_Page("Admin", "admin1234");
        Assert.assertTrue(loginPage.cantlogin());

    }
    @Test
    public void testLogin3() {
        loginPage.login_Page("Adminn", "admin123");
        Assert.assertTrue(loginPage.cantlogin());
    }
    @Test
    public void testLogin4() {
        loginPage.enterLoginUsername("Adminn");
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.requiredFeald());
    }
    @Test
    public void testLogin5() {
        loginPage.enterLoginPassword("admin123");
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.requiredFeald());
    }

    @AfterMethod
    void closeBrowser() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();
    }

}
