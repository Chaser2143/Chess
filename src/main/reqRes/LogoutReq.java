package reqRes;

import models.AuthToken;

/**
 * Serves as the Logout Request Class
 */
public class LogoutReq {
    private AuthToken authorization;

    /**
     * Instantiates a new request
     * @param auth is the AuthToken corresponding to the session
     */
    public LogoutReq(AuthToken auth){
        authorization = auth;
    }

    /**
     * @return the Authtoken
     */
    public AuthToken getAuthorization() {
        return authorization;
    }

    /**
     * Sets the new Authtoken
     */
    public void setAuthorization(AuthToken authorization) {
        this.authorization = authorization;
    }
}
