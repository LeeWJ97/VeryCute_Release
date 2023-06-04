package com.verycute.testrunner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        // Rerun failed tests from rerun.txt file
        features = {"src/test/resources/features/"},
        //dryRun = true,
        //monochrome = true,
        glue = {"com/verycute/stepdefinitions", "com/verycute/hooks", "com/verycute/springconfig"},
        plugin = {"pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "com.verycute.customplugin.MailExtension",
                "rerun:rerun.txt"  // Save Failed test scenarios in rerun.txt file
        }
)

public class FullTestRunner {
}
