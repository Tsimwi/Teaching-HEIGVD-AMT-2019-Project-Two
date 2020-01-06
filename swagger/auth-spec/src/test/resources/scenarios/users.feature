Feature: Users related actions

  Background:
    Given there is an authenticate server

#    TODO: verifier avec un autre appelle que l'utilisateur a bien été crer
  Scenario: Add a new user
    Given I have a user payload and a JWT token
    When I POST it to the /users endpoint
    Then I receive a 201 status code

  Scenario: Add user but not as administrator
    Given I have a user payload and a JWT token not administrator
    When I POST it to the /users endpoint
    Then I receive a 403 status code

  Scenario: Add user that already exist
    Given I have a user payload that already exist and a JWT token
    When I POST it to the /users endpoint
    Then I receive a 409 status code

  Scenario: Change password
    Given I have a password payload and a JWT token
    When I PATCH it to the /users/"remi.poulard@heig-vd.ch" endpoint
    Then I receive a 201 status code

  Scenario: try to change password of an other user
    Given I have a password payload and a JWT token
    When I PATCH it to the /users/"test@test.com" endpoint
    Then I receive a 403 status code

  Scenario: try to change password of an user that doesn't exist
    Given I have a password payload and a JWT token with a bad email
    When I PATCH it to the /users/"wrongEmail" endpoint
    Then I receive a 404 status code

  Scenario: make a request without JWT token
    Given I have a password payload
    When I PATCH it to the /users/"remi.poulard@heig-vd.ch" endpoint
    Then I receive a 401 status code