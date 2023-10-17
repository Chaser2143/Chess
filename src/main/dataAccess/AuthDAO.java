package dataAccess;

import models.AuthToken;

import java.util.HashSet;


/**
 * DAO used for accessing all Auth Tokens in the DB
 */
public class AuthDAO implements DAO{

    HashSet<AuthToken> AuthDB = new HashSet<>();

    /**
     * Clears all the Auth Tokens in the DB
     */
    @Override
    public void clearAll() throws DataAccessException {

    }

    /**
     * Adds an AuthToken to the DB
     */
    public void addAuthToken(AuthToken authToken) throws DataAccessException{

    }

    /**
     * Deletes an AuthToken from the DB
     */
    public void deleteAuthToken(AuthToken authToken) throws DataAccessException{

    }

    /**
     * Finds and returns an AuthToken in the DB
     */
    public AuthToken getAuthToken() throws DataAccessException{
        return new AuthToken("","");
    }

    /**
     * Creates and returns a new AuthToken
     */
    public AuthToken createAuthToken() throws DataAccessException{
        return new AuthToken("","");
    }

    /**
     * Updates a given AuthToken in the DB
     */
    public void updateAuthToken(AuthToken auth) throws DataAccessException{

    }
}
