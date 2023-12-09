package ui;

import chess.CGame;
import clientWebSocket.GameHandler;
import clientWebSocket.WebSocketFacade;
import exception.ResponseException;

import java.util.Arrays;

/**
 * As I understand it, I will make methods in here (maybe even a repl) to implement all of the game - websocket
 * related parts of this project
 */
public class GameUI implements GameHandler {
    private WebSocketFacade wsFacade;

    private String UserName;
    private String Team;
    private String AuthToken;
    private int GameID;

    public GameUI(String AuthToken, String UserName, String Team, int GameID){
        this.AuthToken = AuthToken;
        this.UserName = UserName;
        this.Team = Team;
        this.GameID = GameID;
        try {
            wsFacade = new WebSocketFacade(this); //Has a WebSocketFacade
        } catch (ResponseException ex){
            System.out.println("A fatal error occurred in creating a web socket. ERROR: " + ex.getMessage());
        }
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
//                case "test" -> sendBasic();
                case "redraw" -> redraw(params);
                case "leave" -> leave(params);
                case "makemove" -> makeMove(params);
                case "resign" -> resign(params);
                case "showmoves" -> showMoves(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (Exception ex) {
            return String.join("\n", "An error occurred, please try again with valid parameters.", ex.getMessage(), help()); //This will basically throw an error whenever something doesn't work...
        }
    }

    /**
     * Communicates with the web socket to join as an observer
     */
    public void joinAsObserver(String Authtoken, int gameID) throws ResponseException{
        wsFacade.joinObserver(AuthToken, gameID);
    }

    /**
     * Basic test for WS (sends a string around the world)
     */
    public String sendBasic() throws ResponseException{
        wsFacade.sendStringTest();
        return "Test String Sent";
    }

    /**
     * Redraws the chess board upon the user’s request.
     */
    public String redraw(String... params) throws ResponseException{
        return null;
    }

    /**
     * Removes the user from the game (whether they are playing or observing the game).
     * The client transitions back to the Post-Login UI.
     */
    public String leave(String... params) throws ResponseException{
        return null;
    }

    public String makeMove(String... params) throws ResponseException{
        return null;
    }

    public String resign(String... params) throws Exception{
        return null;
    }

    public String showMoves(String... params) throws ResponseException{
        return null;
    }

    public String help() {
        return """
                redraw - redraws the chess board
                leave - leave the current game
                makemove <piece><row><column> - moves the given piece to this row and column
                resign - forfeit the game
                showmoves <piece><row><column> - highlights the legal moves for a given piece
                help - with possible commands
                """;
    }

    @Override
    public void updateGame(CGame game) {
//        System.out.println("Board received in GameUI");

    }

    @Override
    public void printMessage(String message) {
        System.out.println("Message Received in GameUI: " + message);
    }
}
