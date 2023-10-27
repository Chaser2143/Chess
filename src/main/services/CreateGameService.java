package services;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import reqRes.CreateGameReq;
import reqRes.CreateGameRes;
import reqRes.Response;

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
        //Get Access to the DB
        var AuthDAOInst = AuthDAO.getInstance();
        var GameDAOInst = GameDAO.getInstance();
        //Check the Auth Token is in the DB; else unauthorized
        try {
            if (AuthDAOInst.getAuthToken(request.getAuthorization()) != null) { //Auth is legit
                if (request.getGameName() == null || request.getGameName().isEmpty()) { //Bad Request, no game name
                    return new CreateGameRes(Response.FourHundred);
                }
                else{ //Create a new game!
                    int gameID = GameDAOInst.createGame(request.getGameName());
                    return new CreateGameRes(gameID);
                }
            }
            else{ //return; else error
                return new CreateGameRes(Response.FourOOne); //Unauthorized Case
            }
        }
        catch(dataAccess.DataAccessException dae){
            return new CreateGameRes(Response.FiveHundred + "There was a fatal error in logging in.");//Error Case
        }
    }
}
