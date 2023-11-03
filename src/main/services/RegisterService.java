package services;

import dataAccess.UserDAO;
import models.User;
import reqRes.*;
import server.Server;

import java.sql.Connection;

/**
 * Registers a new User
 */
public class RegisterService {

    /**
     * Registers a new user
     * @param request from Register API
     * @return Register Response
     */
    public RegisterRes Register(RegisterReq request){
        Connection connection = null;

        try {
            //Are all our fields filled out?
            if ((request.getUsername()==null || request.getPassword()==null || request.getEmail()==null) || (request.getUsername().isEmpty() || request.getPassword().isEmpty() || request.getEmail().isEmpty())) {
                return new RegisterRes(Response.FourHundred); //Bad Request
            }

            //Initialize a new connection
            connection = Server.DB.getConnection();

            connection.setAutoCommit(false); //Enable rollback possibility

            //Set the connection
            UserDAO.getInstance().setConnection(connection);

            //Create a User
            var UserDAOInst = UserDAO.getInstance();
            User possibleUser = UserDAOInst.createUser(request.getUsername(), request.getPassword(), request.getEmail());

            //Check if that user is in the DB
            if(UserDAOInst.getUser(request.getUsername(), request.getPassword(), request.getEmail()) == null){
                UserDAOInst.addUser(possibleUser); //If not, add it!

                //Manage all my connection stuff
                connection.commit(); //Commit the transaction

                //Close the connection
                connection.close();

                UserDAO.getInstance().setConnection(null);

                //Now log in
                LoginRes LS = new LoginService().Login(new LoginReq(request.getUsername(), request.getPassword()));//Call the Login Service to log them in and get an Auth token

                return new RegisterRes(LS.getUsername(), LS.getAuthToken());
            }
            else{
                return new RegisterRes(Response.FourOThree);//User already in DB Error
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
            return new RegisterRes(Response.FiveHundred + "There was a fatal error in registering.");//Error Case
        }
    }
}
