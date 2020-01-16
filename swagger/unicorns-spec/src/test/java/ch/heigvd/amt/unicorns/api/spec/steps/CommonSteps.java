package ch.heigvd.amt.unicorns.api.spec.steps;

import ch.heigvd.amt.unicorns.api.DefaultApi;
import ch.heigvd.amt.unicorns.api.spec.helpers.Environment;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Olivier Liechti on 27/07/17.
 */
public class CommonSteps {

    private Environment environment;
    private DefaultApi api;


    public CommonSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("^there is a unicorns API$")
    public void thereIsAUnicornsAPI() {
        assertNotNull(api);
    }

    @Then("^I receive a (\\d+) status code$")
    public void iReceiveAStatusCode(int arg0) {
        assertEquals(arg0, environment.getLastStatusCode());
    }

    @And("^I have a token$")
    public void iHaveAToken() {
        environment.createFakeToken();
        api.getApiClient().setApiKey("Bearer " + environment.getToken());

    }
}
