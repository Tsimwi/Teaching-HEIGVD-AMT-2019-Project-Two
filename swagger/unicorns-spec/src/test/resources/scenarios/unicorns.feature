Feature: Unicorns related actions

  Background:
    Given there is a unicorns API
    And I have a token

#    Test endpoints behaviors with good data
  Scenario: Get all my unicorns
    When I GET /unicorns endpoint with page number 100 and number per page 20
    Then I receive a 200 status code
    And I receive an array of Unicorns

  Scenario: Add an Unicorn
    Given I have a unicorn payload
    When I POST it to the /unicorns endpoint
    Then I receive a 201 status code
    When I GET /unicorns/<name> endpoint with fullviwes false
    Then I receive a 200 status code
    And I receive an Unicorns with fullviews false

  Scenario: Get info about a unicorn
    Given I have a unicorn payload
    And I POST it to the /unicorns endpoint
    And I receive a 201 status code
    When I GET /unicorns/<name> endpoint with fullviwes true
    Then I receive a 200 status code
    And I receive an Unicorns with fullviews true

  Scenario: Update a unicorn
    Given I have a unicorn payload
    And I POST it to the /unicorns endpoint
    And I receive a 201 status code
    And I update my unicorn payload
    When I PUT it to the /unicorn/<name> endpoint
    Then I receive a 200 status code
    And I GET /unicorns/<name> endpoint with fullviwes false
    And I receive an Unicorns that match the update with fullviews false

  Scenario: Delete a unicorn
    Given I have a unicorn payload
    And I POST it to the /unicorns endpoint
    And I receive a 201 status code
    When I DELETE it to the /unicorns/<name> endpoint
    Then I receive a 200 status code
    When I GET /unicorns/<name> endpoint with fullviwes false
    Then I receive a 404 status code

#   This test tests the filter, this is why we perform this test on a single endpoint
  Scenario: Get unicorns with a jwt malformed
    Given I have a malformed token
    When I GET /unicorns endpoint with page number 100 and number per page 20
    Then I receive a 401 status code

  Scenario: Get unicorns with a invalid token
    Given I have an invalid token
    When I GET /unicorns endpoint with page number 100 and number per page 20
    Then I receive a 401 status code

#    Test endpoint with wrong data
  Scenario: Add a unicorn with a malformed payload
    Given I have a malformed unicorn payload
    When I POST it to the /unicorns endpoint
    Then I receive a 400 status code

  Scenario: Add a unicorn that already exist
    Given I GET /unicorns endpoint with page number 100 and number per page 20
    And I take the first unicorn in the list
    When I POST it to the /unicorns endpoint
    Then I receive a 409 status code

  Scenario: Get a unicorn that doesn't exist
    Given I have an unicorn name
    When I GET /unicorns/<name> endpoint with fullviwes false
    Then I receive a 404 status code

  Scenario: Get a unicorn of an other user
    Given I have a unicorn payload
    And I POST it to the /unicorns endpoint
    Then I change the jwt to have a new one of an other user
    When I GET /unicorns/<name> endpoint with fullviwes false
    Then I receive a 403 status code

  Scenario: Update an unicorn with a malformed payload
    Given I have a malformed unicorn payload
    And I update my unicorn payload
    When I PUT it to the /unicorn/<name> endpoint
    Then I receive a 400 status code

  Scenario: Update an unicorn that doesn't exist
    Given I have a unicorn payload
    When I PUT it to the /unicorn/<name> endpoint
    Then I receive a 404 status code

  Scenario: Update an unicorn of an other user
    Given I have a unicorn payload
    And I POST it to the /unicorns endpoint
    Then I change the jwt to have a new one of an other user
    When I PUT it to the /unicorn/<name> endpoint
    Then I receive a 403 status code

  Scenario: Delete an unicorn that doesn't exist
    Given I have an unicorn name
    When I DELETE it to the /unicorns/<name> endpoint
    Then I receive a 404 status code

  Scenario: Delete an unicorn of an other user
    Given I have a unicorn payload
    And I POST it to the /unicorns endpoint
    Then I change the jwt to have a new one of an other user
    When I DELETE it to the /unicorns/<name> endpoint
    Then I receive a 403 status code
