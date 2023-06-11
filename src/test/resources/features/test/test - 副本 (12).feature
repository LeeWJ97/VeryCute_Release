@drytest
Feature: Is it Friday yet?12
  Everybody wants to know when it's 1Friday

  Scenario Outline: Sunday isn't Friday12
    Given today is "<day>"
    When I ask whether it's Friday yet
    Then I should be told "<told>"
    Examples:
      | day    | told |
      | Sunday | Nope |
      | Friday | Yes  |
