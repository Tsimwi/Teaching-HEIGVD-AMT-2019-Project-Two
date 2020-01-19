package ch.heigvd.amt.unicorns.api.spec.steps;

import ch.heigvd.amt.unicorns.api.dto.Magicorn;
import ch.heigvd.amt.unicorns.api.dto.Unicorn;
import ch.heigvd.amt.unicorns.api.DefaultApi;
import ch.heigvd.amt.unicorns.api.spec.helpers.Environment;
import ch.heigvd.amt.users.api.dto.User;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Olivier Liechti on 27/07/17.
 */
public class MagicornSteps {

    private Environment environment;
    private DefaultApi api;

    Magicorn magicorn;

    public MagicornSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();

    }

    @And("^I have a magicorn payload with operation (.+) with error (.+)$")
    public void iHaveAMagicornPayloadWithOperation(String arg0, String arg1) {
        magicorn = new Magicorn();
        magicorn.setUnicorn(environment.getSimpleUnicorn().getName());
        if (arg1.equals("false")) {
            magicorn.setMagic(environment.getSimpleMagic().getName());
        } else {
            magicorn.setMagic(null);
        }

        magicorn.setOperation(arg0);
    }

    @When("^I POST it to the /magicorn endpoint$")
    public void iPOSTItToTheMagicornEndpoint() {
        try {
            environment.setLastApiResponse(api.changeMagicornWithHttpInfo(magicorn));
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
}
