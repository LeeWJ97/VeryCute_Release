@app
Feature: App Chrome2 Test

Scenario: App Chrome2 Test
  Given I am on the Chrome app
  When I click the Chinese accept
  Then The Chinese Google logo should show
  #When I click the without account button
  #And I click the negative button
  #And I click the change to sogou button
  #Then The Google logo should show