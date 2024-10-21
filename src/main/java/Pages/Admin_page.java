package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Admin_page {
    private WebDriver driver;
    private Comman_Actions comman;

    private By navBarEl = By.xpath("//span[@class=\"oxd-text oxd-text--span oxd-main-menu-item--name\"]");
    private By AdminSelectAllUsers = By.cssSelector("div.oxd-checkbox-wrapper");
    private By AdminUsersContenar = By.className("orangehrm-container");
    private By AdminDeleteUsers = By.xpath("//button[@class=\"oxd-button oxd-button--medium oxd-button--label-danger orangehrm-horizontal-margin\"]");
    private By AdminDeleteUsersYesBt = By.cssSelector(" button.oxd-button.oxd-button--medium.oxd-button--label-danger.orangehrm-button-margin");
    private By successSaveMsg = By.xpath("//div[@class=\"oxd-toast oxd-toast--success oxd-toast-container--toast\"]");



    public Admin_page(WebDriver driver){
        this.driver = driver;
        comman = new Comman_Actions(driver);

    }

    public void deletAllUsers(){
        comman.getMenuList(navBarEl);
        comman.menus.get(0).click();
        comman.wait(AdminUsersContenar);

        comman.getMenuList(AdminSelectAllUsers);
        comman.menus.get(0).click();
        driver.findElement(AdminDeleteUsers).click();
        comman.wait(AdminDeleteUsersYesBt);
        driver.findElement(AdminDeleteUsersYesBt).click();
    }
    public boolean isUserDeleted() {
        comman.wait(successSaveMsg);
        return driver.findElement(successSaveMsg).isDisplayed();
    }
}
