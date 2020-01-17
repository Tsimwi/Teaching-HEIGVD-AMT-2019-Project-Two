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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by Olivier Liechti on 27/07/17.
 */
public class UnicornsSteps {

    private Environment environment;
    private DefaultApi api;

    private SimpleUnicorn unicorn;
    private Unicorn receivedUnicorn;
    private SimpleUnicorn receivedSimpleUnicorn;
    private List<SimpleUnicorn> simpleUnicornList;
    private UUID uuid;

    public UnicornsSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
        this.simpleUnicornList = new ArrayList<>();
        this.uuid = UUID.randomUUID();
    }

    @Given("^I have a unicorn payload$")
    public void iHaveAUnicornPayload() throws Throwable {
        this.unicorn = new SimpleUnicorn();
        this.unicorn.setName(this.uuid.toString());
        this.unicorn.setColor("Purple");
        this.unicorn.setHasWings(false);
        this.unicorn.setSpeed(8);
    }

    @When("^I POST it to the /unicorns endpoint$")
    public void iPOSTItToTheUnicornsEndpoint() {
        try {
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
            simpleUnicornList = (List<SimpleUnicorn>) environment.getLastApiResponse().getData();
        } catch (ch.heigvd.amt.unicorns.ApiException e) {
            environment.setLastApiCallThrewException(true);
            environment.setLastApiResponse(null);
            environment.setLastApiException(e);
            environment.setLastStatusCode(environment.getLastApiException().getCode());
        }
    }

    @When("^I DELETE it to the /unicorns/<name> endpoint$")
    public void iDELETEItToTheUnicornsEndpoint() throws Throwable {
        try {
            environment.setLastApiResponse(api.deleteUnicornWithHttpInfo(unicorn.getName()));
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

    @And("^I receive an array of Unicorns$")
    public void iReceiveAnArrayOfUnicorns() {
        assertNotNull(simpleUnicornList);
        assertFalse(simpleUnicornList.isEmpty());
    }


    @And("^I receive an array of Unicorns with magics$")
    public void iReceiveAnArrayOfUnicornsWithMagics() {
        assertNotNull(simpleUnicornList);
        assertFalse(simpleUnicornList.isEmpty());
    }

    @When("^I GET /unicorns/<name> endpoint with fullviwes (.*)$")
    public void iGETUnicornsNameEndpointWithFullviwesFalse(String arg0) {
        try {
            environment.setLastApiResponse(api.getUnicornByNameWithHttpInfo(this.unicorn.getName(), arg0.equals("true")));
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


    @And("^I receive an Unicorns with fullviews (.*)$")
    public void iReceiveAnUnicornsWithFullviews(String arg0) {
        if (arg0.equals("true")) {
            assertNotNull(receivedUnicorn);
            assertNotNull(receivedUnicorn.getMagics());
        } else {
            assertNotNull(receivedUnicorn);
            assertEquals(receivedUnicorn.getName(), unicorn.getName());
        }

    }
}
