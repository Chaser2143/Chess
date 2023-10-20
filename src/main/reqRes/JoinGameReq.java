package reqRes;

import models.AuthToken;

/**
 * Request for Join Game API
 */
public class JoinGameReq extends Request{
    private AuthToken authorization;
    private String playerColor;
    private int gameID;

    /**
     * Constructor for Join Game Request
     * @param authorization is the auth token for the response
     * @param playerColor is the player color
     * @param gameID is the id of the game
     */
    public JoinGameReq(AuthToken authorization, String playerColor, int gameID) {
        this.authorization = authorization;
        this.playerColor = playerColor;
        this.gameID = gameID;
    }

    /**
     * @return the auth token of the request
     */
    public AuthToken getAuthorization() {
        return authorization;
    }

    /**
     * @param authorization becomes the new auth token
     */
    public void setAuthorization(AuthToken authorization) {
        this.authorization = authorization;
    }

    /**
     * @return the player color of the request
     */
    public String getPlayerColor() {
        return playerColor;
    }

    /**
     * @param playerColor becomes the new player color
     */
    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }

    /**
     * @return the game ID of the request
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * @param gameID becomes the new game ID
     */
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
