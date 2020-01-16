package ch.heigvd.amt.unicorns.api.spec.steps;

import ch.heigvd.amt.unicorns.api.dto.Unicorn;
import ch.heigvd.amt.unicorns.api.DefaultApi;
import ch.heigvd.amt.unicorns.api.spec.helpers.Environment;
import ch.heigvd.amt.users.api.dto.User;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;
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

    User userToken;
    Unicorn unicorn;
    UUID uuid;

    public MagicornSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();

    }
}
