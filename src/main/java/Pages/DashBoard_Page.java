package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;

public class DashBoard_Page {

    WebDriver driver;
    private List<WebElement> menus ;


    private By dashboard = By.xpath("//div[@class=\"orangehrm-dashboard-widget-name\"]");
    private By customizeBt = By.xpath("//div[@class=\"orangehrm-customizeBt\"]");


    public FluentWait Fwait ;

    public DashBoard_Page(WebDriver driver){
        this.driver = driver;
    }

    public void wait(By locator ){
        Fwait = new FluentWait(driver);
        Fwait.withTimeout(Duration.ofSeconds(10));
        Fwait.pollingEvery(Duration.ofSeconds(1));
        Fwait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }
    public boolean isDashboardLoaded(){
        wait(dashboard);
        return driver.findElement(dashboard).isDisplayed();
    }
    public List<WebElement> getMenuList (){
        wait(dashboard);
        menus = driver.findElements(dashboard);
        return menus;
    }
    public int menuListSize () {
        return this.menus.size();
    }

    public boolean isElementsContainsProvidedText(String text){

        List<WebElement> elements = driver.findElements(dashboard);
        return elements.stream().anyMatch(e->e.getText().trim().equals(text));
    }
    public boolean isCustomizButtonLoded(){
        wait(dashboard);
        return driver.findElement(customizeBt).isDisplayed();
    }
//    public List<String> listWedhetNames (){
//         List<String> wList ;
//        for(int i= 0; i<menuListSize(); i++ ){
//            wList.indexOf(i) = menus.contains("")
//        }
//    }
//    public boolean  isMyActionsLoaded(){
//        wait(dashboard);
//         menus.getFirst().getText();
//    }
//    public boolean isManagerMetricsDisplayed() {
//        return managerMetrics.isDisplayed();
//    }
//
//    public boolean isEmployeeLeaveRequestsDisplayed() {
//        return employeeLeaveRequests.isDisplayed();
//    }
}
