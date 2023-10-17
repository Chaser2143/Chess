package services;

import reqRes.JoinGameReq;
import reqRes.JoinGameRes;

/**
 * Completes Join Game API Request
 */
public class JoinGameService {

    /**
     * Verifies that the specified game exists, and,
     * if a color is specified, adds the caller as the requested color to the game.
     * If no color is specified the user is joined as an observer.
     * This request is idempotent.
     * @param request from Join Game API
     * @return Join Game Response
     */
    public JoinGameRes JoinGame(JoinGameReq request){
        return new JoinGameRes();
    }
}
