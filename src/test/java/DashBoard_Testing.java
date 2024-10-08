import Pages.DashBoard_Page;
import Pages.Login_Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class DashBoard_Testing {
    WebDriver driver;
    Login_Page loginPage;
    DashBoard_Page dashboardPage;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/");
        loginPage = new Login_Page(driver);
        dashboardPage = new DashBoard_Page(driver);
    }

//    @Test
//    public void testMenus() {
//
//        loginPage.login_Page("Admin", "admin123");
//         List<WebElement> km = dashboardPage.getMenuList();
//        System.out.println(dashboardPage.menuListSize()+ Arrays.toString(km.toArray())+km.getFirst().getText());
//    }
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

       // Assert.assertTrue(dashboardPage.isElementsContainsProvidedText("My Actions"));
        List<String> expectedStrings = Arrays.asList("My Actions","Quick Launch","Employee Distribution by Sub Unit"
                                                    ,"Employee Distribution by Location","Employees on Leave Today"
                                                    ,"Buzz Latest Posts","Time at Work");

        for (String expected : expectedStrings) {
            Assert.assertTrue(dashboardPage.isElementsContainsProvidedText(expected), "Expected text not found: " + expected);
        }
    }
    @Test(testName = "Verify customize of dashboard view for the Admin Account  " , priority = 3 , dependsOnMethods = "VerifyAdminDashboard")
    public void VerifyCustomizeOfDashboardAdminView () {
        Assert.assertTrue(dashboardPage.isCustomizButtonLoded());
    }
//    @Test
//    public void testDashboardAccessAfterLogin() {
//        loginPage.Login_Page("Admin", "admin123");
//        Assert.assertTrue(dashboardPage.isDashboardDisplayed(), "Dashboard is not displayed after login");
//    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }
}
