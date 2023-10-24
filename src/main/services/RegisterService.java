package services;

import dataAccess.UserDAO;
import models.User;
import reqRes.LoginReq;
import reqRes.RegisterReq;
import reqRes.RegisterRes;
import reqRes.Response;

/**
 * Registers a new User
 */
public class RegisterService {

    /**
     * Registers a new user
     * @param request from Register API
     * @return Register Response
     */
    public Response Register(RegisterReq request){
        try {
            //Create a User
            var UserDAOInst = UserDAO.getInstance();
            User possibleUser = UserDAOInst.createUser(request.getUsername(), request.getPassword(), request.getEmail());
            //Check if that user is in the DB
            if(UserDAOInst.getUser(request.getUsername(), request.getPassword(), request.getEmail()) == null){
                UserDAOInst.addUser(possibleUser); //If not, add it!
                return new LoginService().Login(new LoginReq(request.getUsername(), request.getPassword()));//Call the Login Service to log them in and get an Auth token
            } else if (request.getUsername().isEmpty() || request.getPassword().isEmpty() || request.getEmail().isEmpty()) {
                return new Response(Response.FourHundred); //Bad Request
            } else{
                return new Response(Response.FourOThree);//User already in DB Error
            }
        }
        catch(dataAccess.DataAccessException dae) {
            return new Response(Response.FiveHundred + "There was a fatal error in registering.");//Error Case
        }
    }
}
