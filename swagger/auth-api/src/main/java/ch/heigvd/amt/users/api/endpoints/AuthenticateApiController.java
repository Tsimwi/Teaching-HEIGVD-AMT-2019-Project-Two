package ch.heigvd.amt.users.api.endpoints;

import ch.heigvd.amt.users.api.AuthenticationApi;
import ch.heigvd.amt.users.api.UsersApi;
import ch.heigvd.amt.users.api.model.User;
import ch.heigvd.amt.users.api.model.UserCredentials;
import ch.heigvd.amt.users.entities.UserEntity;
import ch.heigvd.amt.users.repositories.UserRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ResponseEntity<Object> createUser(@ApiParam(value = "", required = true) @Valid @RequestBody UserCredentials creds) {
//        UserEntity newUserEntity = toUserEntity(user);
//        userRepository.save(newUserEntity);
//        String email = newUserEntity.getMail();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand().toUri();

        return ResponseEntity.created(location).build();
    }

}
