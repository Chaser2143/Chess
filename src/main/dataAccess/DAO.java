package dataAccess;

import java.sql.SQLException;

/**
 * Interface for general DAO Classes
 */
public interface DAO {

    /**
     * Used for clearing all the given model in the DB
     */
    public void clearAll() throws DataAccessException, SQLException;
}
