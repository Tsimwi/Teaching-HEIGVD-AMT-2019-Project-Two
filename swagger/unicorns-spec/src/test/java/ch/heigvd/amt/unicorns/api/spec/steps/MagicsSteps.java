package ch.heigvd.amt.unicorns.api.spec.steps;

import ch.heigvd.amt.unicorns.api.DefaultApi;
import ch.heigvd.amt.unicorns.api.dto.SimpleMagic;
import ch.heigvd.amt.unicorns.api.dto.Magic;
import ch.heigvd.amt.unicorns.api.dto.UpdateMagic;
import ch.heigvd.amt.unicorns.api.spec.helpers.Environment;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;



import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by Olivier Liechti on 27/07/17.
 */
public class MagicsSteps {

    private Environment environment;
    private DefaultApi api;

    private Magic receivedMagic;
    private List<SimpleMagic> simpleMagicsList;

    private Integer updatedPowerValue;

    public MagicsSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
        this.simpleMagicsList = new ArrayList<>();
    }

    @When("^I POST it to the /magics endpoint$")
    public void iPOSTItToTheMagicsEndpoint() {
        try {
            environment.setLastApiResponse(api.addMagicWithHttpInfo(environment.getSimpleMagic()));
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

    @When("^I GET /magics endpoint with page number (\\d+) and number per page (\\d+)$")
    public void iGETMagicsEndpoint(int arg0, int arg1) {
        try {
            environment.setLastApiResponse(api.getMagicsWithHttpInfo(arg0, arg1));
            environment.setLastApiCallThrewException(false);
            environment.setLastApiException(null);
            environment.setLastStatusCode(environment.getLastApiResponse().getStatusCode());
            simpleMagicsList = (List<SimpleMagic>) environment.getLastApiResponse().getData();
        } catch (ch.heigvd.amt.unicorns.ApiException e) {
            environment.setLastApiCallThrewException(true);
            environment.setLastApiResponse(null);
            environment.setLastApiException(e);
            environment.setLastStatusCode(environment.getLastApiException().getCode());
        }
    }

    @When("^I DELETE it to the /magics/<name> endpoint$")
    public void iDELETEItToTheMagicsEndpoint() throws Throwable {
        try {
            environment.setLastApiResponse(api.deleteMagicWithHttpInfo(environment.getSimpleMagic().getName()));
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

    @And("^I receive an array of Magics$")
    public void iReceiveAnArrayOfMagics() {
        assertNotNull(simpleMagicsList);
        assertFalse(simpleMagicsList.isEmpty());
    }


    @And("^I receive an array of Magics with magics$")
    public void iReceiveAnArrayOfMagicsWithMagics() {
        assertNotNull(simpleMagicsList);
        assertFalse(simpleMagicsList.isEmpty());
    }

    @When("^I GET /magics/<name> endpoint with fullview (.*)$")
    public void iGETMagicsNameEndpointWithFullviewFalse(String arg0) {
        try {
            environment.setLastApiResponse(api.getMagicByNameWithHttpInfo(environment.getSimpleMagic().getName(), arg0.equals("true")));
            environment.setLastApiCallThrewException(false);
            environment.setLastApiException(null);
            environment.setLastStatusCode(environment.getLastApiResponse().getStatusCode());
            receivedMagic = (Magic) environment.getLastApiResponse().getData();

        } catch (ch.heigvd.amt.unicorns.ApiException e) {
            environment.setLastApiCallThrewException(true);
            environment.setLastApiResponse(null);
            environment.setLastApiException(e);
            environment.setLastStatusCode(environment.getLastApiException().getCode());
        }
    }


    @And("^I receive a Magic with fullview (.*)$")
    public void iReceiveAnMagicsWithFullviews(String arg0) {
        if (arg0.equals("true")) {
            assertNotNull(receivedMagic);
            assertNotNull(receivedMagic.getUnicorns());
        } else {
            assertNotNull(receivedMagic);
            assertEquals(receivedMagic.getName(), environment.getSimpleMagic().getName());
        }

    }

    @And("^I update my magic payload$")
    public void iUpdateMyMagicPayload() {
        SimpleMagic simpleMagic = environment.getSimpleMagic();
        UpdateMagic updateMagic = new UpdateMagic();
        updatedPowerValue = 10;

        updateMagic.setPower(updatedPowerValue);
        updateMagic.setSpell(simpleMagic.getSpell());
        environment.setUpdateMagic(updateMagic);
    }

    @When("^I PUT it to the /magic/<name> endpoint$")
    public void iPUTItToTheMagicNameEndpoint() {
        try {
            environment.setLastApiResponse(api.updateMagicWithHttpInfo(environment.getSimpleMagic().getName(), environment.getUpdateMagic()));
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

    @And("^I receive a Magic that match the update with fullview false$")
    public void iReceiveAMagicsThatMatchTheUpdateWithFullviewFalse() {
        assertEquals(updatedPowerValue, environment.getUpdateMagic().getPower());
    }

    @Given("^I have a malformed magic payload$")
    public void iHaveAMalformedMagicPayload() {
        SimpleMagic magic = new SimpleMagic();
        magic.setName(UUID.randomUUID().toString());
        magic.setPower(null);
        magic.setSpell("");
        environment.setSimpleMagic(magic);
    }

    @And("^I take the first magic in the list$")
    public void iTakeTheFirstInTheList() {
        assertNotNull(this.simpleMagicsList);
        environment.setSimpleMagic(simpleMagicsList.get(0));

    }

    @Given("^I have a magic name$")
    public void iHaveAMagicName() {
        environment.setSimpleMagic(new SimpleMagic());
        environment.getSimpleMagic().setName(UUID.randomUUID().toString());
    }
}
