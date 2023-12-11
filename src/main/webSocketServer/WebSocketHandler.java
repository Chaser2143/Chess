package webSocketServer;
import chess.ChessBoard;
import chess.ChessBoardAdapter;
import chess.ChessMove;
import chess.ChessMoveAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.websocket.api.annotations.*;

import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.LoadGameCommand;
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
        System.out.println("Error in WSH : " + throwable.getMessage() + " " + throwable.getStackTrace());
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String commandMSGJson) throws IOException {
        UserGameCommand UGC = new Gson().fromJson(commandMSGJson, UserGameCommand.class);
        switch (UGC.getCommandType()) {//Determine message type & Route to services
            case JOIN_PLAYER -> services.joinPlayer(sessions, session, new Gson().fromJson(commandMSGJson, JoinPlayerCommand.class));
            case JOIN_OBSERVER -> services.joinObserver(sessions, session, new Gson().fromJson(commandMSGJson, JoinObserverCommand.class));
            case RESIGN -> services.resignGame(sessions, session, new Gson().fromJson(commandMSGJson, ResignCommand.class));
            case LEAVE -> services.leaveGame(sessions, session, new Gson().fromJson(commandMSGJson, LeaveCommand.class));
            case MAKE_MOVE -> makeMoveDeserializer(sessions, session, commandMSGJson);
            default -> onError(new Exception("Unknown User Game Command Type"));
        }
    }

    private void makeMoveDeserializer(WebSocketSessions sessions, Session session, String rawJson){
        var builder = new GsonBuilder();
        builder.registerTypeAdapter(ChessMove.class, new ChessMoveAdapter());
        MakeMoveCommand MMC = builder.create().fromJson(rawJson, MakeMoveCommand.class);
        services.makeMove(sessions, session, MMC);
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
        for(var c : s.keySet()){ //Iterate through the keys
            if(s.get(c).isOpen()) {
                if(!c.equals(username)) {
                    sendMessage(s.get(c), message);
                }
            }
            else{
                sessions.removeSession(s.get(c));
            }
        }
    }
}
