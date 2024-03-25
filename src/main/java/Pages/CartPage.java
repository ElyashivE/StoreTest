package Pages;

import DB.CostumerDB;
import Utillty.Utilities;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.*;

public class CartPage extends Utilities
{
    WebDriver driver;
    BasePage basePage;
    By productsBy = By.xpath("//tr[@class='success']");
    By productsTitleBy = By.xpath("//tr[@class='success']/td[2]");
    By productsPriceBy = By.xpath("//tr[@class='success']/td[3]");
    By totalPriceBy = By.xpath("//h3[@id='totalp']");
    By placeOrder = By.xpath("//button[text()='Place Order']");
    By orderBy = By.xpath("//*[@id='orderModal']//*[@class='modal-content']");
    By orderPurchaseBy = By.xpath("//*[text()='Purchase']");
    By orderCloseBy = By.xpath("//div[@id='orderModal']//button[text()='Close']");
    By confirmMsgBy = By.xpath("//*[text()='Thank you for your purchase!']");
    By confirmDataBy = By.xpath("//p[contains(@class,'lead')]");
    By results = By.xpath("//div[@id='tbodyid']");

    public CartPage(WebDriver driver)
    {
        this.driver = driver;
        basePage = new BasePage(driver);
    }
    //################################################## PAGE METHODS ######################################################
    public int getProductsInCart()
    {
        List<WebElement> elements = Utilities.getElements(driver,productsBy);
        return elements.size();
    }
    public String getTitle(int i)
    {
        List<WebElement> name = Utilities.getElements(driver,productsTitleBy);
        return name.get(i).getText();
    }
    public int getPrice(int i)
    {
        List<WebElement> Price = Utilities.getElements(driver,productsPriceBy);
        String price = Price.get(i).getText();
        return Integer.parseInt(price);
    }
    public String getTotalPrice()
    {
        WebElement element = Utilities.getElement(driver, totalPriceBy);
        return element.getText();
    }
    public boolean openPlaceOrder()
    {
        WebElement placeOrderBtn = Utilities.waitVisibility(driver, Utilities.getElement(driver,placeOrder));
        placeOrderBtn.click();
        WebElement orderWin = Utilities.waitVisibility(driver, Utilities.getElement(driver, orderBy));

        return orderWin.isDisplayed();
    }
    public void closePlaceOrder()
    {
        WebElement closeBtn = Utilities.waitVisibility(driver, Utilities.getElement(driver, orderCloseBy));
        closeBtn.click();
    }
    public void purchase()
    {
        WebElement purchaseBtn = Utilities.waitVisibility(driver, Utilities.getElement(driver, orderPurchaseBy));
        purchaseBtn.click();
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
    public List<String> getPurchaseData()
    {
        WebElement confirmMsg = Utilities.waitVisibility(driver, Utilities.getElement(driver,confirmMsgBy));
        WebElement confirmData = Utilities.waitVisibility(driver, Utilities.getElement(driver,confirmDataBy));
        String data = confirmData.getText();
        String[] splitData = data.split("\n");
        String id = splitData[0];
        String amount = splitData[1];
        String card = splitData[2];
        String name = splitData[3];
        String date = splitData[4];

        List<String> list = new ArrayList<>();
        list.add(amount);
        list.add(card);
        list.add(name);
        list.add(date);
        return list;
    }
//############################################### TEST FUNCTIONS #######################################################
    public String _trimPrice(String price)
    {
        price = price.replace("Amount: ","");
        price = price.replace(" USD","");

        return price;
    }
    public String _trimName(String name)
    {
        name = name.replace("Name: ","");

        return name;
    }
    public String _trimCardNumber(String cardNum)
    {
        cardNum = cardNum.replace("Card Number: ","");
        return cardNum;
    }
    public String _trimDateType(String date, String type)
    {
        date = date.replace("Date: ","");
        String [] splitDate = date.split("/");
        String Sday = splitDate[0];
        String Smonth = splitDate[1];
        String Syear = splitDate[2];

        if(type.equals("day"))
            return Sday;
        if (type.equals("month"))
            return Smonth;
        if (type.equals("year"))
            return Syear;
        else
            return "wrong input";
    }
    public Dictionary<String, String> fillForm()
    {
        Dictionary<String, String> customer = CostumerDB.getFakeCustomerDB();
        Enumeration<String> keys = customer.keys();
        while (keys.hasMoreElements())
        {
            String key = keys.nextElement();
            String value = customer.get(key);
            Utilities.getElement(driver,By.id(key)).sendKeys(value);
        }
        return customer;
    }
}
