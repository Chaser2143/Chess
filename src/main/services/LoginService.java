package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import models.AuthToken;
import models.User;
import reqRes.LoginReq;
import reqRes.LoginRes;
import reqRes.Response;

/**
 * Logs in an existing user (returns a new AuthToken)
 */
public class LoginService {

    /**
     * Logs in an existing user
     * @param request from Login API
     * @return Login Response with new authtoken
     */
    public Response Login(LoginReq request){
        try{
            var UserDAOInst = UserDAO.getInstance();
            User possibleUser = UserDAOInst.getUser(request.getUsername(), request.getPassword());
            if(possibleUser != null){ //We found the user
                var AuthDAOInst = AuthDAO.getInstance(); //Access DB
                AuthToken AT = AuthDAOInst.createAuthToken(AuthDAOInst.generateAuthString(),request.getUsername()); //Make Auth Token
                AuthDAOInst.addAuthToken(AT); //Add to DB
                return new LoginRes(AT.getAuthToken(), AT.getUsername()); //Return Success Response
            }
            else{
                return new Response(Response.FourOOne); //Unauthorized Case
            }
        }
        catch(dataAccess.DataAccessException dae){
            return new Response(Response.FiveHundred + "There was a fatal error in logging in.");//Error Case
        }
    }
}
