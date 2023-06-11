package com.verycute.factory;

import com.alibaba.fastjson.JSONObject;
import com.verycute.springconfig.annotation.Factory;
import jakarta.annotation.PostConstruct;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.verycute.utils.FastJsonUtils;
import com.verycute.utils.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Factory
public class DriverFactory {
    public WebDriver driver;
    public static String configPath = null;
    public static JSONObject configJsonObject = null;
    public static ThreadLocal<WebDriver> threadLocalWebDriver = new ThreadLocal<>();  //For Parallel execution

    @Value("${driver.config.filename:target/test-classes/driver-config.json}")
    private void setconfigPath(String configPath) {
        if (DriverFactory.configPath == null){
            DriverFactory.configPath = configPath;
        }
    }

    public WebDriver initDriver() {
        readConfig();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});  // hide "Chrome is being controlled by automated test software."
        options.setAcceptInsecureCerts(true); // ignore insecure SSL certs
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
        if (configJsonObject == null){
            try {
                String config = IOUtils.readTextFile(configPath, "utf-8");
                configJsonObject = FastJsonUtils.toJObject(config);
            } catch (IOException e) {
                System.out.println("failed to get config: " + configPath);
                throw new RuntimeException(e);
            }
        }
    }

    public static synchronized WebDriver getDriver() {
        if (threadLocalWebDriver.get() != null){
            return threadLocalWebDriver.get(); //For Parallel execution
        }
        else{
            DriverFactory driverFactory = new DriverFactory();
            return driverFactory.initDriver();
        }
    }
    public static synchronized void removeDriver() {
        threadLocalWebDriver.get().quit();
        threadLocalWebDriver.remove();
    }
}
