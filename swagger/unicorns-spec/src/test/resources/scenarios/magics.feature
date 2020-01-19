Feature: Magics related actions

  Background:
    Given there is a unicorns API
    And I have a token

#    Test endpoints behaviors with good data
  Scenario: Get all my magics
    When I GET /magics endpoint with page number 2 and number per page 2
    Then I receive a 200 status code
    And I receive an array of Magics

  Scenario: Add a magic
    Given I have a magic payload
    When I POST it to the /magics endpoint
    Then I receive a 201 status code
    When I GET /magics/<name> endpoint with fullviwes false
    Then I receive a 200 status code
    And I receive an Magics with fullviews false

  Scenario: Get info about a magic
    Given I have a magic payload
    And I POST it to the /magics endpoint
    And I receive a 201 status code
    When I GET /magics/<name> endpoint with fullviwes true
    Then I receive a 200 status code
    And I receive an Magics with fullviews true

  Scenario: Update a magic
    Given I have a magic payload
    And I POST it to the /magics endpoint
    And I receive a 201 status code
    And I update my magic payload
    When I PUT it to the /magic/<name> endpoint
    Then I receive a 200 status code
    And I GET /magics/<name> endpoint with fullviwes false
    And I receive an Magics that match the update with fullviews false

  Scenario: Delete a magic
    Given I have a magic payload
    And I POST it to the /magics endpoint
    And I receive a 201 status code
    When I DELETE it to the /magics/<name> endpoint
    Then I receive a 200 status code
    When I GET /magics/<name> endpoint with fullviwes false
    Then I receive a 404 status code

#   This test tests the filter, this is why we perform this test on a single endpoint
  Scenario: Get magics with a jwt malformed
    Given I have a malformed token
    When I GET /magics endpoint with page number 100 and number per page 20
    Then I receive a 401 status code

  Scenario: Get magics with a invalid token
    Given I have an invalid token
    When I GET /magics endpoint with page number 100 and number per page 20
    Then I receive a 401 status code

#    Test endpoint with wrong data
  Scenario: Add a magic with a malformed payload
    Given I have a malformed magic payload
    When I POST it to the /magics endpoint
    Then I receive a 400 status code

  Scenario: Add a magic that already exist
    Given I GET /magics endpoint with page number 1 and number per page 10
    And I take the first magic in the list
    When I POST it to the /magics endpoint
    Then I receive a 409 status code

  Scenario: Get a magic that doesn't exist
    Given I have an magic name
    When I GET /magics/<name> endpoint with fullviwes false
    Then I receive a 404 status code

  Scenario: Get a magic of an other user
    Given I have a magic payload
    And I POST it to the /magics endpoint
    Then I change the jwt to have a new one of an other user
    When I GET /magics/<name> endpoint with fullviwes false
    Then I receive a 403 status code

  Scenario: Update an magic with a malformed payload
    Given I have a malformed magic payload
    When I PUT it to the /magic/<name> endpoint
    Then I receive a 400 status code

  Scenario: Update an magic that doesn't exist
    Given I have a magic payload
    When I PUT it to the /magic/<name> endpoint
    Then I receive a 404 status code

  Scenario: Update an magic of an other user
    Given I have a magic payload
    And I POST it to the /magics endpoint
    Then I change the jwt to have a new one of an other user
    When I PUT it to the /magic/<name> endpoint
    Then I receive a 403 status code

  Scenario: Delete an magic that doesn't exist
    Given I have an magic name
    When I DELETE it to the /magics/<name> endpoint
    Then I receive a 404 status code

  Scenario: Delete an magic of an other user
    Given I have a magic payload
    And I POST it to the /magics endpoint
    Then I change the jwt to have a new one of an other user
    When I DELETE it to the /magics/<name> endpoint
    Then I receive a 403 status code
