package ch.heigvd.amt.users.api.endpoints;

import ch.heigvd.amt.users.api.AuthenticationApi;
import ch.heigvd.amt.users.api.UsersApi;
import ch.heigvd.amt.users.api.exceptions.ApiException;
import ch.heigvd.amt.users.api.model.User;
import ch.heigvd.amt.users.api.model.UserCredentials;
import ch.heigvd.amt.users.business.AuthenticationService;
import ch.heigvd.amt.users.business.IAuthenticationService;
import ch.heigvd.amt.users.business.TokenImplementation;
import ch.heigvd.amt.users.entities.UserEntity;
import ch.heigvd.amt.users.repositories.UserRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class AuthenticateApiController implements AuthenticationApi {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenImplementation tokenImplementation;

    public ResponseEntity<String> authenticateUser(@ApiParam(value = "" ,required=true )  @Valid @RequestBody UserCredentials credentials) {
        String token;
        try {
            token = tokenImplementation.createToken(credentials);
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).build();
        } catch (ApiException e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }
    }

}
