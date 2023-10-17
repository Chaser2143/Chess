package services;

import reqRes.CreateGameReq;
import reqRes.CreateGameRes;

/**
 * Completes Create Game API Request
 */
public class CreateGameService {

    /**
     * Creates a new Game
     * @param request from Create Game API
     * @return Create Game Response
     */
    public CreateGameRes CreateGame(CreateGameReq request){
        return new CreateGameRes(0);
    }
}
