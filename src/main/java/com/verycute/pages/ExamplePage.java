package com.verycute.pages;

import com.verycute.factory.DriverFactory;
import com.verycute.springconfig.annotation.LazyAutowired;
import com.verycute.springconfig.annotation.PageObject;
import com.verycute.utils.DriverUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.time.Duration;


@PageObject
public class ExamplePage<T> {
    private WebDriver driver;
    @LazyAutowired
    private WebDriverWait wait;

    @FindBy(xpath = "//a")
    public WebElement link;

    @Autowired
    public ExamplePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickLink(){
        DriverUtils.tryClickElement(link);
    }


    public void load() {
        driver.get("https://www.example.com");
    }


    public void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        Assert.isTrue(url.contains("https://www.example.com"), "Not on the issue entry page: " + url);
    }


    public T get() {
        try {
            this.load();
            this.isLoaded();
            return (T) this;
        } catch (Exception var2) {
            System.out.println("try to reload...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.load();
            this.isLoaded();
            return (T) this;
        }
    }

}