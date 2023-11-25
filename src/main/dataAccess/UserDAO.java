package dataAccess;

import models.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;

/**
 * Data Accesser for User Model
 */
public class UserDAO implements DAO{
    public static UserDAO instance = new UserDAO();

    private Connection connection = null; //Represents the current connection to the DB. Should always be null unless this DAO is currently in use.

    public void setConnection(Connection connection){
        this.connection = connection;
    }

    /**
     * Singleton design, allows access to a single DB
     * @return User DB
     */
    public static UserDAO getInstance(){
        return instance;
    }

    /**
     * Clears all the Users in the DB
     */
    @Override
    public void clearAll() throws DataAccessException, SQLException {
        //Delete all data from users table
        var deleteStatement = """
                DELETE FROM users""";
        try (var preparedStatement = connection.prepareStatement(deleteStatement)){
            preparedStatement.executeUpdate(); //Run the statement
        }
    }

    /**
     * Adds a user to the DB
     */
    public void addUser(User user) throws DataAccessException, SQLException {
        //Inserts a user into the DB
        var statement = """
        INSERT INTO users (username, password, email)
        VALUES(?,?,?)""";
        try (var preparedStatement = connection.prepareStatement(statement)){
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Finds and returns a user in the DB (Used for registration)
     */
    public User getUser(String Username, String Password, String Email) throws DataAccessException, SQLException {
        //Retrieves a user from the DB
        var statement = """
        SELECT username, password, email
            FROM users
            WHERE username=? AND password=? AND email=?""";
        try (var preparedStatement = connection.prepareStatement(statement)){
            preparedStatement.setString(1, Username);
            preparedStatement.setString(2, Password);
            preparedStatement.setString(3, Email);
            try(var rs = preparedStatement.executeQuery()) { //Run the statement
                while(rs.next()) {
                    return new User(rs.getString("username"), rs.getString("password"), rs.getString("email"));
                }
            }
        }
        return null;
    }

    /**
     * Finds and returns a user in the DB (Used for Logging In)
     */
    public User getUser(String Username, String Password) throws DataAccessException, SQLException {
        if(Username.equals("none") || Password.equals("none")){
            return null;
        }
        //Retrieves a user from the DB
        var statement = """
        SELECT username, password, email
            FROM users
            WHERE username=? AND password=?""";
        try (var preparedStatement = connection.prepareStatement(statement)){
            preparedStatement.setString(1, Username);
            preparedStatement.setString(2, Password);
            try(var rs = preparedStatement.executeQuery()) { //Run the statement
                while(rs.next()) {
                    return new User(rs.getString("username"), rs.getString("password"), rs.getString("email"));
                }
            }
        }
        return null;
    }

    /**
     * Creates and returns a new user
     */
    public User createUser(String Username, String Password, String Email) throws DataAccessException{
        return new User(Username,Password,Email);
    }

    /**
     * Returns all Users
     */
    public HashSet<User> getAll() throws DataAccessException, SQLException {
        HashSet<User> allUsers = new HashSet<User>();
        //Retrieves all games from the DB
        var statement = """
        SELECT * FROM users""";
        try (var preparedStatement = connection.prepareStatement(statement)){
            try(var rs = preparedStatement.executeQuery()) { //Run the statement
                while(rs.next()) {
                    var username = rs.getString("username");
                    var password = rs.getString("password");
                    var email = rs.getString("email");
                    allUsers.add(new User(username, password, email));
                }
            }
        }
        return allUsers;
    }
}
