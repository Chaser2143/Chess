package services;

import dataAccess.AuthDAO;
import dataAccess.UserDAO;
import models.AuthToken;
import models.User;
import reqRes.LoginReq;
import reqRes.LoginRes;
import reqRes.Response;
import server.Server;

import java.sql.Connection;

/**
 * Logs in an existing user (returns a new AuthToken)
 */
public class LoginService {

    /**
     * Logs in an existing user
     * @param request from Login API
     * @return Login Response with new authtoken
     */
    public LoginRes Login(LoginReq request){
        Connection connection = null;

        try{
            //Initialize a new connection
            connection = Server.DB.getConnection();

            connection.setAutoCommit(false); //Enable rollback possibility

            //Set the connection
            UserDAO.getInstance().setConnection(connection);

            var UserDAOInst = UserDAO.getInstance();
            User possibleUser = UserDAOInst.getUser(request.getUsername(), request.getPassword());
            if(possibleUser != null){ //We found the user

                //Set the connection
                AuthDAO.getInstance().setConnection(connection);

                var AuthDAOInst = AuthDAO.getInstance(); //Access DB
                AuthToken AT = AuthDAOInst.createAuthToken(AuthDAOInst.generateAuthString(),request.getUsername()); //Make Auth Token
                AuthDAOInst.addAuthToken(AT); //Add to DB

                connection.commit(); //Commit the transaction

                //Close the connection
                connection.close();

                UserDAO.getInstance().setConnection(null);
                AuthDAO.getInstance().setConnection(null);

                return new LoginRes(AT.getAuthToken(), AT.getUsername()); //Return Success Response
            }
            else{
                return new LoginRes(Response.FourOOne); //Unauthorized Case
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
            return new LoginRes(Response.FiveHundred + "There was a fatal error in logging in.");//Error Case
        }
    }
}
