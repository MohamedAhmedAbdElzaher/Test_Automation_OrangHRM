import Pages.Admin_page;
import Pages.DashBoard_Page;
import Pages.Login_Page;
import Pages.PIM_page;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.apache.maven.surefire.shared.lang3.RandomStringUtils;
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



    private WebDriver driver;
    private Login_Page loginPage;
    private DashBoard_Page dashboardPage;
    private Admin_page adminPage;
    private PIM_page pimpage;

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/");
        loginPage = new Login_Page(driver);
        dashboardPage = new DashBoard_Page(driver);
        adminPage = new Admin_page(driver);
        pimpage = new PIM_page(driver);

    }
    @Test()
    public void testClearUsers() {
        loginPage.login_Page("Admin", "admin123");
        try {
            Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard is not displayed after login");
        } catch (AssertionError e) {
            Allure.addAttachment("Dashboard is not displayed after login With "+" User:Admin Pass:admin123", new ByteArrayInputStream(saveScreenshotPNG(driver)));
            throw e;
        }
        String user = RandomStringUtils.randomAlphanumeric(8);
        String pass = RandomStringUtils.randomAlphanumeric(8);
        pimpage.createNewUser(user,pass);
        try {
            Assert.assertTrue(pimpage.isUsersaved(), "New user is not saved");
        } catch (AssertionError e) {
            Allure.addAttachment("Can't create neu user with "+" User:"+user+" Pass:"+pass, new ByteArrayInputStream(saveScreenshotPNG(driver)));
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



}
