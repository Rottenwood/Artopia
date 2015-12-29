package artopia.models;

import artopia.exceptions.EmptyPassword;
import artopia.exceptions.EmptyUsername;

import javax.persistence.*;

/**
 * @author Rottenwood
 */
@Entity
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Transient
    private boolean isAuthenticated = false;

    public User() {}

    // TODO: 22.12.15 Обработка пароля
    public User(String username, String password) throws EmptyUsername, EmptyPassword {
        if (username.equals("")) {
            throw new EmptyUsername();
        } else if (password.equals("")) {
            throw new EmptyPassword();
        }

        String usernameInLowercase = username.toLowerCase();
        username = Character.toString(usernameInLowercase.charAt(0)).toUpperCase() + usernameInLowercase.substring(1);

        this.username = username;
        this.password = password;
    }


    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void authenticate() {
        isAuthenticated = true;
    }

    public String getUsername() {
        return username;
    }

    public long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
