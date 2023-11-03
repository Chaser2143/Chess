package services;

import com.sun.net.httpserver.Request;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import reqRes.Response;
import server.Server;

import java.sql.Connection;

/**
 * Service used to clear the database.
 * Removes all users, games, and authtokens
 */
public class ClearService {

    /**
     * Actually clears everything from the DB
     */
    public reqRes.Response clear(){
        Connection connection = null;

        try {
            //Initialize a new connection
            connection = Server.DB.getConnection();

            connection.setAutoCommit(false); //Enable rollback possibility

            //Set this connection in all DAOs
            AuthDAO.getInstance().setConnection(connection);
            GameDAO.getInstance().setConnection(connection);
            UserDAO.getInstance().setConnection(connection);

            //Clear
            AuthDAO.getInstance().clearAll();
            GameDAO.getInstance().clearAll();
            UserDAO.getInstance().clearAll();

            connection.commit(); //Commit the transaction

            //Close the connection
            connection.close();

            //Set all the connections in the DAO's to null
            AuthDAO.getInstance().setConnection(null);
            GameDAO.getInstance().setConnection(null);
            UserDAO.getInstance().setConnection(null);
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
            //This would be a response with an error
            Server.logger.severe(exception.toString());
            return new Response(Response.FiveHundred + "There was a fatal error in clearing the data.");
        }
        //Return
        Server.logger.fine("Successfully served clear request");
        return new Response();
    }
}
