package ch.heigvd.amt.users.api.spec.steps;

import ch.heigvd.amt.users.api.dto.InlineObject;
import ch.heigvd.amt.users.api.dto.UserCredentials;
import ch.heigvd.amt.users.api.spec.helpers.Environment;
import ch.heigvd.amt.users.ApiException;
import ch.heigvd.amt.users.ApiResponse;
import ch.heigvd.amt.users.api.DefaultApi;
import ch.heigvd.amt.users.api.dto.User;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;


import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class CreationSteps {

    private Environment environment;
    private DefaultApi api;

    User userToken;
    User user;
    UserCredentials userCredentials;
    InlineObject password;


    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;
    private String token;


    public CreationSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
        this.userToken = new ch.heigvd.amt.users.api.dto.User();
        userToken.setEmail("remi.poulard@heig-vd.ch");
        userToken.setRole("Administrator");
    }

    private String createFakeToken(){
        Algorithm algorithmHS = Algorithm.HMAC256("secret");
        Date now = new Date();
        /* 1 hour of validation */
        Date expiration = new Date(now.getTime() + 3600000);
        return JWT.create()
                .withSubject(userToken.getEmail())
                .withIssuer("auth-server")
                .withIssuedAt(now)
                .withExpiresAt(expiration)
                .withClaim("role", userToken.getRole())
                .sign(algorithmHS);
    }

    @Given("^there is an authenticate server$")
    public void thereIsAnAuthenticateServer() {
        assertNotNull(api);
    }

    @Given("^I have a credential payload$")
    public void iHaveACredentialPayload() {
        userCredentials = new ch.heigvd.amt.users.api.dto.UserCredentials();
        userCredentials.setEmail("remi.poulard@heig-vd.ch");
        userCredentials.setPassword("jeujeujeu");
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

    @Given("^I have a user payload and a JWT token$")
    public void iHaveAUserPayloadAndAJWTToken() {
        UUID uuid = UUID.randomUUID();

        this.user = new ch.heigvd.amt.users.api.dto.User();
        this.user.setEmail(uuid+"@test.com");
        this.user.setFirstName("FirstName");
        this.user.setLastName("Test");
        this.user.setPassword("1234");
        this.user.setRole("Administrator");
        this.token = createFakeToken();
    }


    @When("^I POST it to the /users endpoint$")
    public void iPOSTItToTheUsersEndpoint() {
        try {
            api.getApiClient().addDefaultHeader("Authorization", "Bearer " + token);
            lastApiResponse = api.addUserWithHttpInfo(user);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
    }

    @Given("^I have a password payload and a JWT token$")
    public void iHaveAPasswordPayloadAndAJWTToken() {
        this.password = new ch.heigvd.amt.users.api.dto.InlineObject();
        this.password.setPassword("jeujeujeu");
        this.token = createFakeToken();

    }

    @When("^I PATCH it to the /users/\"([^\"]*)\" endpoint$")
    public void iPATCHItToTheUsersEndpoint(String arg0) throws Throwable {
        try {
            api.getApiClient().setApiKey("Bearer " + token);
            lastApiResponse = api.updateUserWithHttpInfo(userToken.getEmail(), this.password);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
    }
}


    