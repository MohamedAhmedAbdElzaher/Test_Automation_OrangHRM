import Pages.Admin_page;
import Pages.DashBoard_Page;
import Pages.Login_Page;
import Pages.PIM_page;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

public class DashBoard_Testing {
    WebDriver driver;
    Login_Page loginPage;
    DashBoard_Page dashboardPage;
    SoftAssert softAssert = new SoftAssert();
    private Admin_page adminPage;
    private PIM_page pimPage;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/");
        loginPage = new Login_Page(driver);
        dashboardPage = new DashBoard_Page(driver);
        adminPage = new Admin_page(driver);
        pimPage = new PIM_page(driver);
    }

    @Test(testName = "dashboard is accessible after successful login" , priority = 1)
    public void DashBoardLoading () {
        loginPage.login_Page("Admin", "admin123");
        Assert.assertTrue(dashboardPage.isDashboardLoaded());
    }
    @Test(testName = "Verify Admin's dashboard  " , priority = 2 , dependsOnMethods = "DashBoardLoading")
    public void VerifyAdminDashboard () {
        dashboardPage.getMenuList();
        Assert.assertEquals(dashboardPage.menuListSize(),7);
    }
    @Test(testName = "Verify the key functionalities for the Admin Account  " , priority = 3 , dependsOnMethods = "VerifyAdminDashboard")
    public void VerifyAdminDashboardFunctionality () {


        List<String> expectedStrings = Arrays.asList("My Actions","Quick Launch","Employee Distribution by Sub Unit"
                                                    ,"Employee Distribution by Location","Employees on Leave Today"
                                                    ,"Buzz Latest Posts","Time at Work");

        for (String expected : expectedStrings) {
            Assert.assertTrue(dashboardPage.isElementsContainsProvidedText(expected), "Expected text not found: " + expected);
        }
    }
    @Test(testName = "Verify customize of dashboard view for the Admin Account  " , priority = 3 , dependsOnMethods = "VerifyAdminDashboard")
    public void VerifyCustomizeOfDashboardAdminView () {
        try {

            softAssert.assertTrue(dashboardPage.isCustomizButtonLoded());
        }catch (NoSuchElementException e) {
            Allure.addAttachment("Dashboard does not have customize button after login With "+" User:Admin & Pass:admin123", new ByteArrayInputStream(saveScreenshotPNG(driver)));
            throw e;
        }

    }


    @Test(testName = "dashboard is accessible after successful login With User Account" , priority = 4)
    public void UserDashBoardLoading () {
        adminPage.deletAllUsers();
        pimPage.createNewUser("User12", "user123");
        loginPage.logOut();
        loginPage.login_Page("User12", "user123");
        Assert.assertTrue(dashboardPage.isDashboardLoaded());
    }
    @Test(testName = "Verify User's dashboard  " , priority = 5 , dependsOnMethods = "UserDashBoardLoading")
    public void VerifyUserDashboard () {
        dashboardPage.getMenuList();

        try {
            Assert.assertEquals(dashboardPage.menuListSize(),4);
        }catch (AssertionError e) {
            Allure.addAttachment("Dashboard does not have corect tools after login With "+" User:user12 & Pass:user123", new ByteArrayInputStream(saveScreenshotPNG(driver)));
            throw e;
        }

    }
    @Test(testName = "Verify the key functionalities for the User Account  " , priority = 6 , dependsOnMethods = "VerifyUserDashboard")
    public void VerifyUserDashboardFunctionality () {


        List<String> expectedStrings = Arrays.asList("My Actions","Quick Launch","Buzz Latest Posts","Time at Work");

        for (String expected : expectedStrings) {
            Assert.assertTrue(dashboardPage.isElementsContainsProvidedText(expected), "Expected text not found: " + expected);
        }
    }
    @Test(testName = "Verify customize of dashboard view for the User Account  " , priority = 5 , dependsOnMethods = "UserDashBoardLoading")
    public void VerifyCustomizeOfDashboardUserView () {
        try {
            softAssert.assertEquals(dashboardPage.isCustomizButtonLoded(),true,"Dashboard does not have customize button after login With "+" User:Admin & Pass:admin123");
        }catch (NoSuchElementException e) {
            Allure.addAttachment("Dashboard does not have customize button after login With "+" User:Admin & Pass:admin123", new ByteArrayInputStream(saveScreenshotPNG(driver)));
            throw e;
        }
    }




    // Text attachments for Allure
    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshotPNG(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }



}
