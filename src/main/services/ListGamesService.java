package services;

import reqRes.ListGamesReq;
import reqRes.ListGamesRes;

/**
 * Completes List Games API Request
 */
public class ListGamesService {

    /**
     * Gives a list of all games
     * @param request from List Game API
     * @return List Game Response
     */
    public ListGamesRes ListGames(ListGamesReq request){
        return new ListGamesRes();
    }
}
