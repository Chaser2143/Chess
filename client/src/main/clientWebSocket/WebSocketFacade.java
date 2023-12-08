package clientWebSocket;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

public class WebSocketFacade extends Endpoint implements MessageHandler.Whole<String> {

    //Session
    //gameHandler Here

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        //empty?
    }

    /**
     * Process incoming messages
     */
    @Override
    public void onMessage(String s) {
        // Deserialize message
        // Call gameHandler to process message
    }

    //Connect Method

    //Disconnect Method

    //Outgoing messages (functions)
        //1. Create command message
        //2. Send message to server

    public void joinPlayer(String... params){

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
