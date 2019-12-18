package ch.heigvd.amt.users.business;


import org.springframework.stereotype.Component;

@Component
public interface IAuthenticationService {
    String hashPassword(String plainTextPassword);
    boolean checkPassword(String plainTextPassword, String hashedPassword);
}