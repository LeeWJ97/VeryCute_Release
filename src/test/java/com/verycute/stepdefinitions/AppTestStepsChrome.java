package com.verycute.stepdefinitions;

import com.verycute.hooks.Hooks;
import com.verycute.pages.app.Chrome.Chrome_MainPage;
import com.verycute.springconfig.annotation.LazyAutowired;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import java.net.URL;
import java.time.Duration;

public class AppTestStepsChrome {
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    @LazyAutowired
    @Qualifier("appDriver")
    private AppiumDriver driver;
    @LazyAutowired
    @Qualifier("appDriverWait")
    private WebDriverWait wait;
    @LazyAutowired
    private Chrome_MainPage mainpage;

    @Given("I am on the Chrome app")
    public void i_am_on_the_chrome_app() {
        URL remoteAddress = driver.getRemoteAddress();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info(remoteAddress.toString());
    }
    @When("I click the without account button")
    public void i_click_the_without_account_button() {
        mainpage.clickdismissBtn();
    }
    @When("I click the negative button")
    public void i_click_the_negative_button() {
        mainpage.clicknegativeBtn();
    }
    @When("I click the change to sogou button")
    public void i_click_the_change_to_sogou_button() {
        mainpage.clicksogouBtn();
    }
    @Then("The Google logo should show")
    public void the_google_logo_should_show() {
        mainpage.getGoogleLogo();
    }

    @Then("The Chinese Google logo should show")
    public void the_Chinesegoogle_logo_should_show() {
        mainpage.getChineseGoogleLogo();
    }


    @When("I click the Chinese accept")
    public void iClickTheChineseAccept() {
        mainpage.clickChineseAcceptBtn();
    }
}
