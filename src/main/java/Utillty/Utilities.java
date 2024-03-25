package Utillty;

import Pages.BasePage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Utilities
{
    static WebDriver driver;
    public static WebDriver launchDriver()
    {
        driver = new ChromeDriver();
        driver.get("https://www.demoblaze.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        return driver;
    }
    public static void terminateDriver()
    {
        driver.close();
        driver.quit();
    }
    public static WebElement getElement(WebDriver driver, By by)
    {
        WebElement element = driver.findElement(by);
        return element;
    }
    public static WebElement waitVisibility(WebDriver driver,WebElement Element)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOf(Element));
        return element;
    }
    public static Alert waitAlert(WebDriver driver)
    {
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.alertIsPresent());
    }
    public static List<WebElement> getElementsWithWait(WebDriver driver, By by)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(by)));
        List<WebElement> elements = driver.findElements(by);
        return elements;
    }
    public static List<WebElement> getElements(WebDriver driver, By by)
    {
        List<WebElement> elements = driver.findElements(by);
        return elements;
    }
    public int replaceAndConvertPrice(String price)
    {
        price = price.replace("$","");
        price = price.replace(" *includes tax","").trim();
        return Integer.parseInt(price);
    }

}
