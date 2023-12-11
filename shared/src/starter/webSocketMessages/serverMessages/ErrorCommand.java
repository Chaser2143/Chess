package webSocketMessages.serverMessages;

public class ErrorCommand extends ServerMessage {
    public String getErrorMessage() {
        return message;
    }

    private final String message;

    public ErrorCommand(String message) {
        super(ServerMessageType.ERROR);
        this.message = message;
    }
}
