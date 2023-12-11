package models;

import chess.CGame;

/**
 * Represents the Game Model in the DB
 */
public class Game {
    private int gameID;
    private String whiteUsername;
    private String blackUsername;
    private String gameName;
    private CGame game;
    private boolean isOver = false; //Marks if the game is over or not

    /**
     * Constructor for the game
     * @param gameID is the game ID
     * @param whiteUsername is the White Team username
     * @param blackUsername is the Black Team username
     * @param gameName is the name of the game
     */
    public Game(int gameID, String whiteUsername, String blackUsername, String gameName) {
        this.gameID = gameID;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
        this.game = new CGame();
    }

    /**
     * Returns a bool of if the game is over or not
     */
    public boolean gameStatus(){
        return isOver;
    }

    /**
     * Change the status of the game
     */
    public void setOver(boolean status){
        this.isOver = status;
    }

    /**
     * Constructor for the game
     * @param gameID is the game ID
     * @param whiteUsername is the White Team username
     * @param blackUsername is the Black Team username
     * @param gameName is the name of the game
     */
    public Game(int gameID, String whiteUsername, String blackUsername, String gameName, CGame chessGame) {
        this.gameID = gameID;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
        this.game = chessGame;
    }

    /**
     * @return the game ID
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * @param gameID is set as the new gameID
     */
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    /**
     * @return the white username
     */
    public String getWhiteUsername() {
        return whiteUsername;
    }

    /**
     * @param whiteUsername is set as the new white username
     */
    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    /**
     * @return the black username
     */
    public String getBlackUsername() {
        return blackUsername;
    }

    /**
     * @param blackUsername is set as the new black username
     */
    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    /**
     * Returns the game Name
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * @param gameName becomes the new game name
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * @return the game
     */
    public CGame getGame() {
        return game;
    }

    /**
     * @param game is set as the new game
     */
    public void setGame(CGame game) {
        this.game = game;
    }
}
