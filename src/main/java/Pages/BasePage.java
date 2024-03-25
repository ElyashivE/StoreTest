package Pages;

import Utillty.Utilities;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BasePage extends Utilities
{
    WebDriver driver;
    By home = By.xpath("//a[text()='Home ']");
    By contact = By.xpath("//a[text()='Contact']");
    By aboutUs = By.xpath("//a[text()='About us']");
    By cart = By.xpath("//a[text()='Cart']");
    By login = By.xpath("//a[text()='Log in']");
    By signUp = By.xpath("//a[text()='Sign up']");
    By aboutUsBy = By.xpath("//*[@id='videoModal']//*[@class='modal-content']");
    By aboutUsCloseBy = By.xpath("//div[@id='videoModal']//button[text()='Close']");
    By loginBy = By.xpath("//*[@id='logInModal']//*[@class='modal-content']");
    By loginCloseBy = By.xpath("//div[@id='logInModal']//button[text()='Close']");
    By signingBy = By.xpath("//*[@id='signInModal']//*[@class='modal-content']");
    By signingCloseBy = By.xpath("//div[@id='signInModal']//button[text()='Close']");
    By contactBy = By.xpath("//*[@id='exampleModal']//*[@class='modal-content']");
    By contactCloseBy = By.xpath("//div[@id='exampleModal']//button[text()='Close']");
    By emailBy = By.xpath("//*[@id='exampleModal']//div[1]/input");
    By nameBy = By.xpath("//*[@id='exampleModal']//div[2]/input");
    By messageBy = By.xpath("//*[@id='exampleModal']//textarea");
    By sendBtnBy = By.xpath("//*[@id='exampleModal']//*[text()='Send message']");
    public BasePage(WebDriver driver)
    {
        this.driver = driver;

    }
    //################################################## PAGE METHODS ######################################################
    public HomePage goToHome()
    {
        Utilities.getElement(driver, home).click();
        return new HomePage(driver);
    }
    public CartPage goToCart()
    {
        Utilities.getElement(driver, cart).click();
        return new CartPage(driver);
    }
    public boolean openContact()
    {
        Utilities.getElement(driver,contact).click();
        WebElement contactWin = Utilities.waitVisibility(driver, Utilities.getElement(driver, contactBy));

        return contactWin.isDisplayed();
    }
    public void closeContact()
    {
        WebElement closeBtn = Utilities.getElement(driver,contactCloseBy);
        closeBtn.click();
    }
    public boolean openAbout()
    {
        Utilities.getElement(driver,aboutUs).click();
        WebElement aboutUsWin = Utilities.waitVisibility(driver, Utilities.getElement(driver, aboutUsBy));

        return  aboutUsWin.isDisplayed();
    }
    public void closeAbout()
    {
        WebElement closeBtn = Utilities.getElement(driver, aboutUsCloseBy);
        closeBtn.click();
    }
    public boolean openLogin()
    {
        Utilities.getElement(driver,login).click();
        WebElement loginWin = Utilities.waitVisibility(driver, Utilities.getElement(driver, loginBy));

        return loginWin.isDisplayed();
    }
    public void closeLogin()
    {
        WebElement closeBtn = Utilities.getElement(driver, loginCloseBy);
        closeBtn.click();
    }
    public boolean openSignIn()
    {
        Utilities.getElement(driver,signUp).click();
        WebElement signingWin = Utilities.waitVisibility(driver, Utilities.getElement(driver, signingBy));

        return signingWin.isDisplayed();
    }
    public void closeSignIn()
    {
        WebElement closeBtn = Utilities.getElement(driver, signingCloseBy);
        closeBtn.click();
    }
    public String fillContactForm()
    {
        WebElement email = Utilities.getElement(driver,emailBy);
        WebElement name = Utilities.getElement(driver,nameBy);
        WebElement message = Utilities.getElement(driver,messageBy);
        WebElement sendBtn = Utilities.getElement(driver,sendBtnBy);

        email.sendKeys("testName@example.com");
        name.sendKeys("testName");
        message.sendKeys("Hello my name is testName...");
        sendBtn.click();

        Alert alert = driver.switchTo().alert();
        String actualAlertMsg = alert.getText();
        alert.accept();

        return actualAlertMsg;
    }
}
