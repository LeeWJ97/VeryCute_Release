package com.verycute.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;

public class DriverUtils {
    public static void tryClickElement(WebElement element) {
        tryClickElement(element, 10, 500);
    }

    public static void tryClickElement(WebElement element, int maxAttempts, long interval_ms) {
        int attempts = 0;
        while (attempts < maxAttempts) {
            try {
                element.click();
                return; // 点击成功，退出循环
            } catch (NoSuchElementException e) {
                attempts++;
                System.out.println(String.format("%d: Trying to click...", attempts));
                if (attempts >= maxAttempts){
                    e.addSuppressed(new Exception(String.format("Tried to click in %d times (interval: %d s), still failed",  maxAttempts, interval_ms)));
                    e.addSuppressed(new Exception(element.toString()));
                    throw e;
                }

                try {
                    Thread.sleep(interval_ms); // 等待一段时间后重试
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    // 使用示例
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com");

        String xpathStr = "//a";
        WebElement ele = driver.findElement(By.xpath(xpathStr));
        tryClickElement(ele, 5, 100);
        System.out.println("success");

        driver.quit(); // 关闭 WebDriver
    }
}
