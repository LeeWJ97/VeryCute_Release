package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
    public WebDriver driver;

    public static ThreadLocal<WebDriver> threadLocalWebDriver = new ThreadLocal<>();  //For Parallel execution

    public WebDriver initDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1366,768");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        threadLocalWebDriver.set(driver);
        return driver;
    }

    public static synchronized WebDriver getDriver() {
        return threadLocalWebDriver.get(); //For Parallel execution
    }
}
