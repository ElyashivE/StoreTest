import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class ExtantManager
{
    public static ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest test;
    public static WebDriver driver;
    public static void initializeReport()
    {
        htmlReporter =  new ExtentHtmlReporter(System.getProperty("user.dir")+"/Reports/extentReport.html");
        htmlReporter.config().setDocumentTitle("Automation Report");
        htmlReporter.config().setReportName("Report -> demoblaze");
        htmlReporter.config().setTheme(Theme.DARK);
        extent = new ExtentReports();

        extent.attachReporter(htmlReporter);
    }
    public static String CaptureScreenshot(WebDriver driver) throws IOException
    {
        String FileSeparator = System.getProperty("file.separator");
        String Extent_report_path = "."+FileSeparator+"Reports";
        String ScreenshotPath = Extent_report_path+FileSeparator+"screenshots";

        File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String screenshotName = "screenshot"+Math.random()+".png";
        String screenshotpath = ScreenshotPath+FileSeparator+screenshotName;

        FileUtils.copyFile(src,new File(screenshotpath));
        return "."+FileSeparator+"screenshots"+FileSeparator+screenshotName;
    }
    public static boolean printResultToExtentResult(ExtentTest test, String actualResult, String expectedResult, String testDescription)
    {
        boolean isEqual = actualResult.equals(expectedResult);
        if(isEqual)
            test.pass(MarkupHelper.createLabel("PASSED - "+testDescription, ExtentColor.GREEN));
        else
        {
            try
            {
                test.fail(MarkupHelper.createLabel("FAILED!!! - "+testDescription,ExtentColor.RED));
                //create screenshot file and retrieve the file path.
                String x = CaptureScreenshot(driver);
                //add the screenshot file to the extent report.
                test.info("See Screen Below:<br>", MediaEntityBuilder.createScreenCaptureFromPath(x).build());
            }
            catch (Exception ex)
            {
                test.fail(MarkupHelper.createLabel("Unexpected issue when try to get screenshot",ExtentColor.RED));
            }
        }
        test.info("Actual Result: "+actualResult);
        test.info("Expected Result: "+expectedResult);
        return isEqual;
    }
}