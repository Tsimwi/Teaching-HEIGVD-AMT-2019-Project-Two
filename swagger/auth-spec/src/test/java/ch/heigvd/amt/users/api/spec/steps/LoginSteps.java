package ch.heigvd.amt.users.api.spec.steps;

import ch.heigvd.amt.users.api.dto.UserCredentials;
import ch.heigvd.amt.users.api.spec.helpers.Environment;
import ch.heigvd.amt.users.ApiException;
import ch.heigvd.amt.users.api.DefaultApi;
import ch.heigvd.amt.users.api.dto.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class LoginSteps {

    private Environment environment;
    private DefaultApi api;

    UserCredentials userCredentials;
    private String token;


    public LoginSteps(Environment environment) {
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
        userCredentials.setEmail("admin@admin.ch");
        userCredentials.setPassword("test");
    }

    @Then("^I receive a (\\d+) status code and a token$")
    public void iReceiveAStatusCodeAndAToken(int arg0) {
        assertEquals(arg0, environment.getLastStatusCode());
        assertNotNull(token);
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


    @Given("^I have a wrong credential payload$")
    public void iHaveAWrongCredentialPayload() {
        userCredentials = new ch.heigvd.amt.users.api.dto.UserCredentials();
        userCredentials.setEmail("remi.poulard@heig-vd.ch");
        userCredentials.setPassword("youlost");
    }


    @Then("^I receive a (\\d+) status code$")
    public void i_receive_a_status_code(int arg1) throws Throwable {
        assertEquals(arg1, environment.getLastStatusCode());
    }

}


    