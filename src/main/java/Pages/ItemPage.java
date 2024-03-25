package Pages;

import Utillty.Utilities;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ItemPage extends Utilities
{

    WebDriver driver;
    public BasePage basePage;

    By itemName = By.xpath("//h2");
    By itemPrice = By.xpath("//h3");
    By addToCartBtn = By.xpath("//a[text()='Add to cart']");
    public ItemPage(WebDriver driver)
    {
        this.driver = driver;
        basePage = new BasePage(driver);
    }
    //################################################## PAGE METHODS ######################################################
    public String getItemName()
    {
        WebElement name = Utilities.waitVisibility(driver, Utilities.getElement(driver,itemName));
        return name.getText();
    }
    public int getPrice()
    {
        WebElement Price = Utilities.waitVisibility(driver, Utilities.getElement(driver,itemPrice));
        String price = Price.getText();
        return replaceAndConvertPrice(price);
    }
    public void addItem()
    {
        WebElement addItem = Utilities.getElement(driver,addToCartBtn);
        addItem.click();
    }
    public String getAlertMsg()
    {
        Alert alert = Utilities.waitAlert(driver);
        return alert.getText();
    }
    public void closeAlert()
    {
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }
}
