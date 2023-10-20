package services;

import com.sun.net.httpserver.Request;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import reqRes.Response;

/**
 * Service used to clear the database.
 * Removes all users, games, and authtokens
 */
public class ClearService {

    /**
     * Actually clears everything from the DB
     */
    public Response clear(){
        try {
            AuthDAO.getInstance().clearAll();
            GameDAO.getInstance().clearAll();
            UserDAO.getInstance().clearAll();
        }
        catch(dataAccess.DataAccessException dae){
            //This would be a response with an error
            return new Response(Response.FiveHundred + "There was a fatal error in clearing the data.");
        }
        return new Response();
    }
}
