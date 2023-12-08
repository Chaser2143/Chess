package webSocketMessages.userCommands;

/**
 * Tells the server you are leaving the game so it will stop sending you notifications.
 */
public class LeaveCommand extends UserGameCommand{
    private final Integer gameID;

    public LeaveCommand(String authToken, Integer gameID) {
        super(authToken);
        this.commandType = CommandType.LEAVE;
        this.gameID = gameID;
    }
}
