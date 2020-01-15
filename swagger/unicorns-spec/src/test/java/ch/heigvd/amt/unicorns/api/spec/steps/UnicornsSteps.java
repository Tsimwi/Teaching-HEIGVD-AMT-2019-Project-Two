package ch.heigvd.amt.unicorns.api.spec.steps;

import ch.heigvd.amt.unicorns.api.DefaultApi;
import ch.heigvd.amt.unicorns.api.dto.SimpleUnicorn;
import ch.heigvd.amt.unicorns.api.dto.Unicorn;
import ch.heigvd.amt.unicorns.api.spec.helpers.Environment;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import ch.heigvd.amt.unicorns.ApiException;
import ch.heigvd.amt.unicorns.ApiResponse;
import ch.heigvd.amt.unicorns.api.DefaultApiTest;
import ch.heigvd.amt.unicorns.api.dto.MagicTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by Olivier Liechti on 27/07/17.
 */
public class UnicornsSteps {

    private Environment environment;
    private DefaultApi api;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    private SimpleUnicorn unicorn;
    private Unicorn receivedUnicorn;

    public UnicornsSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("^I have a unicorn payload and a JWT token$")
    public void iHaveAUnicornPayloadAndAJWTToken() {
        this.unicorn = new SimpleUnicorn();
        this.unicorn.setName("Rainbow");
        this.unicorn.setColor("Purple");
        this.unicorn.setHasWings(false);
        this.unicorn.setSpeed(8);
    }

    @When("^I POST it to the /unicorns endpoint$")
    public void iPOSTItToTheUnicornsEndpoint() {
        try {
            api.getApiClient().setApiKey("Bearer " + environment.getToken());
            environment.setLastApiResponse(api.addUnicornWithHttpInfo(this.unicorn));
            environment.setLastApiCallThrewException(false);
            environment.setLastApiException(null);
            environment.setLastStatusCode(environment.getLastApiResponse().getStatusCode());
        } catch (ch.heigvd.amt.unicorns.ApiException e) {
            environment.setLastApiCallThrewException(true);
            environment.setLastApiResponse(null);
            environment.setLastApiException(e);
            environment.setLastStatusCode(environment.getLastApiException().getCode());
        }
    }

    @When("^I GET /unicorns endpoint$")
    public void iGETUnicornsEndpoint() {
        try {
            environment.setLastApiResponse(api.getUnicornsWithHttpInfo(1, 50));
            environment.setLastApiCallThrewException(false);
            environment.setLastApiException(null);
            environment.setLastStatusCode(environment.getLastApiResponse().getStatusCode());
            //receivedUnicorn =
        } catch (ch.heigvd.amt.unicorns.ApiException e) {
            environment.setLastApiCallThrewException(true);
            environment.setLastApiResponse(null);
            environment.setLastApiException(e);
            environment.setLastStatusCode(environment.getLastApiException().getCode());
        }
    }

    @And("^I receive an array of Unicorns$")
    public void iReceiveAnArrayOfUnicorns() {
    }

    @When("^I GET /unicorns/([^\"]*) endpoint$")
    public void iGETUnicornsEndpoint(String arg0) throws Throwable {
        try {
            environment.setLastApiResponse(api.getUnicornByNameWithHttpInfo(arg0, false));
            environment.setLastApiCallThrewException(false);
            environment.setLastApiException(null);
            environment.setLastStatusCode(environment.getLastApiResponse().getStatusCode());
            receivedUnicorn = (Unicorn) environment.getLastApiResponse().getData();
        } catch (ch.heigvd.amt.unicorns.ApiException e) {
            environment.setLastApiCallThrewException(true);
            environment.setLastApiResponse(null);
            environment.setLastApiException(e);
            environment.setLastStatusCode(environment.getLastApiException().getCode());
        }
    }

    @And("^I receive a SimpleUnicorn$")
    public void iReceiveASimpleUnicorn() {
        assertEquals(receivedUnicorn.getName(), unicorn.getName());
    }
}
