@api
Feature: API Test Feature
  Scenario: API: MAOYAN Test
    #Given I have the API endpoint "https://tbip.alicdn.com/api/queryip"
    Given I have the API endpoint "https://catfront.dianping.com/api/speed"
    When I send a GET request
    Then the response status code should be 200
    And the response body should have key "message" with value "success"
    And the response body should have key "result" with value 0