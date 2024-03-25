package Pages;

import Utillty.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

public class HomePage extends Utilities
{
    WebDriver driver;
    public BasePage basePage;
    By phones = By.xpath("//a[text()='Phones']");
    By laptops = By.xpath("//a[text()='Laptops']");
    By monitors = By.xpath("//a[text()='Monitors']");
    By titles = By.xpath("//h4[@class='card-title']");
    public HomePage(WebDriver driver)
    {
        this.driver = driver;
        basePage = new BasePage(driver);
    }
    //################################################## PAGE METHODS ######################################################
    public String clickPhones()
    {
        WebElement Phones = Utilities.waitVisibility(driver, Utilities.getElement(driver,phones));
        Phones.click();
        return Phones.getText();
    }
    public String clickLaptops()
    {
        WebElement Laptops = Utilities.getElement(driver,laptops);
        Laptops.click();
        return Laptops.getText();
    }
    public String clickMonitors()
    {
        WebElement Monitors = Utilities.getElement(driver,monitors);
        Monitors.click();
        return Monitors.getText();
    }
    public List<WebElement> returnResults()
    {
        return Utilities.getElementsWithWait(driver,titles);

    }
    public ItemPage selectItems(int item, List<WebElement> result)
    {
        result.get(item).click();
        return new ItemPage(driver);
    }
    public List<String> _getDB(String cat)
    {
        List<String> list = null;
        switch (cat)
        {
            case "Phones" -> list = Arrays.asList("Samsung galaxy s6", "Samsung galaxy s7", "Nokia lumia 1520", "Iphone 6 32gb", "Nexus 6", "Sony xperia z5", "HTC One M9");
            case "Laptops" -> list = Arrays.asList("Sony vaio i5", "Sony vaio i7", "MacBook air", "MacBook Pro",  "Dell i7 8gb", "2017 Dell 15.6 Inch");
            case "Monitors" -> list = Arrays.asList("Apple monitor 24", "ASUS Full HD");
        }
        return list;
    }
    public int verifyElementsOnPage(String categorySelected)
    {
        int fails = 0;
        List<String> listDB = _getDB(categorySelected);
        List<WebElement> Results = Utilities.getElementsWithWait(driver, titles);
        for (WebElement element : Results)
        {
            String result = element.getText();
            boolean isFound = listDB.contains(result);
            if (!isFound) {
                System.out.println(result);
                fails++;
            }
        }
        return fails;
    }
}
