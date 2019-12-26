package ch.heigvd.amt.users.api.endpoints;

import ch.heigvd.amt.users.business.IAuthenticationService;
import ch.heigvd.amt.users.business.TokenImplementation;
import ch.heigvd.amt.users.entities.UserEntity;
import ch.heigvd.amt.users.repositories.UserRepository;
import ch.heigvd.amt.users.api.UsersApi;
import ch.heigvd.amt.users.api.model.User;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class UsersApiController implements UsersApi {

    @Autowired
    UserRepository userRepository;

    @Autowired
    IAuthenticationService authenticationService;

    @Autowired
    TokenImplementation tokenImplementation;

    @Autowired
    HttpServletRequest httpServletRequest;

    public ResponseEntity<Void> addUser(@ApiParam(value = "" ,required=true )  @Valid @RequestBody User user){

        String role = (String) httpServletRequest.getAttribute("role");
        if (role.equals("Administrator")) {
            UserEntity newUserEntity = toUserEntity(user);
            UserEntity userInDb = userRepository.findByMail(user.getEmail());
            if (userInDb == null){
                userRepository.save(newUserEntity);
                return new ResponseEntity<>(null, HttpStatus.CREATED);
            }else {
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }


    public ResponseEntity<Void> updateUser(@ApiParam(value = "",required=true) @PathVariable("email") String email,@ApiParam(value = "" ,required=true )  @Valid @RequestBody String password) {

        String role = (String) httpServletRequest.getAttribute("role");
        String tokenEmail = (String) httpServletRequest.getAttribute("email");
        if(tokenEmail.equals(email)){
            UserEntity userEntity = userRepository.findByMail(tokenEmail);
            if (userEntity == null){
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            userEntity.setPassword(authenticationService.hashPassword(password));
            UserEntity userEntityUpdated = userRepository.save(userEntity);
            if (userEntityUpdated != null){
                return new ResponseEntity<>(null, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
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
