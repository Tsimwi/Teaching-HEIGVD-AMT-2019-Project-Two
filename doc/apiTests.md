# Testing strategy

## Test JMeter

JMeter allows us to test the load on APIs and how the behaviors is influenced by this load. We are going to test what happen when :

- there is no paging and a lot of users 
- there is paging with a lot of users

These tests will allows us to show the importance of paging and they could also show some weird behaviors. 

## Test Cucumber JVM

Cucumber allows us to do units tests that can be run automatically with _maven_. These tests allow us to test the behavior of the API according to the given parameters, these parameters should test all the possible requests that could be send to the API (no token, wrong payload, etc). _Cucumber_ allow us to have a description of a scenario written in English and then comprehensive by people for example:

```
  Scenario: login to api
    Given I have a credential payload
    When I POST it to the /authentication endpoint
    Then I receive a 200 status code and a token
```

Here it's very comprehensive what we want to test. And then we have the implementation of these description in java :

```java
@Given("^I have a credential payload$")
public void iHaveACredentialPayload() {
    userCredentials = new ch.heigvd.amt.users.api.dto.UserCredentials();
    userCredentials.setEmail("admin@admin.ch");
    userCredentials.setPassword("test");
}

@When("^I POST it to the /authentication endpoint$")
public void iPOSTItToTheAuthenticationEndpoint() {
    try {
        environment.setLastApiResponse(api.authenticateUserWithHttpInfo(userCredentials));
        environment.setLastApiCallThrewException(false);
        environment.setLastApiException(null);
        environment.setLastStatusCode(environment.getLastApiResponse().getStatusCode());
        token = environment.getLastApiResponse().getData().toString();
    } catch (ApiException e) {
        environment.setLastApiCallThrewException(true);
        environment.setLastApiResponse(null);
        environment.setLastApiException(e);
        environment.setLastStatusCode(environment.getLastApiException().getCode());
    }
}

@Then("^I receive a (\\d+) status code and a token$")
public void iReceiveAStatusCodeAndAToken(int arg0) {
    assertEquals(arg0, environment.getLastStatusCode());
    assertNotNull(token);
}
```



## Opinions





