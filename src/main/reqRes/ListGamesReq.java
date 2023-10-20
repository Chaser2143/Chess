package reqRes;

import models.AuthToken;

/**
 * Serves as the request for List Games API
 */
public class ListGamesReq extends Request{

    private AuthToken authorization;

    /**
     * Constructor for List Games Request
     * @param auth is the auth token for the game
     */
    public ListGamesReq(AuthToken auth){
        authorization = auth;
    }
}
