package ch.heigvd.amt.users.business;


import ch.heigvd.amt.users.api.exceptions.ApiException;
import ch.heigvd.amt.users.api.model.UserCredentials;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface IAuthenticationService {
    String hashPassword(String plainTextPassword);
    boolean checkPassword(String plainTextPassword, String hashedPassword);
    ResponseEntity<String> authenticateUser(UserCredentials credentials) throws ApiException;
}