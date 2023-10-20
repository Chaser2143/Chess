package reqRes;

/**
 * Request Class for Register API
 */
public class RegisterReq extends Request{
    private String username;
    private String password;
    private String email;

    /**
     * Instantiates the request
     */
    public RegisterReq(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Returns the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setsthe email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
