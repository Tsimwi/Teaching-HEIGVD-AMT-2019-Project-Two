Feature: Unicorns related actions

  Background:
    Given there is a unicorns API
    And I have a token

  Scenario: Create a Unicorn
    Given I have a unicorn payload with attribute name Rainbow
    When I POST it to the /unicorns endpoint
    Then I receive a 201 status code
    When I GET /unicorns/Rainbow endpoint
    Then I receive a 200 status code
    And I receive a Unicorn


   Scenario: Delete a unicorn
     Given I have a unicorn payload with attribute name Fluffy
     And I POST it to the /unicorns endpoint
     And I receive a 201 status code
     When I DELETE it to the /unicorns/Fluffy endpoint
     Then I receive a 200 status code
     When I GET /unicorns/Fluffy endpoint
     Then I receive a 404 status code
     