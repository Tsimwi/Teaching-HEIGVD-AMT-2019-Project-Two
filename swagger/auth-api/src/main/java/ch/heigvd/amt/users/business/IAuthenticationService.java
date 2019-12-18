package ch.heigvd.amt.users.business;


import org.springframework.stereotype.Component;

@Component
public interface IAuthenticationService {

    public String hashPassword(String plainTextPassword);
    public boolean checkPassword(String plainTextPassword, String hashedPassword);
}