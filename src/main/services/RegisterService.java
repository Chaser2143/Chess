package services;

import dataAccess.UserDAO;
import models.User;
import reqRes.*;

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
        try {
            //Are all our fields filled out?
            if (request.getUsername().isEmpty() || request.getPassword().isEmpty() || request.getEmail().isEmpty()) {
                return new RegisterRes(Response.FourHundred); //Bad Request
            }

            //Create a User
            var UserDAOInst = UserDAO.getInstance();
            User possibleUser = UserDAOInst.createUser(request.getUsername(), request.getPassword(), request.getEmail());

            //Check if that user is in the DB
            if(UserDAOInst.getUser(request.getUsername(), request.getPassword(), request.getEmail()) == null){
                UserDAOInst.addUser(possibleUser); //If not, add it!
                LoginRes LS = new LoginService().Login(new LoginReq(request.getUsername(), request.getPassword()));//Call the Login Service to log them in and get an Auth token
                return new RegisterRes(LS.getUsername(), LS.getAuthToken());
            }
            else{
                return new RegisterRes(Response.FourOThree);//User already in DB Error
            }
        }
        catch(dataAccess.DataAccessException dae) {
            return new RegisterRes(Response.FiveHundred + "There was a fatal error in registering.");//Error Case
        }
    }
}
