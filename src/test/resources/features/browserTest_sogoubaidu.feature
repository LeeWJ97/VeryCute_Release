@POTest @debug
Feature: sogou baidu Search Test

  Scenario Outline:  sogou baidu
  Given sogou: I am on the sogou search page
  When sogou: I search for "搜狗!"
  Then sogou: the page title should start with "<ExpectSogou>"

  Given PO: I am on the Baidu search page
  When PO: I search for "百度!"
  Then PO: the page title should start with "<ExpectBaidu>"

    Examples:
      | ExpectSogou |   ExpectBaidu |  
      | 搜狗          |   百度          |  
      | 搜           |   百           |  