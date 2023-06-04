package com.verycute.hooks;

import com.verycute.factory.DriverFactory;
import com.verycute.springconfig.annotation.LazyAutowired;
import io.cucumber.java.*;
import io.cucumber.spring.CucumberContextConfiguration;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.verycute.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootTest
public class Hooks {
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    @LazyAutowired
    private ApplicationContext applicationContext;

    private WebDriver driver;

    private static final String regex = "[\\\\/:*?\"<>|]";
    private static String screenshotPath;



    @BeforeAll(order = 100)
    public static void setPath() {
        logger.info("Start test...");
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

    @BeforeStep(order = 200)
    public void beforestep(Scenario scenario) throws IOException {
        //scenario.log("aaaaaaaaaaaaaaaaaaaaaa");
        scenario.getSourceTagNames();
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

    @Before(order = 10)
    public static void before(Scenario scenario) {
        logger.info("!-----Start test： " + scenario.getName() + "-----");
    }
    
    @After(order = 10)
    public static void after(Scenario scenario)  {
        logger.info("!-----Finish test： " + scenario.getName() + "-----");
    }
}
