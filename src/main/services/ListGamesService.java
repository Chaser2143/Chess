package services;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import reqRes.ListGamesReq;
import reqRes.ListGamesRes;
import reqRes.LoginRes;
import reqRes.Response;
import server.Server;

import java.sql.Connection;

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
        Connection connection = null;

        try {
            //Initialize a new connection
            connection = Server.DB.getConnection();

            connection.setAutoCommit(false); //Enable rollback possibility

            //Set this connection in all DAOs
            AuthDAO.getInstance().setConnection(connection);
            GameDAO.getInstance().setConnection(connection);

            //Get Access to the DB
            var AuthDAOInst = AuthDAO.getInstance();
            var GameDAOInst = GameDAO.getInstance();
            //Check the Auth Token is in the DB; else unauthorized

            if (AuthDAOInst.getAuthToken(request.getAuthorization()) != null) {
                ListGamesRes res = new ListGamesRes();
                var allGames = GameDAOInst.getAll();//Get all games
                for(var game : allGames){ //Add it to the list formatted properly
                    res.addGame(game);
                }

                connection.commit(); //Commit the transaction

                //Close the connection
                connection.close();

                //Set all the connections in the DAO's to null
                AuthDAO.getInstance().setConnection(null);
                GameDAO.getInstance().setConnection(null);

                return res;
            }
            else{ //return; else error
                return new ListGamesRes(Response.FourOOne); //Unauthorized Case
            }
        }
        catch(Exception exception){
            try {
                if (connection != null) {
                    connection.rollback(); //Rollback transaction if something failed
                }
            }
            catch(Exception e) {
                //Do nothing, the next response will cover this too
                Server.logger.severe(e.getMessage());
            }
            Server.logger.severe(exception.getMessage());
            return new ListGamesRes(Response.FiveHundred + "There was a fatal error in listing the game.");//Error Case
        }
    }
}
