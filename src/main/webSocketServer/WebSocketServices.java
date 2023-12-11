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
import java.util.Objects;

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
                if((command.getPlayerColor() == ChessGame.TeamColor.WHITE) && !(AT.getUsername().equals(game.getWhiteUsername()))){ //We want white and the http server didnt give it to us
                    sendMessage(session, new Gson().toJson(new ErrorCommand("Error: This team spot is already taken")));
                    return;
                } else if ((command.getPlayerColor() == ChessGame.TeamColor.BLACK) && !(AT.getUsername().equals(game.getBlackUsername()))) { //We want black and the http server didnt give it to us
                    sendMessage(session, new Gson().toJson(new ErrorCommand("Error: This team spot is already taken")));
                    return;
                } else if (command.getPlayerColor() == null) {
                    sendMessage(session, new Gson().toJson(new ErrorCommand("Error: You must give a team to join a game")));
                    return;
                } else {
                    //Continue On
                    sessions.addSessionToGame(game.getGameID(), AT.getUsername(), session); //Basically is just adding you to the session list
                    LoadGameCommand LGC = new LoadGameCommand(game.getGame(), "Loaded Game");
                    NotificationCommand NC = new NotificationCommand(AT.getUsername() + " joined the game as team " + command.getPlayerColor());

                    //Server sends a LOAD_GAME message back to the root client.
//                broadcastMessage(game.getGameID(), new Gson().toJson(LGC), AT.getUsername());//Broadcast the game out
                    sendMessage(session, new Gson().toJson(LGC)); //Send the game back to root client

                    //Server sends a Notification message to all other clients in that game informing them the root client joined as an observer.
                    broadcastMessage(game.getGameID(), new Gson().toJson(NC), AT.getUsername());//Broadcast the notification out
                }
            }
            else{ //Error Sent
                ErrorCommand EC = new ErrorCommand("Error : GameID or Auth Token not valid");
                sendMessage(session, new Gson().toJson(EC));
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
                ErrorCommand EC = new ErrorCommand("Error : GameID or Auth Token not valid");
                sendMessage(session, new Gson().toJson(EC));
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
                //Figure out what team someone is (for tests)
                ChessGame.TeamColor t;
                if(Objects.equals(AT.getUsername(), game.getWhiteUsername())){
                    t = ChessGame.TeamColor.WHITE;
                } else if(Objects.equals(AT.getUsername(), game.getBlackUsername())){
                    t = ChessGame.TeamColor.BLACK;
                } else{
                    t = null;
                }


                //Make move for opponent
                if(t != game.getGame().getTeamTurn()){
                    sendMessage(session, new Gson().toJson(new ErrorCommand("Error: It is not your turn")));
                    return;
                }
                //Make move observer
                if(t == null){
                    sendMessage(session, new Gson().toJson(new ErrorCommand("Error: Observers cannot make moves")));
                    return;
                }
                //Make move game over
                if(game.getGame().gameStatus()){
                    sendMessage(session, new Gson().toJson(new ErrorCommand("Error: Game is over, no more moves can be made")));
                    return;
                }


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
                ErrorCommand EC = new ErrorCommand("Error : GameID or Auth Token not valid");
                sendMessage(session, new Gson().toJson(EC));
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

    public void leaveGame(WebSocketSessions sessions, Session session, LeaveCommand command){
        //Always check if they were playing, and if so, remove them
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
                //Remove them from the sessions.
                sessions.removeSession(session);
                //Remove them from playing the game (set to null)
                if(AT.getUsername().equals(game.getWhiteUsername())){ //They were playing White
                    game.setWhiteUsername(null);
                    GameDAO.getInstance().updateGameTeam(true, null, game.getGameID());
                }
                if(AT.getUsername().equals(game.getBlackUsername())){ //They were playing Black
                    game.setBlackUsername(null);
                    GameDAO.getInstance().updateGameTeam(false, null, game.getGameID());
                }
                GameDAO.getInstance().updateGame(game.getGameID(), game.getGame()); //Update in DB
                //Broadcast notification that they left
                NotificationCommand NC = new NotificationCommand(AT.getUsername() + " just left the game");
                broadcastMessage(game.getGameID(), new Gson().toJson(NC), AT.getUsername());//Broadcast the notification out
            }
            else{ //Error Sent
                ErrorCommand EC = new ErrorCommand("Error : GameID or Auth Token not valid");
                sendMessage(session, new Gson().toJson(EC));
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

    public void resignGame(WebSocketSessions sessions, Session session, ResignCommand command){
        //Always check if they were playing, and if so, remove them
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
                //Remove them from playing the game (set to null)
                if(AT.getUsername().equals(game.getWhiteUsername())){ //They were playing White
                    GameDAO.getInstance().updateGameTeam(true, null, game.getGameID());
                }
                if(AT.getUsername().equals(game.getBlackUsername())){ //They were playing Black
                    GameDAO.getInstance().updateGameTeam(false, null, game.getGameID());
                }
                game.getGame().setOver(true); //Set the game to over
                GameDAO.getInstance().updateGame(game.getGameID(), game.getGame()); //Update in DB
                //Broadcast notification that they left
                NotificationCommand NC = new NotificationCommand(AT.getUsername() + " just resigned from the game");
                broadcastMessage(game.getGameID(), new Gson().toJson(NC), null);//Broadcast the notification out

                //Remove them from the sessions. (Do this at the end so we can send them a notification too)
                sessions.removeSession(session);
            }
            else{ //Error Sent
                ErrorCommand EC = new ErrorCommand("Error : GameID or Auth Token not valid");
                sendMessage(session, new Gson().toJson(EC));
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
}
