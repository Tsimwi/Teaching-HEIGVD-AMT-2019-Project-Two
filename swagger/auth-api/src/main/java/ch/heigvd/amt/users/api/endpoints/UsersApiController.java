package ch.heigvd.amt.users.api.endpoints;

import ch.heigvd.amt.users.api.exceptions.ApiException;
import ch.heigvd.amt.users.api.model.InlineObject;
import ch.heigvd.amt.users.business.IAuthenticationService;
import ch.heigvd.amt.users.business.TokenImplementation;
import ch.heigvd.amt.users.business.UsersService;
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
    HttpServletRequest httpServletRequest;

    @Autowired
    UsersService usersService;

    public ResponseEntity<Void> addUser(@ApiParam(value = "" ,required=true )  @Valid @RequestBody User user) {

        try{
             return usersService.addUser(user, (String) httpServletRequest.getAttribute("role"));
        }catch (ApiException exception){
            return new ResponseEntity<>( HttpStatus.valueOf(exception.getCode()));
        }

    }


    public ResponseEntity<Void> updateUser(@ApiParam(value = "",required=true) @PathVariable("email") String email,@ApiParam(value = "" ,required=true )  @Valid @RequestBody InlineObject password) {


        String tokenEmail = (String) httpServletRequest.getAttribute("email");

        try {
            return usersService.updateUser(email, tokenEmail, password);
        } catch (ApiException exception) {
            return new ResponseEntity<>( HttpStatus.valueOf(exception.getCode()));
        }
    }

}
