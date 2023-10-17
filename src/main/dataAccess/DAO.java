package dataAccess;

import models.AuthToken;
import reqRes.Response;

import java.util.ArrayList;

/**
 * Interface for general DAO Classes
 */
public interface DAO {

    /**
     * Used for clearing all the given model in the DB
     */
    public void clearAll() throws DataAccessException;
}
