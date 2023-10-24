package dataAccess;

import models.AuthToken;
import models.User;

import java.util.HashSet;

/**
 * Data Accesser for User Model
 */
public class UserDAO implements DAO{
    public static UserDAO instance = new UserDAO();

    HashSet<User> UserDB = new HashSet<>();

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
    public void clearAll() throws DataAccessException{
        UserDB.clear();
    }

    /**
     * Adds a user to the DB
     */
    public void addUser(User user) throws DataAccessException{
        UserDB.add(user);
    }

    /**
     * Deletes a user from the DB
     */
    public void deleteUser(User user) throws DataAccessException{
        UserDB.remove(user);
    }

    /**
     * Finds and returns a user in the DB (Used for registration)
     */
    public User getUser(String Username, String Password, String Email) throws DataAccessException{
        for(User user:UserDB){
            if(user.getUsername().equals(Username) && user.getPassword().equals(Password) && user.getEmail().equals(Email)){
                return user;
            }
        }
        return null;
    }

    /**
     * Finds and returns a user in the DB (Used for Logging In)
     */
    public User getUser(String Username, String Password) throws DataAccessException{
        for(User user:UserDB){
            if(user.getUsername().equals(Username) && user.getPassword().equals(Password)){
                return user;
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
     * Updates a given user in the DB
     */
    public void updateUser(User user) throws DataAccessException{

    }

    /**
     * Returns all Users
     */
    public HashSet<User> getAll() throws DataAccessException{
        return UserDB;
    }
}
