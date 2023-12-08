package webSocketMessages.userCommands;

/**
 * Forfeits the match and ends the game (no more moves can be made).
 */
public class ResignCommand extends UserGameCommand{
    private final Integer gameID;

    public ResignCommand(String authToken, Integer gameID) {
        super(authToken);
        this.commandType = CommandType.RESIGN;
        this.gameID = gameID;
    }
}
