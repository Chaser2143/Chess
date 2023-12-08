package webSocketMessages.serverMessages;

public class ErrorCommand extends ServerMessage {
    private final String errorMessage;

    public ErrorCommand(String errorMessage) {
        super(ServerMessageType.ERROR);
        this.errorMessage = errorMessage;
    }
}
