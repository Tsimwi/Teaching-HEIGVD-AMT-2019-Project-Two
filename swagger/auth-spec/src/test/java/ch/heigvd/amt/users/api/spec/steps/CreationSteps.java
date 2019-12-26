package ch.heigvd.amt.users.api.spec.steps;

import ch.heigvd.amt.users.api.dto.UserCredentials;
import ch.heigvd.amt.users.api.spec.helpers.Environment;
import ch.heigvd.amt.users.ApiException;
import ch.heigvd.amt.users.ApiResponse;
import ch.heigvd.amt.users.api.DefaultApi;
import ch.heigvd.amt.users.api.dto.User;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class CreationSteps {

    private Environment environment;
    private DefaultApi api;

    User user;
    UserCredentials userCredentials;


    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;
    private String token;

    public CreationSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("^there is an authenticate server$")
    public void thereIsAnAuthenticateServer() {
        assertNotNull(api);
    }

    @Given("^I have a credential payload$")
    public void iHaveACredentialPayload() {
        userCredentials = new ch.heigvd.amt.users.api.dto.UserCredentials();
        userCredentials.setEmail("test4");
        userCredentials.setPassword("test4");
    }

    @Then("^I receive a (\\d+) status code and a token$")
    public void iReceiveAStatusCodeAndAToken(int arg0) {
        assertEquals(200, lastStatusCode);
        assertNotNull(token);
    }

    @Then("^I receive a (\\d+) status code$")
    public void i_receive_a_status_code(int arg1) throws Throwable {
        assertEquals(201, lastStatusCode);
    }

    @When("^I POST it to the /authentication endpoint$")
    public void iPOSTItToTheAuthenticationEndpoint() {
        try {
            lastApiResponse = api.authenticateUserWithHttpInfo(userCredentials);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
            token = lastApiResponse.getData().toString();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
    }

    @Given("^I have a user payload$")
    public void iHaveAUserPayload() {
        user = new ch.heigvd.amt.users.api.dto.User();
        user.setEmail("teat@test.com");
        user.setFirstName("FirstName");
        user.setLastName("Test");
        user.setPassword("1234");
        user.setRole("Administrator");
    }

    @When("^I POST it to the /users endpoint$")
    public void iPOSTItToTheUsersEndpoint() {
        try {
            lastApiResponse = api.addUserWithHttpInfo(user);
            //TODO Il faut pouvoir donner le token via le header
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
            token = lastApiResponse.getData().toString();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
    }
}


    