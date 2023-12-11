package webSocketServer;

import chess.ChessGame;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import models.AuthToken;
import models.Game;
import server.Server;
import webSocketMessages.serverMessages.ErrorCommand;
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

//                //Make sure the spot isn't already taken
//                if((command.getPlayerColor() == ChessGame.TeamColor.WHITE) && !game.getWhiteUsername().isEmpty()){ //We want white but its not empty
//                    sendMessage(session, new Gson().toJson("Error: This team spot is already taken"));
//                } else if ((command.getPlayerColor() == ChessGame.TeamColor.BLACK) && !(game.getBlackUsername().isEmpty())) {
//                    sendMessage(session, new Gson().toJson("Error: This team spot is already taken"));
//                }
//                else {
                    //Continue On
                    sessions.addSessionToGame(game.getGameID(), AT.getUsername(), session); //Basically is just adding you to the session list
                    LoadGameCommand LGC = new LoadGameCommand(game.getGame(), "Loaded Game");
                    NotificationCommand NC = new NotificationCommand(AT.getUsername() + " joined the game as team " + command.getPlayerColor());

                    //Server sends a LOAD_GAME message back to the root client.
//                broadcastMessage(game.getGameID(), new Gson().toJson(LGC), AT.getUsername());//Broadcast the game out
                    sendMessage(session, new Gson().toJson(LGC)); //Send the game back to root client

                    //Server sends a Notification message to all other clients in that game informing them the root client joined as an observer.
                    broadcastMessage(game.getGameID(), new Gson().toJson(NC), AT.getUsername());//Broadcast the notification out
//                }
            }
            else{ //Error Sent
                sendMessage(session, new Gson().toJson("Error: GameID or Auth Token not valid"));
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
                LoadGameCommand LGC = new LoadGameCommand(game.getGame(), "Loaded Game");
                NotificationCommand NC = new NotificationCommand(AT.getUsername() + " started observing the game.");

                //Server sends a LOAD_GAME message back to the root client.
//                broadcastMessage(game.getGameID(), new Gson().toJson(LGC), AT.getUsername());//Broadcast the game out
                sendMessage(session, new Gson().toJson(LGC)); //Send the game back to root client

                //Server sends a Notification message to all other clients in that game informing them the root client joined as an observer.
                broadcastMessage(game.getGameID(), new Gson().toJson(NC), AT.getUsername());//Broadcast the notification out
            }
            else{ //Error Sent
                sendMessage(session, new Gson().toJson("Error: GameID or Auth Token not valid"));
            }

            connection.commit(); //Commit the transaction

            //Close the connection
            connection.close();

            //Set all the connections in the DAO's to null
            AuthDAO.getInstance().setConnection(null);
            GameDAO.getInstance().setConnection(null);

        }
        catch(Exception exception){
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
     * Server verifies the validity of the move.
     * Game is updated to represent the move. Game is updated in the database.
     * Server sends a LOAD_GAME message to all clients in the game (including the root client) with an updated game.
     * Server sends a Notification message to all other clients in that game informing them what move was made.
     */
    public void makeMove(WebSocketSessions sessions, Session session, MakeMoveCommand command){
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
                var procGame = game.getGame();

                //Make move will already check if it is a valid move
                try {
                    procGame.makeMove(command.getMove()); //Make the move

                    GameDAO.getInstance().updateGame(game.getGameID(), procGame);//Update in DB

                    //Send out notifications
                    LoadGameCommand LGC = new LoadGameCommand(procGame, "Loaded Game");

                    NotificationCommand NC = new NotificationCommand(AT.getUsername() + " made the move " + command.getMove().toString());

                    //Server sends a LOAD_GAME message back to the root client.
                    sendMessage(session, new Gson().toJson(LGC)); //Send the game back to root client

                    //Server sends a Notification message to all other clients in that game informing them of the move
                    broadcastMessage(game.getGameID(), new Gson().toJson(NC), AT.getUsername());//Broadcast the notification out

                    //We also want to broadcast out the new board to everyone
                    broadcastMessage(game.getGameID(), new Gson().toJson(LGC), AT.getUsername());


                } catch(InvalidMoveException e){
                    sendMessage(session, new Gson().toJson(new ErrorCommand("Error: Move is not valid. Please use specific notation <startColumn> <startRow> <endColumn> <endRow> <BLANK|Promotion>")));//Notify the root user the move was not valid
                }
            }
            else{ //Error Sent
                sendMessage(session, new Gson().toJson("Error: GameID or Auth Token not valid"));
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

    public void leaveGame(LeaveCommand command){}

    public void resignGame(ResignCommand command){}
}
