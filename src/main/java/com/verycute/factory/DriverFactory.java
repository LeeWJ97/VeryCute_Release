package com.verycute.factory;

import com.alibaba.fastjson.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.verycute.utils.FastJsonUtils;
import com.verycute.utils.IOUtils;

import java.io.IOException;
import java.util.Map;

public class DriverFactory {
    public WebDriver driver;
    public static final String configPath  = "target/test-classes/config.json";
    public static JSONObject configJsonObject;
    public static ThreadLocal<WebDriver> threadLocalWebDriver = new ThreadLocal<>();  //For Parallel execution

    public WebDriver initDriver() {
        readConfig();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        JSONObject driverArgs = configJsonObject.getJSONObject("driverArgs");


        for (Map.Entry<String,Object> m:
             driverArgs.entrySet()) {
            if ((Boolean) m.getValue() == true){
                options.addArguments(m.getKey());
            }
        }


        driver = new ChromeDriver(options);
        threadLocalWebDriver.set(driver);
        return driver;
    }

    private static void readConfig() {
        try {
            String config = IOUtils.readTextFile(configPath, "utf-8");
            configJsonObject = FastJsonUtils.toJObject(config);
        } catch (IOException e) {
            System.out.println("failed to get config: " + configPath);
            throw new RuntimeException(e);
        }
    }

    public static synchronized WebDriver getDriver() {
        return threadLocalWebDriver.get(); //For Parallel execution
    }
}
