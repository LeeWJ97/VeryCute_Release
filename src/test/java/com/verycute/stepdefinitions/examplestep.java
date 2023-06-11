package com.verycute.stepdefinitions;

import com.verycute.factory.DriverFactory;
import com.verycute.pages.ExamplePage;
import com.verycute.springconfig.annotation.LazyAutowired;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.time.Duration;


public class examplestep {
    @LazyAutowired
    private WebDriver driver;
    @LazyAutowired
    private ExamplePage examplepage;

    @Given("I am on the example page")
    public void I_visit_example() {
        examplepage.get();
    }

    @When("I click the example link")
    public void Iclicktheexamplelink() {
        examplepage.clickLink();
    }

}


