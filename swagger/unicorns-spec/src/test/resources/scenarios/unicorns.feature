Feature: Unicorns related actions

  Background:
    Given there is a unicorns API

  Scenario: Create a Unicorn
    Given I have a unicorn payload and a JWT token
    When I POST it to the /unicorns endpoint
    Then I receive a 201 status code
    When I GET /unicorns/Rainbow endpoint
    Then I receive a 200 status code
    And I receive a SimpleUnicorn