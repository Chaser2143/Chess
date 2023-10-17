package reqRes;

import models.AuthToken;

/**
 * Serves as the Request for the Create Game API
 */
public class CreateGameReq {
    private AuthToken authorization;
    private String gameName;

    /**
     * Instantiates a Create Game Request
     * @param authorization is the AuthToken for the new game
     * @param gameName is the name of the game
     */
    public CreateGameReq(AuthToken authorization, String gameName) {
        this.authorization = authorization;
        this.gameName = gameName;
    }

    /**
     * @return the auth token
     */
    public AuthToken getAuthorization() {
        return authorization;
    }

    /**
     * @param authorization becomes the new Auth Token
     */
    public void setAuthorization(AuthToken authorization) {
        this.authorization = authorization;
    }

    /**
     * @return the game name
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
}
