package ch.heigvd.amt.users.business;

import ch.heigvd.amt.users.api.exceptions.ApiException;
import ch.heigvd.amt.users.api.model.InlineObject;
import ch.heigvd.amt.users.api.model.User;
import ch.heigvd.amt.users.entities.UserEntity;
import ch.heigvd.amt.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UsersService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    IAuthenticationService authenticationService;

    @Autowired
    TokenImplementation tokenImplementation;

    public ResponseEntity<Void> addUser(User user, String role) throws ApiException {
        if (role.equals("Administrator")) {
            UserEntity newUserEntity = toUserEntity(user);
            UserEntity userInDb = userRepository.findByMail(user.getEmail());
            if (userInDb == null){
                userRepository.save(newUserEntity);
                return new ResponseEntity<>(null, HttpStatus.CREATED);
            }else {
                throw new ApiException(HttpStatus.CONFLICT.value(), "");
            }
        } else {
            throw new ApiException(HttpStatus.FORBIDDEN.value(), "");
        }
    }

    public ResponseEntity<Void> updateUser(String email, String emailToken, InlineObject password) throws ApiException {

        if(emailToken.equals(email)){
            UserEntity userEntity = userRepository.findByMail(emailToken);
            if (userEntity == null){
                throw new ApiException(HttpStatus.NOT_FOUND.value(), "");
            }
            userEntity.setPassword(authenticationService.hashPassword(password.getPassword()));
            userRepository.save(userEntity);
            return new ResponseEntity<>(null, HttpStatus.CREATED);

        }else {
            throw new ApiException(HttpStatus.FORBIDDEN.value(), "");
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
}
