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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static ch.qos.logback.core.joran.util.beans.BeanUtil.isGetter;

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
            if (checkPayloadIsValid(user, findGetters(User.class))) {
                UserEntity newUserEntity = toUserEntity(user);
                if (!userRepository.existsByMail(user.getEmail())) {
                    userRepository.save(newUserEntity);
                    return new ResponseEntity<>(null, HttpStatus.CREATED);
                } else {
                    throw new ApiException(HttpStatus.CONFLICT.value(), "");
                }
            } else {
                throw new ApiException(HttpStatus.BAD_REQUEST.value(), "");
            }

        } else {
            throw new ApiException(HttpStatus.FORBIDDEN.value(), "");
        }
    }

    public ResponseEntity<Void> updateUser(String email, String emailToken, InlineObject password) throws ApiException {

        if (emailToken.equals(email)) {
            UserEntity userEntity = userRepository.findByMail(emailToken);
            if (userEntity == null) {
                throw new ApiException(HttpStatus.NOT_FOUND.value(), "");
            }
            if (checkPayloadIsValid(password, findGetters(InlineObject.class))) {
                userEntity.setPassword(authenticationService.hashPassword(password.getPassword()));
                userRepository.save(userEntity);
                return new ResponseEntity<>(null, HttpStatus.CREATED);
            } else {
                throw new ApiException(HttpStatus.BAD_REQUEST.value(), "");
            }

        } else {
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

    /**
     * This method check that the payload is valid. A valid payload mean that
     * all value were given and that they were not empty
     * @param payload the payload that we want to test if it's valid
     * @param list the list with all getter of the payload's class
     * @return true is the payload is valid, false otherwise
     */
    private boolean checkPayloadIsValid(Object payload, List<Method> list) {

        Object object;
        for (Method method : list) {
            try {
                object = method.invoke(payload);
                if (object == null || object.toString().isEmpty()) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method will return the list of all getter of a class
     * @param c the class that we want to analyse
     * @return a list of method
     */
    private ArrayList<Method> findGetters(Class<?> c) {
        ArrayList<Method> list = new ArrayList<>();
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods)
            if (isGetter(method))
                list.add(method);
        return list;
    }
}
