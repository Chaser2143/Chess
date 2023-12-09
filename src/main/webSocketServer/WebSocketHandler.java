package webSocketServer;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.annotations.*;

import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.userCommands.*;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {
    private static WebSocketSessions sessions = new WebSocketSessions(); //this holds all the actual sessions
    private WebSocketServices services = new WebSocketServices(); //Access all my services through here

    @OnWebSocketConnect
    public void onConnect(Session session){
        //I could add sessions here if I wanted...
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String str){
    }

    @OnWebSocketError
    public void onError(Throwable throwable){
        System.out.println("Error in WSH : " + throwable.getMessage());
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String commandMSGJson) throws IOException {
        UserGameCommand UGC = new Gson().fromJson(commandMSGJson, UserGameCommand.class);
        switch (UGC.getCommandType()) {//Determine message type & Route to services
            case JOIN_PLAYER -> services.joinPlayer(sessions, session, new Gson().fromJson(commandMSGJson, JoinPlayerCommand.class));
            case JOIN_OBSERVER -> services.joinObserver(sessions, session, new Gson().fromJson(commandMSGJson, JoinObserverCommand.class));
            case RESIGN -> services.resignGame(new Gson().fromJson(commandMSGJson, ResignCommand.class));
            case LEAVE -> services.leaveGame(new Gson().fromJson(commandMSGJson, LeaveCommand.class));
            case MAKE_MOVE -> services.makeMove(new Gson().fromJson(commandMSGJson, MakeMoveCommand.class));
            default -> onError(new Exception("Unknown User Game Command Type"));
        }
    }

    /**
     * Sends a message to a specific user
     */
    public static void sendMessage(Session session, String message) throws IOException{
        session.getRemote().sendString(message);
    }

    /**
     * Broadcasts a message to everyone in a game except this user
     */
    public static void broadcastMessage(int gameID, String message, String username) throws IOException{
        //Right now this will send to everyone - Will need to fix later
        var s = sessions.getSessionsForGame(gameID);
        for (var c : s.values()){
            if(c.isOpen()) {
                sendMessage(c, message);
            }
            else{
                sessions.removeSession(c);
            }
        }
    }
}
