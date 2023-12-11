package webSocketMessages.serverMessages;

public class ErrorCommand extends ServerMessage {
    public String getErrorMessage() {
        return errorMessage;
    }

    private final String errorMessage;

    public ErrorCommand(String message) {
        super(ServerMessageType.ERROR);
        this.errorMessage = message;
    }
}
