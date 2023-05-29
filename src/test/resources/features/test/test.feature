@drytest
Feature: Is it Friday yet?
  Everybody wants to know when it's Friday

  Scenario Outline: Sunday isn't Friday
    Given today is "<day>"
    When I ask whether it's Friday yet
    Then I should be told "<told>"
    Examples:
      | day    | told |
      | Sunday | Nope |
      | Friday | Yes  |
