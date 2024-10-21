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

    private WebDriver driver;
    private Login_Page loginPage;
    private DashBoard_Page dashboardPage;
    private Admin_page adminPage;
    private PIM_page pimPage;

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/");
        loginPage = new Login_Page(driver);
        dashboardPage = new DashBoard_Page(driver);
        adminPage = new Admin_page(driver);
        pimPage = new PIM_page(driver);
    }

    @Test()
    public void testLogin() {
        loginPage.login_Page("Admin", "admin123");
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard is not displayed after login");
        adminPage.deletAllUsers();
        String[][] element = {
                {"User1", "user123"},
                {"User15", "user123"},
                {"User16", "user1234"}
        };
        for (int i = 0; i < element.length; i++) {
            pimPage.createNewUser(element[i][0], element[i][1]);
        }
        loginPage.logOut();

    }

    @Test(dataProvider = "dataProvider")
    public void testLoginDataProvider(String User, String Password, boolean status) {
        loginPage.ClearLoginUsername();
        loginPage.ClearLoginPassword();
        loginPage.login_Page(User, Password);
       try {
           Assert.assertEquals(dashboardPage.isDashboardLoaded(), status, "Dashboard is not displayed after login");
       } catch (AssertionError e) {
           Allure.addAttachment("Dashboard is not displayed after login With "+" User:"+User+" Pass:"+Password, new ByteArrayInputStream(saveScreenshotPNG(driver)));
           throw e;
       }
        loginPage.logOut();

    }



    // Text attachments for Allure
    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshotPNG(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @AfterTest
    void closeBrowser() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();
    }

    @DataProvider
    public Object[][] dataProvider() {

        return new Object[][]{
                {"User1", "user123", true},
                {"User15", "user123", true},
                {"", "user123", false},
                {"User15", "", false},
                {"", "", false},
                {"User16", "user1234", true}
        };

    }

}
