package models;

/**
 * Represents the AuthToken Model in the DB
 */
public class AuthToken {
    private String authToken;
    private String username;

    /**
     * Constructor for AuthToken
     * @param authToken is the string value
     * @param username is the username it should correspond to
     */
    public AuthToken(String authToken, String username) {
        this.authToken = authToken;
        this.username = username;
    }

    /**
     * @return the auth token
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Sets the authtoken
     * @param authToken to set it to
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username is set as the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
