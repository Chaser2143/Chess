package webSocketServer;

import chess.CGame;
import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import models.AuthToken;
import models.Game;
import reqRes.Response;
import server.Server;
import webSocketMessages.serverMessages.LoadGameCommand;
import webSocketMessages.serverMessages.NotificationCommand;
import webSocketMessages.userCommands.*;
import org.eclipse.jetty.websocket.api.Session;

import java.sql.Connection;

import static webSocketServer.WebSocketHandler.broadcastMessage;
import static webSocketServer.WebSocketHandler.sendMessage;

public class WebSocketServices {
    /**
     * Server sends a LOAD_GAME message back to the root client.
     * Server sends a Notification message to all other clients in that game informing them what color the root client is joining as.
     */
    public void joinPlayer(WebSocketSessions sessions, Session session, JoinPlayerCommand command){
        Connection connection = null;
        try {
            //Initialize a new connection
            connection = Server.DB.getConnection();

            connection.setAutoCommit(false); //Enable rollback possibility

            //Set this connection in all DAOs
            AuthDAO.getInstance().setConnection(connection);
            GameDAO.getInstance().setConnection(connection);

            //Validate by checking the game exists and the Authtoken is valid
            Game game = GameDAO.getInstance().getGame(command.getGameID());
            AuthToken AT = AuthDAO.getInstance().getAuthToken(command.getAuthString());
            if ((AT != null) && (game != null)){
                sessions.addSessionToGame(game.getGameID(), AT.getUsername(), session); //Basically is just adding you to the session list
                LoadGameCommand LGC = new LoadGameCommand(game.getGame());
                NotificationCommand NC = new NotificationCommand(AT.getUsername() + " joined the game as team " + command.getTeam());

                //Server sends a LOAD_GAME message back to the root client.
//                broadcastMessage(game.getGameID(), new Gson().toJson(LGC), AT.getUsername());//Broadcast the game out
                sendMessage(session, new Gson().toJson(LGC)); //Send the game back to root client

                //Server sends a Notification message to all other clients in that game informing them the root client joined as an observer.
                broadcastMessage(game.getGameID(), new Gson().toJson(NC), AT.getUsername());//Broadcast the notification out
            }

            connection.commit(); //Commit the transaction

            //Close the connection
            connection.close();

            //Set all the connections in the DAO's to null
            AuthDAO.getInstance().setConnection(null);
            GameDAO.getInstance().setConnection(null);

        } catch(Exception exception){
            try {
                if (connection != null) {
                    connection.rollback(); //Rollback transaction if something failed
                }
            }
            catch(Exception e) {
                //Do nothing, the next response will cover this too
                Server.logger.severe(e.getMessage());
            }
            //This would be a response with an error
            Server.logger.severe(exception.toString());
        }
    }

    /**
     * Server sends a LOAD_GAME message back to the root client.
     * Server sends a Notification message to all other clients in that game informing them the root client joined as an observer.
     */
    public void joinObserver(WebSocketSessions sessions, Session session, JoinObserverCommand command){
        Connection connection = null;
        try {
            //Initialize a new connection
            connection = Server.DB.getConnection();

            connection.setAutoCommit(false); //Enable rollback possibility

            //Set this connection in all DAOs
            AuthDAO.getInstance().setConnection(connection);
            GameDAO.getInstance().setConnection(connection);

            //Validate by checking the game exists and the Authtoken is valid
            Game game = GameDAO.getInstance().getGame(command.getGameID());
            AuthToken AT = AuthDAO.getInstance().getAuthToken(command.getAuthString());
            if ((AT != null) && (game != null)){
                sessions.addSessionToGame(game.getGameID(), AT.getUsername(), session); //Basically is just adding you to the session list
                LoadGameCommand LGC = new LoadGameCommand(game.getGame());
                NotificationCommand NC = new NotificationCommand(AT.getUsername() + " started observing the game.");

                //Server sends a LOAD_GAME message back to the root client.
//                broadcastMessage(game.getGameID(), new Gson().toJson(LGC), AT.getUsername());//Broadcast the game out
                sendMessage(session, new Gson().toJson(LGC)); //Send the game back to root client

                //Server sends a Notification message to all other clients in that game informing them the root client joined as an observer.
                broadcastMessage(game.getGameID(), new Gson().toJson(NC), AT.getUsername());//Broadcast the notification out
            }

            connection.commit(); //Commit the transaction

            //Close the connection
            connection.close();

            //Set all the connections in the DAO's to null
            AuthDAO.getInstance().setConnection(null);
            GameDAO.getInstance().setConnection(null);

        } catch(Exception exception){
            try {
                if (connection != null) {
                    connection.rollback(); //Rollback transaction if something failed
                }
            }
            catch(Exception e) {
                //Do nothing, the next response will cover this too
                Server.logger.severe(e.getMessage());
            }
            //This would be a response with an error
            Server.logger.severe(exception.toString());
        }
    }

    public void makeMove(MakeMoveCommand command){}

    public void leaveGame(LeaveCommand command){}

    public void resignGame(ResignCommand command){}
}
