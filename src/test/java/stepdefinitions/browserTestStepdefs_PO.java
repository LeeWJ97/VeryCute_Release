package stepdefinitions;

import factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.var;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.Search;


public class browserTestStepdefs_PO {
    private WebDriver driver = DriverFactory.getDriver();
    private Search search;

    @Given("PO: I am on the Baidu search page")
    public void I_visit_baidu() {
        search = new Search(driver);

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


