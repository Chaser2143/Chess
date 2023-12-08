package webSocketMessages.serverMessages;

public class NotificationCommand extends ServerMessage{
    private final String Message;

    public NotificationCommand(String Message) {
        super(ServerMessageType.NOTIFICATION);
        this.Message = Message;
    }
}
