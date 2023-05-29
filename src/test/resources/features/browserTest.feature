@failtest
Feature: Browser Test

Scenario Outline: Finding some fail 百度
  Given I am on the Baidu search page
  When I search for "百度<Expect>!"
  Then the page title should start with "<Expect>"
  Examples:
    | Expect |
    | 1      |
    | 2      |
    | 3      |
    | 4      |
    | 5      |