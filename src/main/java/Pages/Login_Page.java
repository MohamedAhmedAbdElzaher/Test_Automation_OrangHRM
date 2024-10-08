package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Login_Page {
    private WebDriver driver;
    private By usernameBox = By.xpath("//input[@name=\"username\"]");
    private By PaswordBox  = By.xpath("//input[@name=\"password\"]");
    private By loginButton = By.xpath("//button[@type=\"submit\"]");
    public FluentWait Fwait ;


    public Login_Page(WebDriver driver){
        this.driver = driver;
    }
    public void wait(By locator ){
        Fwait = new FluentWait(driver);
        Fwait.withTimeout(Duration.ofSeconds(10));
        Fwait.pollingEvery(Duration.ofSeconds(1));
        Fwait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }
    public void login_Page(String username , String password ){
        wait(loginButton);
        driver.findElement(usernameBox).sendKeys(username);
        driver.findElement(PaswordBox).sendKeys(password);
        driver.findElement(loginButton).click();

    }
    public void enterLoginUsername (String username){
        wait(usernameBox);
        driver.findElement(usernameBox).sendKeys(username);
    }
    public void enterLoginPassword (String password){
        wait(PaswordBox);
        driver.findElement(PaswordBox).sendKeys(password);
    }
    public void clickLoginButton(){
        wait(loginButton);
        driver.findElement(loginButton).click();
    }
}
