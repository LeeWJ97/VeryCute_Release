@POTest
Feature: sogou Search Test

  Scenario Outline:  Finding some sogou
  Given sogou: I am on the sogou search page
  When sogou: I search for "搜狗!"
  Then sogou: the page title should start with "<Expect>"
    Examples:
      | Expect |
      | 搜狗  |