package reqRes;

/**
 * Serves as the Request for the Create Game API
 */
public class CreateGameReq extends Request{
    private String authorization;
    private String gameName;

    /**
     * Instantiates a Create Game Request
     * @param authorization is the AuthToken for the new game
     * @param gameName is the name of the game
     */
    public CreateGameReq(String authorization, String gameName) {
        this.authorization = authorization;
        this.gameName = gameName;
    }

    /**
     * @return the auth token
     */
    public String getAuthorization() {
        return authorization;
    }

    /**
     * @param authorization becomes the new Auth Token
     */
    public void setAuthorization(String authorization) {
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
