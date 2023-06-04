Feature: Browser Test_Script Type

  @failtest
Scenario Outline: Finding some fail 百度
  Given I am on the Baidu search page
  When I search for "百度<Expect>!"
  Then the page title should start with "<Expect2>"
  Examples:
    | Expect | Expect2 |
    | 1      | 1       |
    | 2      | 百度2     |