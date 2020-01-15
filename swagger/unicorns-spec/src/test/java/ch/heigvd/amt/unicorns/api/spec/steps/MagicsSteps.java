package ch.heigvd.amt.unicorns.api.spec.steps;

import ch.heigvd.amt.unicorns.ApiException;
import ch.heigvd.amt.unicorns.ApiResponse;
import ch.heigvd.amt.unicorns.api.DefaultApi;
import ch.heigvd.amt.unicorns.api.spec.helpers.Environment;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Olivier Liechti on 27/07/17.
 */
public class MagicsSteps {

    private Environment environment;
    private DefaultApi api;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    public MagicsSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("^I have a magic payload and a JWT token$")
    public void iHaveAMagicPayloadAndAJWTToken() {
    }

    @When("^I POST it to the /magics endpoint$")
    public void iPOSTItToTheMagicsEndpoint() {
    }

    @When("^I GET /magics endpoint$")
    public void iGETMagicsEndpoint() {
    }

    @And("^I receive an array of Magics$")
    public void iReceiveAnArrayOfMagics() {
    }
}
