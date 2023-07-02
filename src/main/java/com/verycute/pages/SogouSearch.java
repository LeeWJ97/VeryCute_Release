package com.verycute.pages;

import com.verycute.factory.DriverFactory;
import com.verycute.springconfig.annotation.LazyAutowired;
import com.verycute.springconfig.annotation.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;

import java.time.Duration;


@PageObject
public class SogouSearch extends LoadableComponent<SogouSearch> {

    private WebDriver driver;

    @Qualifier("webDriverWait")
    @LazyAutowired
    private WebDriverWait wait;

    @FindBy(xpath = "//*[@name='query']")
    public WebElement searchInput;

    @FindBy(xpath = "//*[@id='stb']")
    public WebElement searchBtn;

    @Autowired
    public SogouSearch(WebDriver driver) {
        this.driver =  driver;
        //wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        PageFactory.initElements(driver, this);
    }

    public void setInputText(String text){
        wait.until(ExpectedConditions.elementToBeClickable(searchInput)).clear();
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
        driver.get("https://www.sogou.com");
    }

    @Override
    public void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        Assert.isTrue(url.contains("https://www.sogou.com"), "Not on the issue entry page: " + url);
    }

}