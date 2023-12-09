package clientWebSocket;
import chess.ChessBoard;
import chess.ChessBoardAdapter;
import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exception.ResponseException;
import webSocketMessages.serverMessages.ErrorCommand;
import webSocketMessages.serverMessages.LoadGameCommand;
import webSocketMessages.serverMessages.NotificationCommand;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.websocket.*;


public class WebSocketFacade extends Endpoint {

    private Session session; //Session
    private GameHandler gameHandler; //gameHandler Here

    public WebSocketFacade(GameHandler gameHandler) throws ResponseException{
        try {
            URI uri = new URI("ws://localhost:8080/connect");
            this.gameHandler = gameHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, uri);

            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message){
                    ServerMessage SM = new Gson().fromJson(message, ServerMessage.class);
                    switch(SM.getServerMessageType()){
                        case LOAD_GAME -> loadGameHandler(message);
                        case NOTIFICATION -> notificationHandler(new Gson().fromJson(message, NotificationCommand.class));
                        case ERROR -> errorHandler(new Gson().fromJson(message, ErrorCommand.class));
                    }
                }
            });
        }
        catch (DeploymentException | IOException | URISyntaxException ex){
            throw new ResponseException(500, ex.getMessage());
        }
    }

    /**
     * Intermediate for passing an error through
     */
    public void errorHandler(ErrorCommand EC){
        gameHandler.printMessage("Error : " + EC.getErrorMessage());
    }

    /**
     * Intermediate for passing a notification through
     */
    public void notificationHandler(NotificationCommand NC){
        gameHandler.printMessage(NC.getMessage());
    }

    /**
     * Just used as an intermediate step to pass the game through (Deserializing)
     */
    public void loadGameHandler(String LGCJson){
        var builder = new GsonBuilder();
        builder.registerTypeAdapter(ChessBoard.class, new ChessBoardAdapter());
        LoadGameCommand LGC = builder.create().fromJson(LGCJson, LoadGameCommand.class);
        gameHandler.updateGame(LGC.getGame());
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) { //Leave empty
    }

    //Disconnect Method?
    public void disconnect() throws ResponseException {
        try {
            this.session.close();
        } catch (IOException ex){
            throw new ResponseException(500, ex.getMessage());
        }
    }

    //Outgoing messages (functions)
        //1. Create command message
        //2. Send message to server

    public void sendStringTest() throws ResponseException {
        sendMessage("Hello World!");
    }

    public void joinPlayer(String authToken, Integer gameID, ChessGame.TeamColor team) throws ResponseException{
        var commandMSG = new JoinPlayerCommand(authToken, gameID, team); //1. Create command message
        //Serialize this and then send as message (Do as String first)
        sendMessage(new Gson().toJson(commandMSG)); //2. Send message to server
    }

    public void joinObserver(String authToken, Integer gameID) throws ResponseException{
        var commandMSG = new JoinObserverCommand(authToken, gameID); //1. Create command message
        sendMessage(new Gson().toJson(commandMSG)); //2. Send message to server
    }

    public void makeMove(String... params){

    }

    public void leaveGame(String... params){

    }

    public void resignGame(String... params){

    }

    public void sendMessage(String str) throws ResponseException{
        try {
            this.session.getBasicRemote().sendText(str);
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

}
