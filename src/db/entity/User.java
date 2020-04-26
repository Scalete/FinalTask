package db.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * User entity.
 *
 * @author M.Palaguta
 *
 */
public class User implements Serializable {

    private int id;
    private String firstName;
    private String lastName;
    private String password;
    private String telephone;
    private String email;
    private double count;

    public User(int id, String firstName, String lastName, String password, String telephone, String email, double count) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.telephone = telephone;
        this.email = email;
        this.count = count;
    }

    public User() {
        id = -1;
        firstName = "";
        lastName = "";
        password = "";
        telephone = "";
        email = "";
        count = 0.0d;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }
}
