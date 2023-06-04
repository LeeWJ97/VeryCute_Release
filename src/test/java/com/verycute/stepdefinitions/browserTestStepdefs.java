package com.verycute.stepdefinitions;

import com.verycute.factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;


public class browserTestStepdefs {

    private WebDriver driver = DriverFactory.getDriver();
//    @Autowired
//    private WebDriver driver;

    @Given("I am on the Baidu search page")
    public void I_visit_baidu() {
        driver.get("https://www.baidu.com");

    }

    @When("I search for {string}")
    public void search_for(String query) {
        WebElement element = driver.findElement(By.id("kw"));
        // Enter something to search for
        element.sendKeys(query);
        // Now submit the form. WebDriver will find the form for us from the element
        element.submit();
    }

    @Then("the page title should start with {string}")
    public void checkTitle(final String titleStartsWith) {
        // Baidu's search is rendered dynamically with JavaScript
        // Wait for the page to load timeout after ten seconds

        String[] tempTitle = {""};
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    tempTitle[0] = d.getTitle();
                    return d.getTitle().toLowerCase().startsWith(titleStartsWith);
                }
            });
        } catch (Exception e) {
            e.addSuppressed(new Exception("Actual Title not expected:" + tempTitle[0]));
            throw new RuntimeException(e);
        }
    }


}


