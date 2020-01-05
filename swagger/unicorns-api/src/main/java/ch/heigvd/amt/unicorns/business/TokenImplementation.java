package ch.heigvd.amt.unicorns.business;

import ch.heigvd.amt.unicorns.api.exceptions.ApiException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenImplementation implements ITokenImplementation {

    /**
     * Extract the token from the header
     * @param token the string that is in the header (Bearer <token<)
     * @return the token
     * @throws ApiException throw an exception if the header is empty of the the word Bearer is missing
     */
    public String getTokenFromHeaderAuthorization(String token) throws ApiException {

        if (token == null) {
            throw new ApiException(401, "Authorization header is missing");
        }
        if (!token.startsWith("Bearer")) {
            throw new ApiException(401, "Authorization head is malformed");
        }
        return token.substring(7);
    }

    /**
     * Verify that the token is signed with the secret and if it's still valid
     * @param token the token
     * @return the token decoded
     * @throws ApiException throw an exception if the token is not valid
     */
    public DecodedJWT decodeJWT(String token) throws ApiException {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth-server")
                    .build();
            return verifier.verify(token);
        } catch (JWTVerificationException exception) {
            throw new ApiException(401, "Invalid token");
        }
    }
}
