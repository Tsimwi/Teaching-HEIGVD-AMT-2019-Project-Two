package ch.heigvd.amt.users.api.spec.steps;

import ch.heigvd.amt.users.api.dto.InlineObject;
import ch.heigvd.amt.users.api.dto.UserCredentials;
import ch.heigvd.amt.users.api.spec.helpers.Environment;
import ch.heigvd.amt.users.ApiException;
import ch.heigvd.amt.users.ApiResponse;
import ch.heigvd.amt.users.api.DefaultApi;
import ch.heigvd.amt.users.api.dto.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class UsersSteps {

    private Environment environment;
    private DefaultApi api;

    User userToken;
    UserCredentials userCredentials;
    User user;
    InlineObject password;
    private String token;


    public UsersSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
        this.userToken = new ch.heigvd.amt.users.api.dto.User();
        userToken.setEmail("remi.poulard@heig-vd.ch");
        userToken.setRole("Administrator");

    }

//    @Given("^there is an authenticate server$")
//    public void thereIsAnAuthenticateServer() {
//        assertNotNull(api);
//    }

//    @Then("^I receive a (\\d+) status code$")
//    public void i_receive_a_status_code(int arg1) throws Throwable {
//        assertEquals(arg1, environment.getLastStatusCode());
//    }

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
            environment.setLastApiResponse(api.addUserWithHttpInfo(user));
            environment.setLastApiCallThrewException(false);
            environment.setLastApiException(null);
            environment.setLastStatusCode(environment.getLastApiResponse().getStatusCode());
        } catch (ApiException e) {
            environment.setLastApiCallThrewException(true);
            environment.setLastApiResponse(null);
            environment.setLastApiException(e);
            environment.setLastStatusCode(environment.getLastApiException().getCode());
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
            environment.setLastApiResponse(api.updateUserWithHttpInfo(arg0, this.password));
            environment.setLastApiCallThrewException(false);
            environment.setLastApiException(null);
            environment.setLastStatusCode(environment.getLastApiResponse().getStatusCode());
        } catch (ApiException e) {
            environment.setLastApiCallThrewException(true);
            environment.setLastApiResponse(null);
            environment.setLastApiException(e);
            environment.setLastStatusCode(environment.getLastApiException().getCode());

        }
    }



    @Given("^I have a user payload and a JWT token not administrator$")
    public void iHaveAUserPayloadAndAJWTTokenNotAdministrator() {
        UUID uuid = UUID.randomUUID();

        this.user = new ch.heigvd.amt.users.api.dto.User();
        this.user.setEmail(uuid+"@test.com");
        this.user.setFirstName("FirstName");
        this.user.setLastName("Test");
        this.user.setPassword("1234");
        this.user.setRole("Administrator");
        this.userToken.setRole("Collaborator");
        this.token = createFakeToken();
    }

    @Given("^I have a user payload that already exist and a JWT token$")
    public void iHaveAUserPayloadThatAlreadyExistAndAJWTToken() {

        this.user = new ch.heigvd.amt.users.api.dto.User();
        this.user.setEmail("remi.poulard@heig-vd.ch");
        this.user.setFirstName("FirstName");
        this.user.setLastName("Test");
        this.user.setPassword("1234");
        this.user.setRole("Administrator");
        this.token = createFakeToken();
    }

    @Given("^I have a password payload and a JWT token with a bad email$")
    public void iHaveAPasswordPayloadAndAJWTTokenWithABadEmail() {
        this.password = new ch.heigvd.amt.users.api.dto.InlineObject();
        this.password.setPassword("jeujeujeu");
        this.userToken.setEmail("wrongEmail");
        this.token = createFakeToken();
    }

    @Given("^I have a password payload$")
    public void iHaveAPasswordPayload() {
        this.password = new ch.heigvd.amt.users.api.dto.InlineObject();
        this.password.setPassword("jeujeujeu");
        this.token = "";
    }
}


