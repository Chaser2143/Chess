package reqRes;

/**
 * Serves as the request for List Games API
 */
public class ListGamesReq extends Request{

    private String authorization;

    /**
     * Constructor for List Games Request
     * @param auth is the auth token for the game
     */
    public ListGamesReq(String auth){
        authorization = auth;
    }

    public String getAuthorization() {
        return authorization;
    }
}
