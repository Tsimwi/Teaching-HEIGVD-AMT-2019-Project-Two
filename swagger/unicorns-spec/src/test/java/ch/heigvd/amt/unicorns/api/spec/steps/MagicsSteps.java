package ch.heigvd.amt.unicorns.api.spec.steps;

import ch.heigvd.amt.unicorns.api.DefaultApi;
import ch.heigvd.amt.unicorns.api.dto.SimpleMagic;
import ch.heigvd.amt.unicorns.api.dto.Magic;
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

    private SimpleMagic magic;
    private Magic receivedMagic;
    private List<SimpleMagic> simpleMagicsList;
    private UUID uuid;
    private UUID uuidUpdated;

    public MagicsSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
        this.simpleMagicsList = new ArrayList<>();
        this.uuid = UUID.randomUUID();
    }

    @Given("^I have a magic payload$")
    public void iHaveAMagicPayload() throws Throwable {
        this.magic = new SimpleMagic();
        this.magic.setName(this.uuid.toString());
        this.magic.setPower(3);
        this.magic.setSpell("Unlimited power !!");
    }

    @When("^I POST it to the /magics endpoint$")
    public void iPOSTItToTheMagicsEndpoint() {
        try {
            environment.setLastApiResponse(api.addMagicWithHttpInfo(this.magic));
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
            environment.setLastApiResponse(api.deleteMagicWithHttpInfo(magic.getName()));
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

    @When("^I GET /magics/<name> endpoint with fullviwes (.*)$")
    public void iGETMagicsNameEndpointWithFullviwesFalse(String arg0) {
        try {
            environment.setLastApiResponse(api.getMagicByNameWithHttpInfo(this.magic.getName(), arg0.equals("true")));
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


    @And("^I receive an Magics with fullviews (.*)$")
    public void iReceiveAnMagicsWithFullviews(String arg0) {
        if (arg0.equals("true")) {
            assertNotNull(receivedMagic);
            assertNotNull(receivedMagic.getUnicorns());
        } else {
            assertNotNull(receivedMagic);
            assertEquals(receivedMagic.getName(), magic.getName());
        }

    }

    @And("^I update my magic payload$")
    public void iUpdateMyMagicPayload() {
        uuidUpdated = UUID.randomUUID();
        magic.setName(uuidUpdated.toString());
    }

    @When("^I PUT it to the /magic/<name> endpoint$")
    public void iPUTItToTheMagicNameEndpoint() {
        try {
            environment.setLastApiResponse(api.updateMagicWithHttpInfo(uuid.toString(), magic));
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

    @And("^I receive an Magics that match the update with fullviews false$")
    public void iReceiveAnMagicsThatMatchTheUpdateWithFullviewsFalse() {
        assertEquals(uuidUpdated.toString(), magic.getName());
    }

    @Given("^I have a malformed magic payload$")
    public void iHaveAMalformedMagicPayload() {
        this.magic = new SimpleMagic();
        this.magic.setName(this.uuid.toString());
        this.magic.setPower(3);
        this.magic.setSpell("");
    }

    @And("^I take the first magic in the list$")
    public void iTakeTheFirstInTheList() {
        assertNotNull(this.simpleMagicsList);
        this.magic = simpleMagicsList.get(0);

    }

    @Given("^I have an magic name$")
    public void iHaveAnMagicName() {
        this.magic = new SimpleMagic();
        this.magic.setName(UUID.randomUUID().toString());
    }
}
