import Pages.Login_Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Login_Testing {

    private WebDriver driver;
    private Login_Page loginPage;
    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/");
        loginPage = new Login_Page(driver);

    }
    @Test
    public void testLogin() {
        loginPage.login_Page("Admin","admin123");
    }
}
