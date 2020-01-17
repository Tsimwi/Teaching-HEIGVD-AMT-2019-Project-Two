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
    User user;
    InlineObject password;
    private String token;
    UUID uuid;


    public UsersSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
        this.userToken = new ch.heigvd.amt.users.api.dto.User();
        userToken.setEmail("admin@admin.ch");
        userToken.setRole("Administrator");
        uuid = UUID.randomUUID();

    }

    private String createFakeToken() {
        Algorithm algorithmHS = Algorithm.HMAC256("secret");
        Date now = new Date();
        /* 5 hour of validation */
        Date expiration = new Date(now.getTime() + 3600000 * 5);
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

        this.user = new ch.heigvd.amt.users.api.dto.User();
        this.user.setEmail(this.uuid + "@test.com");
        this.user.setFirstName("FirstName");
        this.user.setLastName("Test");
        this.user.setPassword("1234");
        this.user.setRole("Administrator");
        this.token = createFakeToken();
        api.getApiClient().setApiKey("Bearer " + this.token);
    }


    @When("^I POST it to the /users endpoint$")
    public void iPOSTItToTheUsersEndpoint() {
        try {
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
        this.password.setPassword("test");
        this.token = createFakeToken();
        api.getApiClient().setApiKey("Bearer " + this.token);

    }

    @When("^I PATCH it to the /users/(.*) endpoint$")
    public void iPATCHItToTheUsersEndpoint(String arg0) throws Throwable {
        try {

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
        this.user.setEmail(uuid + "@test.com");
        this.user.setFirstName("FirstName");
        this.user.setLastName("Test");
        this.user.setPassword("1234");
        this.user.setRole("Administrator");
        this.userToken.setRole("Collaborator");
        this.token = createFakeToken();
        api.getApiClient().setApiKey("Bearer " + this.token);

    }

    @Given("^I have a user payload that already exist and a JWT token$")
    public void iHaveAUserPayloadThatAlreadyExistAndAJWTToken() {

        this.user = new ch.heigvd.amt.users.api.dto.User();
        this.user.setEmail("test");
        this.user.setFirstName("FirstName");
        this.user.setLastName("Test");
        this.user.setPassword("1234");
        this.user.setRole("Administrator");
        this.token = createFakeToken();
        api.getApiClient().setApiKey("Bearer " + this.token);

    }

    @Given("^I have a password payload and a JWT token with a bad email$")
    public void iHaveAPasswordPayloadAndAJWTTokenWithABadEmail() {
        this.password = new ch.heigvd.amt.users.api.dto.InlineObject();
        this.password.setPassword("jeujeujeu");
        this.userToken.setEmail("dd");
        this.token = createFakeToken();
        api.getApiClient().setApiKey("Bearer " + this.token);

    }

    @Given("^I have a password payload$")
    public void iHaveAPasswordPayload() {
        this.password = new ch.heigvd.amt.users.api.dto.InlineObject();
        this.password.setPassword("admin");
        this.token = null;
        api.getApiClient().setApiKey("Bearer " + token);
    }

    @When("^I PATCH it to the /users/(.*) endpoint without the header$")
    public void iPATCHItToTheUsersHjjhjEndpointWithoutTheHeader(String arg0) {
        try {
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

    @When("^I POST it to the /users endpoint without Bearer string$")
    public void iPOSTItToTheUsersEndpointWithoutBearerString() {
        try {
            api.getApiClient().setApiKey("Berer " + token);
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

    @Given("^I have a user payload malformed and a JWT token$")
    public void iHaveAUserPayloadMalformedAndAJWTToken() {
        this.user = new ch.heigvd.amt.users.api.dto.User();
        this.user.setEmail(this.uuid + "@test.com");
        this.user.setFirstName("");
        this.user.setLastName("Test");
        this.user.setPassword("1234");
        this.user.setRole("Administrator");
        this.token = createFakeToken();
        api.getApiClient().setApiKey("Bearer " + this.token);
    }

    @Given("^I have a password payload malformed and a JWT token$")
    public void iHaveAPasswordPayloadMalformedAndAJWTToken() {
        this.password = new ch.heigvd.amt.users.api.dto.InlineObject();
        this.password.setPassword("");
        this.token = createFakeToken();
        api.getApiClient().setApiKey("Bearer " + this.token);
    }
}


