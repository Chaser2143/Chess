package services;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import reqRes.CreateGameReq;
import reqRes.CreateGameRes;
import reqRes.Response;
import server.Server;

import java.sql.Connection;

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

            if (AuthDAOInst.getAuthToken(request.getAuthorization()) != null) { //Auth is legit
                if (request.getGameName() == null || request.getGameName().isEmpty()) { //Bad Request, no game name
                    return new CreateGameRes(Response.FourHundred);
                }
                else{ //Create a new game!
                    int gameID = GameDAOInst.createGame(request.getGameName());

                    connection.commit(); //Commit the transaction

                    //Close the connection
                    connection.close();

                    //Set all the connections in the DAO's to null
                    AuthDAO.getInstance().setConnection(null);
                    GameDAO.getInstance().setConnection(null);

                    return new CreateGameRes(gameID);
                }
            }
            else{ //return; else error
                return new CreateGameRes(Response.FourOOne); //Unauthorized Case
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
                Server.logger.severe(exception.getMessage());
            }
            Server.logger.severe(exception.getMessage());
            return new CreateGameRes(Response.FiveHundred + "There was a fatal error in creating your game.");//Error Case
        }
    }
}
