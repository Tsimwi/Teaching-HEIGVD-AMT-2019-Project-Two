package ch.heigvd.amt.users.business;


import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService {

    public String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public boolean checkPassword(String plainTextPassword, String hashedPassword) {
        try {
            boolean correct = BCrypt.checkpw(plainTextPassword, hashedPassword);
            return correct;
        } catch (Exception e) {
            return false;
        }
    }
}