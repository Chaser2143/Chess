package dataAccess;

import models.AuthToken;

import java.util.HashSet;
import java.util.UUID;


/**
 * DAO used for accessing all Auth Tokens in the DB
 */
public class AuthDAO implements DAO{

    public static AuthDAO instance = new AuthDAO();

    HashSet<AuthToken> AuthDB = new HashSet<>();

    /**
     * Singleton design, allows access to a single DB
     * @return Auth DB
     */
    public static AuthDAO getInstance(){
        return instance;
    }

    /**
     * Clears all the Auth Tokens in the DB
     */
    @Override
    public void clearAll() throws DataAccessException {
        AuthDB.clear();
    }

    /**
     * Adds an AuthToken to the DB
     */
    public void addAuthToken(AuthToken authToken) throws DataAccessException{
        AuthDB.add(authToken);
    }

    /**
     * Deletes an AuthToken from the DB
     */
    public void deleteAuthToken(AuthToken authToken) throws DataAccessException{
        AuthDB.remove(authToken);
    }

    /**
     * Finds and returns an AuthToken in the DB
     */
    public AuthToken getAuthToken(String AT, String Username) throws DataAccessException{
        for(AuthToken A : AuthDB){
            if (A.getAuthToken().equals(AT) && A.getUsername().equals(Username)){
                return A;
            }
        }
        return null;
    }

    /**
     * Creates and returns a new AuthToken
     */
    public AuthToken createAuthToken(String AuthToken, String Username) throws DataAccessException{
        return new AuthToken(AuthToken,Username);
    }

    /**
     * Generates a random Auth String
     * @return String
     * @throws DataAccessException if something goes horribly wrong
     */
    public String generateAuthString() throws DataAccessException{
        return UUID.randomUUID().toString();
    }

    /**
     * Updates a given AuthToken in the DB
     */
    public void updateAuthToken(AuthToken oldAuth, AuthToken newAuth) throws DataAccessException{
        AuthDB.remove(oldAuth);
        AuthDB.add(newAuth);
    }

    /**
     * Returns all AuthTokens
     */
    public HashSet<AuthToken> getAll() throws DataAccessException{
        return AuthDB;
    }
}
