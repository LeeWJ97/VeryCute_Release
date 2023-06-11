package com.verycute.stepdefinitions;
import com.verycute.hooks.Hooks;
import com.verycute.springconfig.annotation.LazyAutowired;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.restassured.RestAssured;

import java.util.Map;

import static org.hamcrest.Matchers.*;

public class ApiTestSteps {
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
    @LazyAutowired
    private RestAssured ra;
    private RequestSpecification rs;
    private Response response;

    @Given("I have the API endpoint {string}")
    public void setAPIEndpoint(String endpoint) {
         rs = ra.given().baseUri(endpoint);
         // set a default UA
         rs = rs.given().headers("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Mobile Safari/537.36 Edg/112.0.1722.64");
         //rs.relaxedHTTPSValidation();
         //rs.proxy("127.0.0.1", 8080);
         //TODO: Move to config file
    }

    @Given("I have set the following headers:")
    public void setHeaders(Map<String, String> headers) {
        rs = rs.given().headers(headers);
    }

    @Given("I have set the JSON body: {string}")
    public void setBody(String body) {
        rs = rs.given().body(body).contentType(ContentType.JSON);
    }

    @When("I send a GET request")
    public void sendGETRequest() {
        response = rs.get();
        logResStr();
    }

    @When("I send a POST request")
    public void sendPostRequest() {
        response = rs.post();
        logResStr();
    }

    @Then("the response status code should be {int}")
    public void verifyStatusCode(int statusCode) {
        try {
            response.then().statusCode(statusCode);
        } catch (AssertionError e) {
            logger.error(e.getMessage());
            throw new AssertionError(e);
        }
    }

    @Then("the response body should have key {string} with value {string}")
    public void verifyResponseKeyValue(String key, String value) {
        try {
            response.then().assertThat().body(key, equalTo(value));
        } catch (AssertionError e) {
            logger.error(e.getMessage());
            throw new AssertionError(e);
        }
    }

    @Then("the response body should have key {string} with value {int}")  //content-type in res must be application/json when resolve JSON body, if is plain/html, it will resolve as XML
    public void verifyResponseKeyValue(String key, int value) {
        try {
            response.then().assertThat().body(key, equalTo(value));
        } catch (AssertionError e) {
            logger.error(e.getMessage());
            throw new AssertionError(e);
        }
    }

    public void logResStr(){
        logger.info("Res Status line: " + response.getStatusLine());
        logger.info("Res Headers: \n" + response.getHeaders().toString());
        logger.info("Res Body: " + response.getBody().asString());
    }
}
