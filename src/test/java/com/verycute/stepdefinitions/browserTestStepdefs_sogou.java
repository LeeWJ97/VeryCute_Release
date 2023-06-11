package com.verycute.stepdefinitions;

import com.verycute.factory.DriverFactory;
import com.verycute.pages.SogouSearch;
import com.verycute.springconfig.annotation.LazyAutowired;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;


public class browserTestStepdefs_sogou {
    @LazyAutowired
    private WebDriver driver;
    @LazyAutowired   //need to lazyautowired, beacause webdriver init in hook @before, not in the starter
    private SogouSearch search;

    @Given("sogou: I am on the sogou search page")
    public void I_visit_sogou() {
        search.load();

    }

    @When("sogou: I search for {string}")
    public void search_for(String query) {
        search.setInputText(query);
        search.sumbitSearch();
    }

    @Then("sogou: the page title should start with {string}")
    public void checkTitle(final String titleStartsWith) {
        var actualTitleStartsWith = search.getTitle(titleStartsWith);
        Assert.assertTrue(String.format("Expect: %s, Actual: %s", titleStartsWith, actualTitleStartsWith), actualTitleStartsWith.toLowerCase().contains(titleStartsWith.toLowerCase()));
    }

}


