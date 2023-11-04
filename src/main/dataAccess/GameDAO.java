package dataAccess;

import chess.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.AuthToken;
import models.Game;
import models.User;

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
     * Finds and returns a game in the DB
     */
    public Game getGame(int gameID) throws DataAccessException, SQLException { //TODO Needs checked
        //Retrieves a game from the DB
        var retrieveStatement = """
        SELECT *
            FROM games
            WHERE gameid=?""";
        try (var preparedStatement = connection.prepareStatement(retrieveStatement)){
            preparedStatement.setInt(1, gameID);
            try(var rs = preparedStatement.executeQuery()) { //Run the statement
                if (rs.next()) {
                    var gameId = rs.getInt("gameid");
                    var whiteUserName = rs.getString("whiteUsername");
                    var blackUserName = rs.getString("blackUsername");
                    var gameName = rs.getString("gameName");
                    var gameJSON = rs.getString("game");
                    var builder = new GsonBuilder();
                    builder.registerTypeAdapter(CGame.class, new ChessGameAdapter());
                    var game = builder.create().fromJson(gameJSON, CGame.class);
                    return new Game(gameId, whiteUserName, blackUserName, gameName, game);
                }
            }
        }
        return null;
    }

    /**
     * Updates a given team (joins a user to a game in the DB)
     * @param username is the username to join
     * @param isWhite is whether to join to the white team or not
     */
    public void updateGameTeam(boolean isWhite, String username, int gameID) throws SQLException {
        //Joins a user to a game
        String statement;
        if (isWhite) { //White username
            statement = """
                    UPDATE games
                        SET whiteUsername=?
                        WHERE gameid=?""";
        } else { //Black Username
            statement = """
                    UPDATE games
                        SET blackUsername=?
                        WHERE gameid=?""";
        }
        try (var preparedStatement = connection.prepareStatement(statement)) {
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, gameID);
            preparedStatement.executeUpdate(); //Run the statement
        }
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
     * Returns all games
     */
    public HashSet<Game> getAll() throws DataAccessException, SQLException {
        HashSet<Game> allGames = new HashSet<Game>();
        //Retrieves all games from the DB
        var statement = """
        SELECT * FROM games""";
        try (var preparedStatement = connection.prepareStatement(statement)){
            try(var rs = preparedStatement.executeQuery()) { //Run the statement
                while(rs.next()) {
                    var gameID = rs.getInt("gameid");
                    var whiteUserName = rs.getString("whiteUsername");
                    var blackUserName = rs.getString("blackUsername");
                    var gameName = rs.getString("gameName");
                    var gameJSON = rs.getString("game");
                    var builder = new GsonBuilder();
                    builder.registerTypeAdapter(CGame.class, new ChessGameAdapter());
                    var game = builder.create().fromJson(gameJSON, CGame.class);
                    Game existingGame = new Game(gameID, whiteUserName, blackUserName, gameName, game);
                    allGames.add(existingGame);
                }
            }
        }
        return allGames;
    }
}
