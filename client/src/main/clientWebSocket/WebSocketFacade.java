package clientWebSocket;
import webSocketMessages.userCommands.*;

import java.net.URI;
import java.net.URISyntaxException;
import javax.websocket.*;

public class WebSocketFacade extends Endpoint implements MessageHandler.Whole<String> {

    private Session session; //Session
    private GameHandler gameHandler; //gameHandler Here

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        //empty
    }

    /**
     * Process incoming messages
     */
    @Override
    public void onMessage(String s) {
        // Deserialize message
        // Call gameHandler to process message
    }

    /**
     * Creates a valid WS Session by connecting
     */
    //Connect Method
    public void connect() throws Exception {
        URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);
    }

    //Disconnect Method

    //Outgoing messages (functions)
        //1. Create command message
        //2. Send message to server

    public void joinPlayer(String... params) throws Exception{
        var commandMSG = new JoinPlayerCommand(null, null, null, null);
        this.session.getBasicRemote().sendObject(commandMSG);
    }

    public void joinObserver(String... params){

    }

    public void makeMove(String... params){

    }

    public void leaveGame(String... params){

    }

    public void resignGame(String... params){

    }

    //sendMessage()

}
