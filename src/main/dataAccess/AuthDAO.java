package dataAccess;

import models.AuthToken;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.UUID;


/**
 * DAO used for accessing all Auth Tokens in the DB
 */
public class AuthDAO implements DAO{

    public static AuthDAO instance = new AuthDAO();

    private Connection connection = null; //Represents the current connection to the DB. Should always be null unless this DAO is currently in use.

    public void setConnection(Connection connection){
        this.connection = connection;
    }

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
    public void clearAll() throws DataAccessException, SQLException {
        //Delete all data from authtokens table
        var deleteStatement = """
                DELETE FROM authtokens""";
        try (var preparedStatement = connection.prepareStatement(deleteStatement)){
            preparedStatement.executeUpdate(); //Run the statement
        }
    }

    /**
     * Adds an AuthToken to the DB
     */
    public void addAuthToken(AuthToken authToken) throws DataAccessException, SQLException {
        //Inserts an authtoken into the DB
        var statement = """
        INSERT INTO authtokens (username, authtoken)
        VALUES(?,?)""";
        try (var preparedStatement = connection.prepareStatement(statement)){
            preparedStatement.setString(1, authToken.getUsername());
            preparedStatement.setString(2, authToken.getAuthToken());
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Deletes an AuthToken from the DB
     */
    public void deleteAuthToken(AuthToken authToken) throws DataAccessException, SQLException {
        //Deletes an AuthToken from the DB
        var statement = """
        DELETE FROM authtokens
            WHERE authtoken=? AND username=?""";
        try (var preparedStatement = connection.prepareStatement(statement)){
            preparedStatement.setString(1, authToken.getAuthToken());
            preparedStatement.setString(2, authToken.getUsername()); //I think this is unnecessary cause auth tokens are a primary key... but oh well, better safe than sorry
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Finds and returns an AuthToken in the DB
     */
    public AuthToken getAuthToken(String AT) throws DataAccessException, SQLException {
        if(AT.equals("none")){
            return null;
        }
        //Retrieves an AuthToken from the DB
        var retrieveStatement = """
        SELECT username, authtoken
            FROM authtokens
            WHERE authtoken=?""";
        try (var preparedStatement = connection.prepareStatement(retrieveStatement)){
            preparedStatement.setString(1, AT);
            try(var rs = preparedStatement.executeQuery()) { //Run the statement
                while (rs.next()) {
                    return new AuthToken(rs.getString("authtoken"), rs.getString("username"));
                }
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
     * Returns all AuthTokens
     */
    public HashSet<AuthToken> getAll() throws DataAccessException, SQLException {
        HashSet<AuthToken> allTokens = new HashSet<AuthToken>();
        //Retrieves all games from the DB
        var statement = """
        SELECT * FROM authtokens""";
        try (var preparedStatement = connection.prepareStatement(statement)){
            try(var rs = preparedStatement.executeQuery()) { //Run the statement
                while(rs.next()) {
                    var username = rs.getString("username");
                    var authtoken = rs.getString("authtoken");
                    allTokens.add(new AuthToken(authtoken, username));
                }
            }
        }
        return allTokens;
    }
}
