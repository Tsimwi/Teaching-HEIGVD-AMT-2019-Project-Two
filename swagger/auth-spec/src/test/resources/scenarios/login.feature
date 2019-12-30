Feature: Login to the auth api

  Background:
    Given there is an authenticate server

  Scenario: login to api
    Given I have a credential payload
    When I POST it to the /authentication endpoint
    Then I receive a 200 status code and a token

  Scenario: attempt to login with bad credentials
    Given I have a wrong credential payload
    When I POST it to the /authentication endpoint
    Then I receive a 400 status code