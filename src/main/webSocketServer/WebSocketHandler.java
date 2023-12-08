package webSocketServer;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;

import org.eclipse.jetty.websocket.api.Session;

public class WebSocketHandler {
    private WebSocketSessions sessions; //this holds all the actual sessions

    @OnWebSocketConnect
    public void onConnect(Session session){
        //TODO
    }

    @OnWebSocketClose
    public void onClose(Session session){
        //TODO
    }

    @OnWebSocketError
    public void onError(Throwable throwable){
        //TODO
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String str){
        //Determine message type
        //Call one of the service methods to manage
    }

    /**
     * Sends a message to a specific user
     */
    public void sendMessage(int gameID, String message, String username){

    }

    /**
     * Broadcasts a message to everyone in a game except this user
     */
    public void broadcastMessage(int gameID, String message, String username){

    }
}
