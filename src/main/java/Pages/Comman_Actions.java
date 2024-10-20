package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;

public class Comman_Actions {

    private WebDriver driver;

    public FluentWait Fwait ;
    public List<WebElement> menus ;


    public Comman_Actions(WebDriver driver){
        this.driver = driver;
    }
    public void wait(By locator ){
        Fwait = new FluentWait(driver);
        Fwait.withTimeout(Duration.ofSeconds(10));
        Fwait.pollingEvery(Duration.ofSeconds(1));
        Fwait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    public List<WebElement> getMenuList ( By locator ){
        wait(locator);
        menus = driver.findElements(locator);
        return menus;
    }
}
