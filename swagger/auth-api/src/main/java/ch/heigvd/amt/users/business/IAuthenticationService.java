package ch.heigvd.amt.users.business;


public interface IAuthenticationService {

    public String hashPassword(String plainTextPassword);
    public boolean checkPassword(String plainTextPassword, String hashedPassword);
}