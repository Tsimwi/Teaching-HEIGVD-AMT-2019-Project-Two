package ch.heigvd.amt.users.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
@Entity
public class UserEntity implements Serializable {

    @Id
    private String mail;

    private String firstName;
    private String lastName;
    private String password;
    private String role;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMail() {
        return mail;
    }
}
