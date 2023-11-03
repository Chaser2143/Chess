package services;

import dataAccess.AuthDAO;
import dataAccess.UserDAO;
import models.AuthToken;
import models.User;
import reqRes.*;
import server.Server;

import java.sql.Connection;

/**
 * Completes Log Out API Request
 */
public class LogoutService {

    /**
     * Logs out the user represented by the authToken
     * @param request from Log Out API
     * @return LogOut Response
     */
    public LogoutRes Logout(LogoutReq request) {
        //Find the authtoken with that string
        //Delete it from the DB
        //If we cant find the auth, unauthorized

        Connection connection = null;

        try {
            //Initialize a new connection
            connection = Server.DB.getConnection();

            connection.setAutoCommit(false); //Enable rollback possibility

            //Set the connection
            AuthDAO.getInstance().setConnection(connection);

            var AuthDAOInst = AuthDAO.getInstance(); //Access DB

            AuthToken AT = AuthDAOInst.getAuthToken(request.getAuthorization());

            if (AT != null) { //We found the user
                AuthDAOInst.deleteAuthToken(AT); //Delete the Auth Token from the DB

                connection.commit(); //Commit the transaction

                //Close the connection
                connection.close();

                AuthDAO.getInstance().setConnection(null);

                return new LogoutRes(); //Return Success Response
            } else {
                return new LogoutRes(Response.FourOOne); //Unauthorized Case
            }
        } catch(Exception exception){
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
            return new LogoutRes(Response.FiveHundred + "There was a fatal error in logging out.");//Error Case
        }
    }
}
