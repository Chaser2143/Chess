package webSocketMessages.serverMessages;

public class ErrorCommand extends ServerMessage {
    public String getErrorMessage() {
        return errorMessage;
    }

    private final String errorMessage;

    public ErrorCommand(String errorMessage) {
        super(ServerMessageType.ERROR);
        this.errorMessage = errorMessage;
    }
}
