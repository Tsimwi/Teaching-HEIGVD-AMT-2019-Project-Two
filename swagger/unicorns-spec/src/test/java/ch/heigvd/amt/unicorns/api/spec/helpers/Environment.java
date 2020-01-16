package ch.heigvd.amt.unicorns.api.spec.helpers;

import ch.heigvd.amt.unicorns.api.DefaultApi;
import ch.heigvd.amt.unicorns.ApiException;
import ch.heigvd.amt.unicorns.ApiResponse;
import ch.heigvd.amt.users.api.dto.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Olivier Liechti on 24/06/17.
 */
public class Environment {

    private DefaultApi api = new DefaultApi();

    private ch.heigvd.amt.unicorns.ApiResponse lastApiResponse;
    private ch.heigvd.amt.unicorns.ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;
    private String token;
    private User userToken;

    public Environment() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("environment.properties"));
        String url = properties.getProperty("ch.heigvd.amt.unicorns.server.url");
        api.getApiClient().setBasePath(url);
        this.userToken = new User();
        this.userToken.setEmail("test");
        this.userToken.setRole("Administrator");
    }

    public DefaultApi getApi() {
        return api;
    }

    public void setApi(DefaultApi api) {
        this.api = api;
    }

    public ch.heigvd.amt.unicorns.ApiResponse getLastApiResponse() {
        return lastApiResponse;
    }

    public void setLastApiResponse(ApiResponse lastApiResponse) {
        this.lastApiResponse = lastApiResponse;
    }

    public ch.heigvd.amt.unicorns.ApiException getLastApiException() {
        return lastApiException;
    }

    public void setLastApiException(ApiException lastApiException) {
        this.lastApiException = lastApiException;
    }

    public boolean isLastApiCallThrewException() {
        return lastApiCallThrewException;
    }

    public void setLastApiCallThrewException(boolean lastApiCallThrewException) {
        this.lastApiCallThrewException = lastApiCallThrewException;
    }

    public int getLastStatusCode() {
        return lastStatusCode;
    }

    public void setLastStatusCode(int lastStatusCode) {
        this.lastStatusCode = lastStatusCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void createFakeToken() {
        Algorithm algorithmHS = Algorithm.HMAC256("secret");
        Date now = new Date();
        /* 5 hour of validation */
        Date expiration = new Date(now.getTime() + 3600000 * 5);
        this.token = JWT.create()
                .withSubject(userToken.getEmail())
                .withIssuer("auth-server")
                .withIssuedAt(now)
                .withExpiresAt(expiration)
                .withClaim("role", userToken.getRole())
                .sign(algorithmHS);
    }

    public void setUserToken(User userToken) {
        this.userToken = userToken;
    }
}
