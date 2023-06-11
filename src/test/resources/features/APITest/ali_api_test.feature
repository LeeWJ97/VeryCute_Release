@api
Feature: API Test Feature
  Scenario: API: ALI timestamp Test
    #Given I have the API endpoint "https://tbip.alicdn.com/api/queryip"  //wrong res content-type, cannot resolve JSON body
    Given I have the API endpoint "http://acs.m.taobao.com/gw/mtop.common.getTimestamp/*"
    When I send a GET request
    Then the response status code should be 200
    And the response body should have key "api" with value "mtop.common.getTimestamp"
    And the response body should have key "v" with value "*"
    And the response body should have key "ret[0]" with value "SUCCESS::接口调用成功"
    #And the response body should have key "data.t" with value "1686381765383"