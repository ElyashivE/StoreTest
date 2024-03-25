import Pages.CartPage;
import Pages.HomePage;
import Pages.ItemPage;
import Utillty.Utilities;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class MainTest extends ExtantManager
{
    static Dictionary<String, Integer> myItems = new Hashtable<>();
    HomePage homePage;
    ItemPage itemPage;
    CartPage cartPage;
    static WebDriver driver;
    @BeforeTest
    public void initSetup()
    {
        driver = Utilities.launchDriver();
        homePage = new HomePage(driver);
        initializeReport();
    }
    @AfterMethod
    public static void getResult(ITestResult result) throws Exception
    {
        if(result.getStatus() == ITestResult.FAILURE)
        {
            test.fail(MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
            test.fail(MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
            String screenshotPath = CaptureScreenshot(driver);
            test.fail("Test Case Failed Snapshot is below " + test.addScreenCaptureFromPath(screenshotPath));
        }
        else if(result.getStatus() == ITestResult.SKIP)
        {
            test.skip(MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
        }
        else if(result.getStatus() == ITestResult.SUCCESS)
        {
            test.pass(MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
        }
    }
    @AfterTest
    public void tearDown()
    {
        Utilities.terminateDriver();
        extent.flush();
    }
    @Test(priority = 1)
    public void verifySearchResultsPhones()
    {
        String testName = "Verify Search Result for Phones";
        String testDescription = "We are sorting the list by category Phones and compare results to DB";
        test = extent.createTest(testName,testDescription);

        test.info("Starting... create HomePage object");
        homePage = new HomePage(driver);
        test.info("Sorting results by Phones");
        String category = homePage.clickPhones();
        test.info("Verify results on page");
        int fails = homePage.verifyElementsOnPage(category);
        Assert.assertEquals(fails,0);
    }
    @Test(priority = 2)
    public void verifySearchResultsLaptops()
    {
        String testName = "Verify Search Result for Laptops";
        String testDescription = "We are sorting the list by category Laptops and compare results to DB";
        test = extent.createTest(testName,testDescription);

        test.info("Starting... create HomePage object");
        homePage = new HomePage(driver);
        test.info("Sorting results by Laptops");
        String category = homePage.clickLaptops();
        test.info("Verify results on page");
        int fails = homePage.verifyElementsOnPage(category);
        Assert.assertEquals(fails,0);
    }
    @Test(priority = 3)
    public void verifySearchResultsMonitors()
    {
        String testName = "Verify Search Result for Monitors";
        String testDescription = "We are sorting the list by category Monitors and compare results to DB";
        test = extent.createTest(testName,testDescription);

        test.info("Starting... create HomePage object");
        homePage = new HomePage(driver);
        test.info("Sorting results by Monitors");
        String category = homePage.clickMonitors();
        int fails = homePage.verifyElementsOnPage(category);
        Assert.assertEquals(fails,0);
    }
    @Test(priority = 4)
    public void navigateBasePage()
    {
        String testName = "Navigation Base Page";
        String testDescription = "Verify visibility of popups and navigation to expected Pages";
        test = extent.createTest(testName,testDescription);

        boolean isVisible;
        String expectedURL;
        String actualURL;

        expectedURL = "https://www.demoblaze.com/index.html";
        test.info("Starting... create HomePage object");
        homePage = new HomePage(driver);
        test.info("Navigate to HomePage via BasePage");
        homePage.basePage.goToHome();
        test.info("Fetch current URL address for comparison");
        actualURL = driver.getCurrentUrl();
        Assert.assertEquals(actualURL,expectedURL);

        test.info("Verify Contact visibility");
        isVisible = homePage.basePage.openContact();
        test.info("Close Contact");
        homePage.basePage.closeContact();
        Assert.assertTrue(isVisible);

        test.info("Verify About Visibility");
        isVisible = homePage.basePage.openAbout();
        test.info("Close About");
        homePage.basePage.closeAbout();
        Assert.assertTrue(isVisible);

        expectedURL = "https://www.demoblaze.com/cart.html";
        test.info("Navigate to CartPage via BasePage");
        homePage.basePage.goToCart();
        test.info("Fetch current URL address for comparison");
        actualURL = driver.getCurrentUrl();
        Assert.assertEquals(actualURL,expectedURL);

        test.info("Verify Login visibility");
        isVisible = homePage.basePage.openLogin();
        test.info("Close Login");
        homePage.basePage.closeLogin();
        Assert.assertTrue(isVisible);

        test.info("Verify SignIn Visibility");
        isVisible = homePage.basePage.openSignIn();
        test.info("Close SignIn");
        homePage.basePage.closeSignIn();
        Assert.assertTrue(isVisible);
    }
    @Test(priority = 5)
    public void sendContactMessage()
    {
        String testName = "Send Contact Message";
        String testDescription = "Verify proper alert message when sending Contact message";
        test = extent.createTest(testName,testDescription);

        String expectedAlertMsg = "Thanks for the message!!";
        test.info("Starting... create HomePage object");
        homePage = new HomePage(driver);
        test.info("Verify Contact visibility");
        homePage.basePage.openContact();
        test.info("Fill Contact form and verify alert message");
        String actualAlertMsg = homePage.basePage.fillContactForm();
        Assert.assertEquals(actualAlertMsg,expectedAlertMsg);
    }
    @Test(priority = 6)
    public void addItemToCart()
    {
        String testName = "Add Items to Cart";
        String testDescription = "Verify proper alert message when adding item to cart";
        test = extent.createTest(testName,testDescription);

        int fails = 0;

        test.info("Starting... create HomePage object");
        homePage = new HomePage(driver);
        test.info("Navigate to HomePage via BasePage");
        homePage = homePage.basePage.goToHome();
        test.info("Sorting results by Monitors");
        homePage.clickMonitors();
        test.info("Get items for Monitors");
        List<WebElement> Results = homePage.returnResults();
        test.info("How many items: "+Results.size());
        // loop iteration will be change according to results
        for (int i = 0; i <= Results.size()-1; i++)
        {
            int itemNum = i+1;
            test.info("Adding item no."+itemNum);
            test.info("Go to ItemPage");
            itemPage = homePage.selectItems(i,Results);
            test.info("Get Item name");
            String name = itemPage.getItemName();
            test.info("Get item price");
            int price = itemPage.getPrice();
            myItems.put(name,price);
            test.info("Add item to cart");
            itemPage.addItem();
            String expectedAlertMsg = "Product added";
            String actualAlertMsg = itemPage.getAlertMsg();
            test.info("Close alert");
            itemPage.closeAlert();
            // no need to perform on last iteration
            if (i != Results.size()-1)
            {
                test.info("Navigate to HomePage via BasePage");
                homePage = homePage.basePage.goToHome();
                test.info("Sorting results by Monitors");
                homePage.clickMonitors();
                //reload results
                Results = homePage.returnResults();
            }
            if(!actualAlertMsg.equals(expectedAlertMsg))
                fails++;
        }
        Assert.assertEquals(fails,0);
    }
    @Test(priority = 7, dependsOnMethods = "addItemToCart")
    public void compareItemsAddedToCart()
    {
        String testName = "Compare Items Added to Cart";
        String testDescription = "Verify items added are the same and showed in cart(by Name and price)";
        test = extent.createTest(testName,testDescription);

        Dictionary<String, Integer> cartItems = new Hashtable<>();
        test.info("Navigate to CartPage via BasePage");
        cartPage = itemPage.basePage.goToCart();
        test.info("Get how much items in cart");
        int itemsInCart = cartPage.getProductsInCart();
        test.info("How much items: "+itemsInCart);
        for (int i = 0; i < itemsInCart; i++)
        {
            int itemNum = i+1;
            test.info("Get item in Cart no."+itemNum);
            String title = cartPage.getTitle(i);
            test.info("Get item Name: "+ title);
            int price = cartPage.getPrice(i);
            test.info("Get item price: "+price);
            cartItems.put(title,price);
        }
        Assert.assertEquals(cartItems,myItems);
    }
    @Test(priority = 8, dependsOnMethods = "compareItemsAddedToCart")
    public void checkProductPriceInCart()
    {
        String testName = "Check Products Price in Cart";
        String testDescription = "Verify total price calculation vs items price in cart";
        test = extent.createTest(testName,testDescription);

        int sum = 0;
        test.info("Get how much items in cart");
        int itemsInCart = cartPage.getProductsInCart();
        for (int i = 0; i < itemsInCart; i++)
        {
            int itemNum = i+1;
            test.info("Get item price for item no."+itemNum);
            int price = cartPage.getPrice(i);
            sum = sum + price;
        }
        test.info("Items total price calculated: "+sum);
        test.info("Get total price");
        String totalPrice = cartPage.getTotalPrice();
        Assert.assertEquals(String.valueOf(sum),totalPrice);
    }
    @Test(priority = 9, dependsOnMethods = "checkProductPriceInCart")
    public void approveEmptyOrder()
    {
        String testName = "Make and Approve empty Order";
        String testDescription = "Verify proper alert message when making order with empty fields";
        test = extent.createTest(testName,testDescription);

        boolean isVisible;
        String expectedAlertMsg;
        String actualAlertMsg;

        test.info("Verify order popup visibility");
        isVisible = cartPage.openPlaceOrder();
        test.info("Close popup");
        cartPage.closePlaceOrder();
        if(isVisible)
        {
            test.info("Verify order window visibility");
            cartPage.openPlaceOrder();
            test.info("Apply purchase");
            cartPage.purchase();
            expectedAlertMsg = "Please fill out Name and Creditcard.";
            actualAlertMsg = cartPage.getAlertMsg();
            test.info("Close alert");
            cartPage.closeAlert();
            test.info("Verify order window");
            cartPage.closePlaceOrder();
            Assert.assertEquals(actualAlertMsg,expectedAlertMsg);
        }
        Assert.assertTrue(isVisible);
    }
    @Test(priority = 10, dependsOnMethods = "approveEmptyOrder")
    public void approveOrder()
    {
        String testName = "Make and Approve Order";
        String testDescription = "Verify purchase data vs costumer DB";
        test = extent.createTest(testName,testDescription);

        String totalPrice;

        test.info("Get total price from cart");
        totalPrice = cartPage.getTotalPrice();
        test.info("Open order form");
        cartPage.openPlaceOrder();
        test.info("Fill form with costumer details");
        Dictionary<String, String> customer = cartPage.fillForm();
        test.info("Apply purchase");
        cartPage.purchase();
        test.info("Fetch data");
        List<String> data = cartPage.getPurchaseData();

        String amount = data.get(0);
        String card = data.get(1);
        String name = data.get(2);
        String date = data.get(3);

        int totalFails , priceFail = 0, nameFail = 0, cardFail = 0, dateMonthFail = 0, dateYearFail = 0;

        amount = cartPage._trimPrice(amount);
        if(!amount.equals(totalPrice))
        {
            priceFail++;
            test.info(MarkupHelper.createLabel("Fail - Price is wrong and not match!!!", ExtentColor.RED));
        }

        name = cartPage._trimName(name);
        if(!name.equals(customer.get("name")))
        {
            nameFail++;
            test.info(MarkupHelper.createLabel("Fail - Name is wrong and not match!!!", ExtentColor.RED));
        }

        card = cartPage._trimCardNumber(card);
        if(!card.equals(customer.get("card")))
        {
            cardFail++;
            test.info(MarkupHelper.createLabel("Fail - Credit-card is wrong and not match!!!", ExtentColor.RED));
        }

        String dateMonth = cartPage._trimDateType(date,"month");
        if(!dateMonth.equals(customer.get("month")))
        {
            dateMonthFail++;
            test.info(MarkupHelper.createLabel("Fail - Month is wrong and not match!!!", ExtentColor.RED));
        }

        String dateYear = cartPage._trimDateType(date,"year");
        if(!dateYear.equals(customer.get("year")))
        {
            dateYearFail++;
            test.info(MarkupHelper.createLabel("Fail - Year is wrong and not match!!!", ExtentColor.RED));
        }
        totalFails = priceFail + nameFail + cardFail + dateMonthFail + dateYearFail;
        Assert.assertEquals(totalFails, 0);
    }
}
