Feature: Unicorns related actions

  Background:
    Given there is a unicorns API
    And I have a token

  Scenario: Create a Unicorn
    Given I have a unicorn payload
    When I POST it to the /unicorns endpoint
    Then I receive a 201 status code
    When I GET /unicorns/<name> endpoint with fullviwes false
    Then I receive a 200 status code
    And I receive an Unicorns with fullviews false


  Scenario: Delete a unicorn
    Given I have a unicorn payload
    And I POST it to the /unicorns endpoint
    And I receive a 201 status code
    When I DELETE it to the /unicorns/<name> endpoint
    Then I receive a 200 status code
    When I GET /unicorns/<name> endpoint with fullviwes false
    Then I receive a 404 status code

  Scenario: Get all my unicorns
    When I GET /unicorns endpoint
    Then I receive a 200 status code
    And I receive an array of Unicorns


  Scenario: Get info about a unicorn
    Given I have a unicorn payload
    And I POST it to the /unicorns endpoint
    And I receive a 201 status code
    When I GET /unicorns/<name> endpoint with fullviwes true
    Then I receive a 200 status code
    And I receive an Unicorns with fullviews true
     