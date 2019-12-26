package ch.heigvd.amt.users.business;

import ch.heigvd.amt.users.api.exceptions.ApiException;
import ch.heigvd.amt.users.api.model.UserCredentials;
import ch.heigvd.amt.users.entities.UserEntity;
import ch.heigvd.amt.users.repositories.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenImplementation implements ITokenImplementation {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    public String createToken(UserCredentials credentials) throws ApiException {
        UserEntity userEntity = userRepository.findByMail(credentials.getEmail());

        /* check if the user was in the database and if the password supplied is correct */
        if (userEntity == null || !authenticationService.checkPassword(credentials.getPassword(), userEntity.getPassword())) {
            throw new ApiException(400, "Invalid email/password supplied.");
        }

        Algorithm algorithmHS = Algorithm.HMAC256("secret");
        Date now = new Date();
        /* 1 hour of validation */
        Date expiration = new Date(now.getTime() + 3600000);
        return JWT.create()
                .withSubject(userEntity.getMail())
                .withIssuer("auth-server")
                .withIssuedAt(now)
                .withExpiresAt(expiration)
                .withClaim("role", userEntity.getRole())
                .sign(algorithmHS);
    }

    /**
     * Loof if the user provided by the token existe or not
     * @param decodedJWT token given in the header of the request
     * @return true if the user exist, false otherwise
     */
    public boolean tokenUserExist(DecodedJWT decodedJWT){
        String email = decodedJWT.getSubject();
        return userRepository.findByMail(email) != null;
    }

    public String getTokenFromHeaderAuthorization(String token) throws ApiException {

        if (token == null) {
            throw new ApiException(401, "Header Authorization is missing");
        }
        if (!token.startsWith("Bearer")) {
            throw new ApiException(401, "Header Authorization is not well formed");
        }
        return token.substring(7);
    }

    public DecodedJWT decodeJWT(String token) throws ApiException {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth-server")
                    .build(); //Reusable verifier instance
            return verifier.verify(token);
        } catch (JWTVerificationException exception) {
            throw new ApiException(401, "Invalid token");
        }
    }
}
