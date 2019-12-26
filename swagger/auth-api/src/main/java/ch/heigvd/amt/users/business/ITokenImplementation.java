package ch.heigvd.amt.users.business;

import ch.heigvd.amt.users.api.exceptions.ApiException;
import ch.heigvd.amt.users.api.model.UserCredentials;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

@Component
public interface ITokenImplementation {

    String createToken(UserCredentials credentials) throws ApiException;
    boolean tokenUserExist(DecodedJWT decodedJWT);
    String getTokenFromHeaderAuthorization(String token) throws ApiException;
    DecodedJWT decodeJWT(String token) throws ApiException;
}
