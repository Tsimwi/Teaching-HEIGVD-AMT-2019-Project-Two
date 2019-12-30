package ch.heigvd.amt.users.api.endpoints;

import ch.heigvd.amt.users.api.AuthenticationApi;
import ch.heigvd.amt.users.api.exceptions.ApiException;
import ch.heigvd.amt.users.api.model.UserCredentials;
import ch.heigvd.amt.users.business.AuthenticationService;
import ch.heigvd.amt.users.business.TokenImplementation;
import ch.heigvd.amt.users.repositories.UserRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class AuthenticateApiController implements AuthenticationApi {

    @Autowired
    AuthenticationService authenticationService;

    public ResponseEntity<String> authenticateUser(@ApiParam(value = "" ,required=true )  @Valid @RequestBody UserCredentials credentials) {
        try {
            return authenticationService.authenticateUser(credentials);
        } catch (ApiException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getCode()));

        }
    }

}
