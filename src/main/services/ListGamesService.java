package services;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import reqRes.ListGamesReq;
import reqRes.ListGamesRes;
import reqRes.LoginRes;
import reqRes.Response;

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
        //Get Access to the DB
        var AuthDAOInst = AuthDAO.getInstance();
        var GameDAOInst = GameDAO.getInstance();
        //Check the Auth Token is in the DB; else unauthorized
        try {
            if (AuthDAOInst.getAuthToken(request.getAuthorization()) != null) {
                ListGamesRes res = new ListGamesRes();
                var allGames = GameDAOInst.getAll();//Get all games
                for(var game : allGames){ //Add it to the list formatted properly
                    res.addGame(game);
                }
                return res;
            }
            else{ //return; else error
                return new ListGamesRes(Response.FourOOne); //Unauthorized Case
            }
        }
        catch(dataAccess.DataAccessException dae){
            return new ListGamesRes(Response.FiveHundred + "There was a fatal error in logging in.");//Error Case
        }
    }
}
