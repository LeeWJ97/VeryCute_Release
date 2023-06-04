package com.verycute.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

class IsItFriday {
    static String isItFriday(String today) {
        if ("friday".equals(today.toLowerCase())){
            return "Yes";
        }
        else{
            return "Nope";
        }

    }
}

public class MyStepdefs {

    private String today;
    private String actualAnswer;

    @Given("today is {string}")
    public void today_is_day(String day) {
        today = day;
    }

    @When("I ask whether it's Friday yet")
    public void i_ask_whether_it_s_Friday_yet() {
        actualAnswer = IsItFriday.isItFriday(today);
    }

    @Then("I should be told {string}")
    public void i_should_be_told(String expectedAnswer) {
        Assert.assertEquals(expectedAnswer, actualAnswer);
    }
}
