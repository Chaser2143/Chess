package reqRes;

/**
 * Serves as the Logout Request Class
 */
public class LogoutReq extends Request{
    private String authorization;

    /**
     * Instantiates a new request
     * @param auth is the AuthToken corresponding to the session
     */
    public LogoutReq(String auth){
        authorization = auth;
    }

    /**
     * @return the Authtoken
     */
    public String getAuthorization() {
        return authorization;
    }

    /**
     * Sets the new Authtoken
     */
    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}
