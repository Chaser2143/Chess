package reqRes;

/**
 * Login Request Class
 */
public class LoginReq {
    private String username;
    private String password;

    /**
     * Instantiates the Login Request
     * @param username becomes the username
     * @param password becomes the password
     */
    public LoginReq(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username becomes the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
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
}
