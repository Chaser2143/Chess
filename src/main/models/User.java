package models;

/**
 * Represents the User Model in the DB
 */
public class User {
    private String username;
    private String password;
    private String email;

    /**
     * Constructor for the User Model
     * @param username is the username
     * @param password is the password
     * @param email is the user's email
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username sets the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password becomes the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email becomes the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
