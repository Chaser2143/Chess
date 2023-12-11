package webSocketMessages.serverMessages;

public class NotificationCommand extends ServerMessage{
    private final String message;

    public String getMessage() {
        return message;
    }

    public NotificationCommand(String message) {
        super(ServerMessageType.NOTIFICATION);
        this.message = message;
    }
}
