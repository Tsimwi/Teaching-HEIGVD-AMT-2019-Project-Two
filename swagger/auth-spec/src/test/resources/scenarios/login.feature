Feature: Login to the auth api

  Background:
    Given there is an authenticate server

  Scenario: login to api
    Given I have a credential payload
    When I POST it to the /authentication endpoint
    Then I receive a 200 status code and a token

  Scenario: Add a new user
    Given I have a user payload and a JWT token
    When I POST it to the /users endpoint
    Then I receive a 200 status code

   Scenario: Change password
     Given I have a password payload and a JWT token
     When I PATCH it to the /users/"email" endpoint
     Then I receive a 201 status code