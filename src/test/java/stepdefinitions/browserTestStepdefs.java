package stepdefinitions;

import factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class browserTestStepdefs {

    private WebDriver driver = DriverFactory.getDriver();

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

        new WebDriverWait(driver, Duration.ofSeconds(1)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith(titleStartsWith);
            }
        });
    }


}


