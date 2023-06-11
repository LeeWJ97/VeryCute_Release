Feature: MIX test

  @mixtest
  Scenario Outline: api mix UI
    Given I have the API endpoint "http://acs.m.taobao.com/gw/mtop.common.getTimestamp/*"
    When I send a GET request
    Given I am on the example page
    When I click the example link
    Then the response status code should be 200
    And the response body should have key "api" with value "mtop.common.getTimestamp"
    And the response body should have key "v" with value "*"
    Given sogou: I am on the sogou search page
    When sogou: I search for "搜狗!"
    Then the response body should have key "ret[0]" with value "SUCCESS::接口调用成功"
    Then sogou: the page title should start with "<Expect>"
    Given I have the API endpoint "https://catfront.dianping.com/api/speed"
    When I send a GET request
    Then the response status code should be 200
    And the response body should have key "message" with value "success"
    And the response body should have key "result" with value 0

    @failtest
    Examples:
      | Expect |
      | 1搜狗    |

    Examples:
      | Expect |
      | 搜狗     |