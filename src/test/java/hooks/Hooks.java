package hooks;

import factory.DriverFactory;
import io.cucumber.java.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.DateTimeUtils;

import java.io.File;
import java.io.IOException;

public class Hooks {
    private WebDriver driver;

    private static final String regex = "[\\\\/:*?\"<>|]";
    private static String screenshotPath;



    @BeforeAll(order = 100)
    public static void setPath() {
        String today = DateTimeUtils.formatDateText(DateTimeUtils.timestampToDate(DateTimeUtils.getCurrentTimestamp()));
        screenshotPath = "./screenshots/" + today.replaceAll(regex, "_") + "/";
    }

    @Before(order = 200)
    public void openBrowser() {
        DriverFactory driverFactory = new DriverFactory();
        driver = driverFactory.initDriver();
    }
    @After(order = 100)
    public void closeBrowser() {
        driver.quit();
    }

    @AfterStep(order = 200)
    public void takeScreenshotAndTrace(Scenario scenario) throws IOException {
        if (scenario.isFailed()) {
            String regex = "[\\\\/:*?\"<>|]";
            String screenshotName = scenario.getName().replaceAll(regex, "_");

            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            byte[] scrFileByte = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);

            String nowdate = DateTimeUtils.formatDateText(DateTimeUtils.timestampToDate(DateTimeUtils.getCurrentTimestamp())).replaceAll(regex, "_");
            File outputFile = new File(screenshotPath + screenshotName + "_" + nowdate + ".png");
            FileUtils.copyFile(scrFile, outputFile);
            System.out.println(outputFile);

            //scenario.attach(scrFileByte, "image/png", screenshotName);  //Attach screenshot to report if scenario fails
            scenario.attach(scrFileByte, "image/png", "Failed Screenshot");
        }
    }


    @AfterAll
    public static void afterall() throws IOException {
//        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("target/HTMLReport/ToolsQA.html");
//        ExtentReports extentReports = new ExtentReports();
//        extentReports.attachReporter(htmlReporter);
//        // 将报告保存到指定路径
//        extentReports.flush();
        System.out.println("finish test");
    }
}
