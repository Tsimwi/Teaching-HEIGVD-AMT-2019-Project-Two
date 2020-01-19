Feature: Magicorn related actions

  Background:
    Given there is a unicorns API
    And I have a token

  Scenario: Add a magicorn
    Given I have a unicorn payload
    And I POST it to the /unicorns endpoint
    And I receive a 201 status code
    And I have a magic payload
    And I POST it to the /magics endpoint
    And I receive a 201 status code
    And I have a magicorn payload with operation add with error false
    When I POST it to the /magicorn endpoint
    Then I receive a 201 status code
    And I POST it to the /magicorn endpoint
    Then I receive a 400 status code

  Scenario: Remove a magicorn
    Given I have a unicorn payload
    And I POST it to the /unicorns endpoint
    And I receive a 201 status code
    And I have a magic payload
    And I POST it to the /magics endpoint
    And I receive a 201 status code
    And I have a magicorn payload with operation add with error false
    When I POST it to the /magicorn endpoint
    Then I receive a 201 status code
    And I have a magicorn payload with operation remove with error false
    When I POST it to the /magicorn endpoint
    Then I receive a 200 status code
    When I POST it to the /magicorn endpoint
    Then I receive a 400 status code

  Scenario: Add a magicorn with a malformed payload
    Given I have a unicorn payload
    And I POST it to the /unicorns endpoint
    And I receive a 201 status code
    And I have a magic payload
    And I POST it to the /magics endpoint
    And I receive a 201 status code
    And I have a magicorn payload with operation add with error true
    When I POST it to the /magicorn endpoint
    Then I receive a 400 status code

   Scenario: Add a magicorn with a non-existant object
     Given I have a unicorn payload
     And I POST it to the /unicorns endpoint
     And I receive a 201 status code
     And I have a magic payload
     And I have a magicorn payload with operation add with error false
     When I POST it to the /magicorn endpoint
     Then I receive a 404 status code

   Scenario: Add a magicorn without being the owner of the objects
     Given I have a unicorn payload
     And I POST it to the /unicorns endpoint
     And I receive a 201 status code
     And I have a magic payload
     And I POST it to the /magics endpoint
     And I receive a 201 status code
     And I change the jwt to have a new one of an other user
     And I have a magicorn payload with operation add with error false
     When I POST it to the /magicorn endpoint
     Then I receive a 403 status code

   Scenario: Make an unknown operation on the /magicorn endpoint
     Given I have a unicorn payload
     And I POST it to the /unicorns endpoint
     And I receive a 201 status code
     And I have a magic payload
     And I POST it to the /magics endpoint
     And I receive a 201 status code
     And I have a magicorn payload with operation cake with error false
     When I POST it to the /magicorn endpoint
     Then I receive a 400 status code