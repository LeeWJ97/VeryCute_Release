package testrunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        // Rerun failed tests from rerun.txt file
        //features = {"@target/rerun.txt"},
        features = {"@rerun.txt"},
        //dryRun = true,
        //monochrome = true,
        glue = {"stepdefinitions", "hooks"},
        plugin = {"pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "customplugin.MailExtension",
                "rerun:rerun.txt"
        }
)


public class ReRunner {
}
