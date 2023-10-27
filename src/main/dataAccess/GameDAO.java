package dataAccess;

import chess.CGame;
import models.AuthToken;
import models.Game;

import java.util.HashSet;

/**
 * DAO used for accessing all Games in the DB
 */
public class GameDAO implements DAO{
    public static GameDAO instance = new GameDAO();

    HashSet<Game> GameDB = new HashSet<>();

    public static int gameID = 0;

    /**
     * Singleton design, allows access to a single DB
     * @return Game DB
     */
    public static GameDAO getInstance(){
        return instance;
    }

    /**
     * Counter for gameID's
     * @return int
     */
    public static int getNextID(){
        gameID++;
        return gameID;
    }

    /**
     * Clears all the Games in the DB
     */
    @Override
    public void clearAll() throws DataAccessException{
        GameDB.clear();
    }

    /**
     * Adds a Game to the DB
     */
    public void addGame(Game game) throws DataAccessException{
        GameDB.add(game);
    }

    /**
     * Deletes a Game from the DB
     */
    public void deleteGame(Game game) throws DataAccessException{
        GameDB.remove(game);
    }

    /**
     * Finds and returns a game in the DB
     */
    public Game getGame(int gameID) throws DataAccessException{
        for(Game G : GameDB){
            if (G.getGameID() == gameID){
                return G;
            }
        }
        return null;
    }

    /**
     * Creates and returns a new Game
     */
    public int createGame(String gameName) throws DataAccessException{
        Game newGame = new Game(GameDAO.getNextID(),"","", gameName);
        GameDB.add(newGame);
        return newGame.getGameID();
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
