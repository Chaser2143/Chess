package reqRes;

/**
 * Serves as the response for the login API
 */
public class LoginRes extends Response{
    private String message;
    private String authToken;
    private String username;

    /**
     * Constructor for Login Response
     * @param message Message for response
     * @param authToken Authtoken generated
     * @param username Username Associated
     */
    public LoginRes(String message, String authToken, String username) {
        super(message);
        this.authToken = authToken;
        this.username = username;
    }

    /**
     * Gets the Response Message
     * @return string
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the Response message
     * @param message becomes the new message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the response auth token
     * @return String
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Sets the Response Auth Token
     * @param authToken becomes the new auth token
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * Gets the Response Username
     * @return string
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the response username
     * @param username becomes the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
