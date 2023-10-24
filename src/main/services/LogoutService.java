package services;

import dataAccess.AuthDAO;
import dataAccess.UserDAO;
import models.AuthToken;
import models.User;
import reqRes.*;

/**
 * Completes Log Out API Request
 */
public class LogoutService {

    /**
     * Logs out the user represented by the authToken
     * @param request from Log Out API
     * @return LogOut Response
     */
    public Response Logout(LogoutReq request) {
        //Find the authtoken with that string
        //Delete it from the DB
        //If we cant find the auth, unauthorized
        try {
            var AuthDAOInst = AuthDAO.getInstance(); //Access DB
            AuthToken AT = AuthDAOInst.getAuthToken(request.getAuthorization());
            if (AT != null) { //We found the user
                AuthDAOInst.deleteAuthToken(AT); //Delete the Auth Token from the DB
                return new Response(); //Return Success Response
            } else {
                return new Response(Response.FourOOne); //Unauthorized Case
            }
        } catch (dataAccess.DataAccessException dae) {
            return new Response(Response.FiveHundred + "There was a fatal error in logging out.");//Error Case
        }
    }
}
