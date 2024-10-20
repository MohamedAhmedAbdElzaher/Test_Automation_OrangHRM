package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Login_Page {
    private WebDriver driver;
    private Comman_Actions comman ;

    private By usernameBox = By.xpath("//input[@name=\"username\"]");
    private By PaswordBox  = By.xpath("//input[@name=\"password\"]");
    private By loginButton = By.xpath("//button[@type=\"submit\"]");
    private By invalidCridention = By.xpath("//p[@class = \"oxd-text oxd-text--p oxd-alert-content-text\"]");
    private By reqired = By.xpath("//span[@class=\"oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message\"]");
    private By dropdownmiu = By.xpath("//i[@class=\"oxd-icon bi-caret-down-fill oxd-userdropdown-icon\"]");
    private By dropdownmiuEl = By.xpath("//a[@class=\"oxd-userdropdown-link\"]");






   public Login_Page(WebDriver driver) {
       this.driver = driver;
       comman = new Comman_Actions(driver);
   }
    public void login_Page(String username , String password ){

        comman.wait(loginButton);
        driver.findElement(usernameBox).sendKeys(username);
        driver.findElement(PaswordBox).sendKeys(password);
        driver.findElement(loginButton).click();

    }
    public void enterLoginUsername (String username){

        comman.wait(usernameBox);
        driver.findElement(usernameBox).sendKeys(username);
    }
    public void ClearLoginUsername () {

        comman.wait(usernameBox);
        driver.findElement(usernameBox).sendKeys(Keys.CONTROL + "a");
        driver.findElement(usernameBox).sendKeys(Keys.DELETE);
    }
    public void enterLoginPassword (String password){

        comman.wait(PaswordBox);
        driver.findElement(PaswordBox).sendKeys(password);
    }
    public void ClearLoginPassword () {

        comman.wait(PaswordBox);
        driver.findElement(PaswordBox).sendKeys(Keys.CONTROL + "a");
        driver.findElement(PaswordBox).sendKeys(Keys.DELETE);
    }
    public void clickLoginButton(){

        driver.findElement(loginButton).click();
    }
    public boolean cantlogin(){
        return driver.findElements(invalidCridention).size() == 0;
    }
    public boolean requiredFeald(){
        return driver.findElements(reqired).size() == 1;
    }



    public void logOut () {


        try {
            driver.findElement(dropdownmiu).click();
            comman.getMenuList(dropdownmiuEl);
            comman.menus.getLast().click();
        }catch (NoSuchElementException e){}

    }

}
