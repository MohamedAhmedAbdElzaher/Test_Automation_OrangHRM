package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PIM_page {

    private WebDriver driver;
    private Comman_Actions comman;

    private By navBarEl = By.xpath("//span[@class=\"oxd-text oxd-text--span oxd-main-menu-item--name\"]");
    private By addUserBt = By.xpath("//button[@class=\"oxd-button oxd-button--medium oxd-button--secondary\"]");
    private By addUserEmpFirstName = By.xpath("//input[@class=\"oxd-input oxd-input--active orangehrm-firstname\"]");
    private By addUserEmpLastName = By.xpath("//input[@class=\"oxd-input oxd-input--active orangehrm-lastname\"]");
    private By addUserCreatLoginData = By.xpath("//span[@class=\"oxd-switch-input oxd-switch-input--active --label-right\"]");
    private By addUserCreatTextBoxInList = By.xpath("//input[@class=\"oxd-input oxd-input--active\"]");
    private By addUserSaveBt = By.xpath("//button[@class=\"oxd-button oxd-button--medium oxd-button--secondary orangehrm-left-space\"]");
    private By successSaveMsg = By.xpath("//div[@class=\"oxd-toast oxd-toast--success oxd-toast-container--toast\"]");
    private By newUserData = By.xpath("//div[@class=\"orangehrm-edit-employee-imagesection\"]");



    public PIM_page(WebDriver driver){
        this.driver = driver;
        comman = new Comman_Actions(driver);

    }


    public void createNewUser ( String username , String password ){
        comman.getMenuList(navBarEl);
        comman.menus.get(1).click();
        comman.wait(addUserBt);
        driver.findElement(addUserBt).click();
        comman.wait(addUserEmpFirstName);
        driver.findElement(addUserEmpFirstName).sendKeys("Zaher");
        driver.findElement(addUserEmpLastName).sendKeys("Only");
        comman.wait(addUserCreatLoginData);
        driver.findElement(addUserCreatLoginData).click();
        comman.getMenuList(addUserCreatTextBoxInList);
        comman.menus.get(2).sendKeys(username);
        comman.menus.get(3).sendKeys(password);
        comman.menus.get(4).sendKeys(password);
        driver.findElement(addUserSaveBt).click();
        comman.wait(successSaveMsg);
        comman.wait(newUserData);
    }
    public boolean isUsersaved() {
       return driver.findElement(newUserData).isDisplayed();
    }
}
