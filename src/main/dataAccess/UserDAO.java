package dataAccess;

import models.User;

import java.util.HashSet;

public class UserDAO implements DAO{

    HashSet<User> UserDB = new HashSet<>();

    /**
     * Clears all the Users in the DB
     */
    @Override
    public void clearAll() throws DataAccessException{

    }

    /**
     * Adds a user to the DB
     */
    public void addUser(User user) throws DataAccessException{

    }

    /**
     * Deletes a user from the DB
     */
    public void deleteUser(User user) throws DataAccessException{

    }

    /**
     * Finds and returns a user in the DB
     */
    public User getUser() throws DataAccessException{
        return new User("","","");
    }

    /**
     * Creates and returns a new user
     */
    public User createUser() throws DataAccessException{
        return new User("","","");
    }

    /**
     * Updates a given user in the DB
     */
    public void updateUser(User user) throws DataAccessException{

    }
}
