package services;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import models.AuthToken;
import models.Game;
import reqRes.CreateGameRes;
import reqRes.JoinGameReq;
import reqRes.JoinGameRes;
import reqRes.Response;

import java.util.Objects;

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
        //Get Access to the DB
        var AuthDAOInst = AuthDAO.getInstance();
        var GameDAOInst = GameDAO.getInstance();
        //Check the Auth Token is in the DB; else unauthorized
        try {
            Game game = GameDAOInst.getGame(request.getGameID());
            if(game == null){
                return new JoinGameRes(Response.FourHundred); //Bad Request, game doesn't exist
            }
            AuthToken A = AuthDAOInst.getAuthToken(request.getAuthorization());
            if (A != null) { //Auth is legit
                if (Objects.equals(request.getPlayerColor(), "WHITE")) {
                    //Check if that color is taken or not, if not update it
                    if(game.getWhiteUsername() == null) {
                        game.setWhiteUsername(A.getUsername());
                        return new JoinGameRes();
                    }
                    else{
                        return new JoinGameRes(Response.FourOThree); //Already Taken
                    }
                }
                else if (Objects.equals(request.getPlayerColor(), "BLACK")) {
                        if(game.getBlackUsername() == null){
                            game.setBlackUsername(A.getUsername());
                            return new JoinGameRes();
                        }
                        else{
                            return new JoinGameRes(Response.FourOThree); //Already taken
                        }
                    }
                else if(request.getPlayerColor() == null || request.getPlayerColor().isEmpty()){ //Empty; Spectator case
                    game.addObserver(A.getUsername());
                    return new JoinGameRes();
                }
                else {
                    return new JoinGameRes(Response.FourHundred); //Bad Request, no conditions met
                }
            }
            else{ //return; else error
                return new JoinGameRes(Response.FourOOne); //Unauthorized Case
            }
        }
        catch(dataAccess.DataAccessException dae){
            return new JoinGameRes(Response.FiveHundred + "There was a fatal error in logging in.");//Error Case
        }
    }
}
