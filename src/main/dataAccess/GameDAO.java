package dataAccess;

import chess.CGame;
import models.AuthToken;
import models.Game;

import java.util.HashSet;

/**
 * DAO used for accessing all Games in the DB
 */
public class GameDAO implements DAO{

    HashSet<Game> GameDB = new HashSet<>();

    /**
     * Clears all the Games in the DB
     */
    @Override
    public void clearAll() throws DataAccessException{

    }

    /**
     * Adds a Game to the DB
     */
    public void addGame(Game game) throws DataAccessException{

    }

    /**
     * Deletes a Game from the DB
     */
    public void deleteGame(Game game) throws DataAccessException{

    }

    /**
     * Finds and returns a game in the DB
     */
    public Game getGame() throws DataAccessException{
        return new Game(0,"","", "", new CGame());
    }

    /**
     * Creates and returns a new Game
     */
    public Game createGame() throws DataAccessException{
        return new Game(0,"","", "", new CGame());
    }

    /**
     * Updates a given game in the DB
     */
    public void updateGame(Game game) throws DataAccessException{

    }

    /**
     * Returns all games
     */
    public HashSet<Game> getAll() throws DataAccessException{
        return GameDB;
    }
}
