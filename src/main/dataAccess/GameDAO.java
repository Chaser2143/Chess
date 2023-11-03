package dataAccess;

import chess.CGame;
import com.google.gson.Gson;
import models.AuthToken;
import models.Game;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

/**
 * DAO used for accessing all Games in the DB
 */
public class GameDAO implements DAO{
    public static GameDAO instance = new GameDAO();

    public static int gameID = 0;

    private Connection connection = null; //Represents the current connection to the DB. Should always be null unless this DAO is currently in use.

    public void setConnection(Connection connection){
        this.connection = connection;
    }

    /**
     * Singleton design, allows access to a single DB
     * @return Game DB
     */
    public static GameDAO getInstance(){
        return instance;
    }

//    /** I believe this can be deleted
//     * Counter for gameID's
//     * @return int
//     */
//    public static int getNextID(){
//        gameID++;
//        return gameID;
//    }

    /**
     * Clears all the Games in the DB
     */
    @Override
    public void clearAll() throws DataAccessException, SQLException {
        //Delete all data from games table
        var deleteStatement = """
                DELETE FROM games""";
        try (var preparedStatement = connection.prepareStatement(deleteStatement)){
            preparedStatement.executeUpdate(); //Run the statement
        }
    }

    /**
     * Adds a Game to the DB
     */
    public void addGame(Game game) throws DataAccessException{
//        GameDB.add(game);
    }

    /**
     * Deletes a Game from the DB
     */
    public void deleteGame(Game game) throws DataAccessException{
//        GameDB.remove(game);
    }

    /**
     * Finds and returns a game in the DB
     */
    public Game getGame(int gameID) throws DataAccessException{
//        for(Game G : GameDB){
//            if (G.getGameID() == gameID){
//                return G;
//            }
//        }
        return null;
    }

    /**
     * Creates and returns a new Game
     */
    public int createGame(String gameName) throws DataAccessException, SQLException {
        int generatedPrimaryKey = 0; //0 means something went wrong
        if (gameName.matches("[a-zA-Z]+")) {

            //Create a new Chess Game
            CGame newGame = new CGame();

            //Serialize the Chess Game to put it in the DB
            var json = new Gson().toJson(newGame);

            //Insert into DB
            var statement = "INSERT INTO games (gameName, game) VALUES(?, ?)";
            try (var preparedStatement = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, gameName);
                preparedStatement.setString(2, json);
                preparedStatement.executeUpdate();

                // Get the generated primary key
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedPrimaryKey = generatedKeys.getInt(1);
                    }
                }
            }
        }
        return generatedPrimaryKey;
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
        return new HashSet<Game>();
    }
}
