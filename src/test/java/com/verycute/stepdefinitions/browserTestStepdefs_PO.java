package com.verycute.stepdefinitions;

import com.verycute.factory.DriverFactory;
import com.verycute.springconfig.annotation.LazyAutowired;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.verycute.pages.Search;
import com.verycute.CucumberSpringApplication;


public class browserTestStepdefs_PO {
    //@Autowired
    private WebDriver driver = DriverFactory.getDriver();
    @LazyAutowired   //need to lazyautowired, beacause webdriver init in hook @before, not in the starter
    private Search search;

    @Given("PO: I am on the Baidu search page")
    public void I_visit_Baidu() {
        //search = new Search(driver);

        //check load() and isload()
        search.get();

    }

    @When("PO: I search for {string}")
    public void search_for(String query) {
        search.setInputText(query);
        search.sumbitSearch();
    }

    @Then("PO: the page title should start with {string}")
    public void checkTitle(final String titleStartsWith) {
        var actualTitleStartsWith = search.getTitle(titleStartsWith);
        Assert.assertTrue(String.format("Expect: %s, Actual: %s", titleStartsWith, actualTitleStartsWith), actualTitleStartsWith.toLowerCase().contains(titleStartsWith.toLowerCase()));
    }

}


