package services;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import models.AuthToken;
import models.Game;
import reqRes.JoinGameReq;
import reqRes.JoinGameRes;
import reqRes.Response;
import server.Server;

import java.sql.Connection;
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

            Game game = GameDAOInst.getGame(request.getGameID());
            if(game == null){
                return new JoinGameRes(Response.FourHundred); //Bad Request, game doesn't exist
            }
            AuthToken A = AuthDAOInst.getAuthToken(request.getAuthorization());
            if (A != null) { //Auth is legit
                if (Objects.equals(request.getPlayerColor(), "WHITE")) {
                    //Check if that color is taken or not, if not update it
                    if(game.getWhiteUsername() == null) {
                        GameDAOInst.updateGameTeam(true, A.getUsername(), game.getGameID());
                    }
                    else{
                        return new JoinGameRes(Response.FourOThree); //Already Taken
                    }
                }
                else if (Objects.equals(request.getPlayerColor(), "BLACK")) {
                        if(game.getBlackUsername() == null){
                            GameDAOInst.updateGameTeam(false, A.getUsername(), game.getGameID());
                        }
                        else{
                            return new JoinGameRes(Response.FourOThree); //Already taken
                        }
                    }
                else if(request.getPlayerColor() == null || request.getPlayerColor().isEmpty()){ //Empty; Spectator case
                    //Empty; Spectator case, we don't actually do anything here
                }
                else {
                    return new JoinGameRes(Response.FourHundred); //Bad Request, no conditions met
                }
                //Catch all response for successful join
                connection.commit(); //Commit the transaction

                //Close the connection
                connection.close();

                //Set all the connections in the DAO's to null
                AuthDAO.getInstance().setConnection(null);
                GameDAO.getInstance().setConnection(null);
                return new JoinGameRes();
            }
            else{ //return; else error
                return new JoinGameRes(Response.FourOOne); //Unauthorized Case
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
            return new JoinGameRes(Response.FiveHundred + "There was a fatal error in joining the game.");//Error Case
        }
    }
}
