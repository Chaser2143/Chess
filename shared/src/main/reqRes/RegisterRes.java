package reqRes;

/**
 * Response for Register API
 */
public class RegisterRes extends Response{
    private String username;
    private String authToken;
    /**
     * Instantiates a constructor for the response
     */
    public RegisterRes(String username, String authToken) {
        this.username = username;
        this.authToken = authToken;
    }

    public RegisterRes(String Error){
        super(Error);
    }

    /**
     * Gets the Username
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
     * Returns the Authtoken
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Sets the Authtoken
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
