@api
Feature: API Test Feature
  Scenario: API: POST Test
    Given I have the API endpoint "https://reqres.in/api/users"
    Given I have set the following headers:
      | User-Agent | Tester |
      | TestFlag   | 1      |
    Given I have set the JSON body: "{\"name\":\"testname\",\"job\":\"leader\"}"
    When I send a POST request
    Then the response status code should be 201
    And the response body should have key "name" with value "testname"
    And the response body should have key "job" with value "leader"
