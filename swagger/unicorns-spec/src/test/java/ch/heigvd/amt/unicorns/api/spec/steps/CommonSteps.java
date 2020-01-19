package ch.heigvd.amt.unicorns.api.spec.steps;

import ch.heigvd.amt.unicorns.api.DefaultApi;
import ch.heigvd.amt.unicorns.api.dto.SimpleMagic;
import ch.heigvd.amt.unicorns.api.dto.SimpleUnicorn;
import ch.heigvd.amt.unicorns.api.dto.UpdateMagic;
import ch.heigvd.amt.unicorns.api.dto.UpdateUnicorn;
import ch.heigvd.amt.unicorns.api.spec.helpers.Environment;
import ch.heigvd.amt.users.api.dto.User;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.swagger.models.auth.In;

import java.util.UUID;

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

    @Given("^I have a malformed token$")
    public void iHaveAMalformedToken() {
        api.getApiClient().setApiKey("Bere " + environment.getToken());
    }

    @Given("^I have an invalid token$")
    public void iHaveAnInvalidToken() {
        api.getApiClient().setApiKey("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBhZG1pbi5jaCIsInJvbGUiOiJBZG1pbmlzdHJhdG9yIiwiaXNzIjoiYXV0aC1zZXJ2ZXIiLCJleHAiOjE1NzkyOTcxOTksImlhdCI6MTU3OTI5NzE5OX0.USYs2AJ40vT0OzHs3QNwU1YIH1j9WlcjC0yo0aTDE0Y");
    }

    @Given("^I have a unicorn payload$")
    public void iHaveAUnicornPayload() throws Throwable {
        String color = "Purple";
        Integer speed = 8;
        boolean hasWings = false;

        SimpleUnicorn simpleUnicorn = new SimpleUnicorn();
        simpleUnicorn.setName(UUID.randomUUID().toString());
        simpleUnicorn.setColor(color);
        simpleUnicorn.setHasWings(hasWings);
        simpleUnicorn.setSpeed(speed);
        environment.setSimpleUnicorn(simpleUnicorn);

        UpdateUnicorn updateUnicorn = new UpdateUnicorn();
        updateUnicorn.setColor(color);
        updateUnicorn.setHasWings(hasWings);
        updateUnicorn.setSpeed(speed);
        environment.setUpdateUnicorn(updateUnicorn);
    }

    @Given("^I have a magic payload$")
    public void iHaveAMagicPayload() throws Throwable {
        String spell = "Unlimited POWER";
        Integer power = 3;

        SimpleMagic simpleMagic = new SimpleMagic();
        simpleMagic.setName(UUID.randomUUID().toString());
        simpleMagic.setPower(power);
        simpleMagic.setSpell(spell);
        environment.setSimpleMagic(simpleMagic);

        UpdateMagic updateMagic = new UpdateMagic();
        updateMagic.setPower(power);
        updateMagic.setSpell(spell);
        environment.setUpdateMagic(updateMagic);
    }

    @Then("^I change the jwt to have a new one of an other user$")
    public void iChangeTheJwtToHaveANewOneOfAnOtherUser() {
        User user = new User();
        user.setEmail("test");
        user.setRole("Administrator");
        environment.setUserToken(user);
        environment.createFakeToken();
        api.getApiClient().setApiKey("Bearer " + environment.getToken());
    }
}
