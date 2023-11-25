package reqRes;

/**
 * Serves as the Response for the Create Game API
 */
public class CreateGameRes extends Response{
    private Integer gameID = null;

    /**
     * Instantiates a new create game response
     * @param gameID is the id of the game
     */
    public CreateGameRes(int gameID) {
        this.gameID = gameID;
    }

    /**
     * Error Create Game Response
     * @param message Error
     */
    public CreateGameRes(String message){
        super(message);
    }

    /**
     * Gets the game ID
     * @return int
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * Sets the game id
     * @param gameID becomes the new Game ID
     */
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
