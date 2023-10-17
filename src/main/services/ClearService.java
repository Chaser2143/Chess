package services;

import com.sun.net.httpserver.Request;
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
        return new Response();
    }
}
