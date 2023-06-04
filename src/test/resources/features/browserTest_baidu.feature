@POTest
Feature: Baidu Test

  Scenario Outline:  Finding some 百度
  Given PO: I am on the Baidu search page
  When PO: I search for "百度!"
  Then PO: the page title should start with "<Expect>"
    Examples:
      | Expect |
      | 百度     |
      | 百      |