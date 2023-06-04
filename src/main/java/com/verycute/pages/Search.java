package com.verycute.pages;

import com.verycute.factory.DriverFactory;
import com.verycute.springconfig.annotation.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.Assert;

import java.time.Duration;


@PageObject
public class Search extends LoadableComponent<Search> {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//*[@id='kw']")
    public WebElement searchInput;

    @FindBy(xpath = "//*[@id='su']")
    public WebElement searchBtn;


    public Search() {
        this.driver =  DriverFactory.getDriver();;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        PageFactory.initElements(driver, this);
    }

    public void setInputText(String text){
        wait.until(ExpectedConditions.visibilityOf(searchInput)).clear();
        searchInput.sendKeys(text);
    }

    public void sumbitSearch(){
        //wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(searchInput)).submit();
    }

    public String getTitle(final String titleStartsWith) {
        try {
            wait.until((ExpectedCondition<Boolean>) d -> d.getTitle().toLowerCase().startsWith(titleStartsWith));
        }
        finally {
            return driver.getTitle();
        }
    }

    @Override
    public void load() {
        driver.get("https://baidu.com");
    }

    @Override
    public void isLoaded() {
        String url = driver.getCurrentUrl();
        Assert.isTrue(url.contains("baidu.com"), "Not on the issue entry page: " + url);
    }

    @Override
    public Search get() {
        try {
            this.isLoaded();
            return this;
        } catch (Exception var2) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.load();
            this.isLoaded();
            return this;
        }
    }

}