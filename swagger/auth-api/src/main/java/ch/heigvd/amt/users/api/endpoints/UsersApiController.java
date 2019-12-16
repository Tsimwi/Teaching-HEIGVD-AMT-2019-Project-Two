package ch.heigvd.amt.users.api.endpoints;

import ch.heigvd.amt.users.business.AuthenticationService;
import ch.heigvd.amt.users.business.IAuthenticationService;
import ch.heigvd.amt.users.entities.UserEntity;
import ch.heigvd.amt.users.repositories.UserRepository;
import ch.heigvd.amt.users.api.UsersApi;
import ch.heigvd.amt.users.api.model.User;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class UsersApiController implements UsersApi {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    public ResponseEntity<Object> addUser(@ApiParam(value = "" ,required=true )  @Valid @RequestBody User user) {
        UserEntity newUserEntity = toUserEntity(user);

        userRepository.save(newUserEntity);

//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest().path("/{id}")
//                .buildAndExpand(newUserEntity.getMail()).toUri();
//
//        return ResponseEntity.created(location).build();
        return null;
    }


    public ResponseEntity<Void> updateUser(@ApiParam(value = "",required=true) @PathVariable("email") String email, @ApiParam(value = "" ,required=true )  @Valid @RequestBody String password) {


        return null;
    }


    private UserEntity toUserEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setMail(user.getEmail());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setPassword(authenticationService.hashPassword(user.getPassword()));
        entity.setRole(user.getRole());
        return entity;
    }

    private User toUser(UserEntity entity) {
        User user = new User();
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setRole(entity.getRole());
        return user;
    }

}
