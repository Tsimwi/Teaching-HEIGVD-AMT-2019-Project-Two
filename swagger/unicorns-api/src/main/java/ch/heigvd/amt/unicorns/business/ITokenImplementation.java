package ch.heigvd.amt.unicorns.business;

import ch.heigvd.amt.unicorns.api.exceptions.ApiException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

@Component
public interface ITokenImplementation {

    String getTokenFromHeaderAuthorization(String token) throws ApiException;
    DecodedJWT decodeJWT(String token) throws ApiException;
}
