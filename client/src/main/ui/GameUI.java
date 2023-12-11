package ui;

import chess.*;
import clientWebSocket.GameHandler;
import clientWebSocket.WebSocketFacade;
import exception.ResponseException;
import models.Game;
import webSocketMessages.userCommands.MakeMoveCommand;

import java.util.Arrays;
import java.util.Objects;

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

    private CGame cachedGame;

    public GameUI(String AuthToken, String UserName, String Team, int GameID){
        this.AuthToken = AuthToken;
        this.UserName = UserName;
        this.Team = Team.toLowerCase();
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
                case "redraw" -> redraw();
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
     * Communicates with the web socket to join as a player
     */
    public void joinAsPlayer(String Authtoken, int gameID, ChessGame.TeamColor team) throws ResponseException{
        wsFacade.joinPlayer(AuthToken, gameID, team);
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
    public String redraw() throws ResponseException{
        drawBoard.main((CBoard) cachedGame.getBoard(), Objects.equals(Team, "white") || Objects.equals(Team, "")); //Draw in white direction if true, else black

        System.out.println("Please use specific notation makeMove <startColumn> <startRow> <endColumn> <endRow> <BLANK|Promotion>");
        System.out.println("White is Red.");
        return "Redrawing Board.";
    }

    /**
     * Removes the user from the game (whether they are playing or observing the game).
     * The client transitions back to the Post-Login UI.
     */
    public String leave(String... params) throws ResponseException{
        return null;
    }

    public String makeMove(String... params) throws ResponseException{
        assertPlaying();
        assertTurn();

        if(params.length != 4 && params.length != 5){ //4 is regular, 5 is with promotion
            throw new ResponseException(400, "Invalid Input, please use specific notation <startColumn> <startRow> <endColumn> <endRow> <BLANK|Promotion>");
        }

        int startCol;
        switch (params[0]) { //Watch Offset Here
            case "a" -> startCol = 1;
            case "b" -> startCol = 2;
            case "c" -> startCol = 3;
            case "d" -> startCol = 4;
            case "e" -> startCol = 5;
            case "f" -> startCol = 6;
            case "g" -> startCol = 7;
            case "h" -> startCol = 8;
            default -> throw new ResponseException(400, "Invalid Input, please use specific notation <startColumn> <startRow> <endColumn> <endRow> <BLANK|Promotion>");
        }

        int startRow = Integer.valueOf(params[1]);

        CPosition start = new CPosition(startRow, startCol);

        int endCol;
        switch(params[2]){ //Watch Offset Here
            case "a" -> endCol = 1;
            case "b" -> endCol = 2;
            case "c" -> endCol = 3;
            case "d" -> endCol = 4;
            case "e" -> endCol = 5;
            case "f" -> endCol = 6;
            case "g" -> endCol = 7;
            case "h" -> endCol = 8;
            default -> throw new ResponseException(400, "Invalid Input, please use specific notation <startColumn> <startRow> <endColumn> <endRow> <BLANK|Promotion>");
        }

        int endRow = Integer.valueOf(params[3]);

        CPosition end = new CPosition(endRow, endCol);

        if(params.length == 5){
            ChessPiece.PieceType p;
            switch(params[4]){ //Watch Offset Here
                case "r" -> p = ChessPiece.PieceType.ROOK;
                case "q" -> p = ChessPiece.PieceType.QUEEN;
                case "b" -> p = ChessPiece.PieceType.BISHOP;
                case "n" -> p = ChessPiece.PieceType.KNIGHT;
                case "p" -> p = ChessPiece.PieceType.PAWN;

                default -> throw new ResponseException(400, "Invalid Input, please use specific notation <startColumn> <startRow> <endColumn> <endRow> <BLANK|Promotion>");
            }

            wsFacade.makeMove(AuthToken, GameID, new CMove(start, end, p));
        }
        else{
            wsFacade.makeMove(AuthToken, GameID, new CMove(start, end));
        }
        return "Move Sent to server";
    }

    public String resign(String... params) throws Exception{
        assertPlaying();
        return null;
    }

    public String showMoves(String... params) throws ResponseException{
        assertPlaying();
        return null;
    }

    public String help() {
        if(Team.isEmpty()){
            return """
                redraw - redraws the chess board
                leave - leave the current game
                help - with possible commands
                """;
        }
        return """
                redraw - redraws the chess board
                leave - leave the current game
                makemove <startColumn> <startRow> <endColumn> <endRow> - moves the given piece to this row and column
                resign - forfeit the game
                showmoves <piece> <column> <row> - highlights the legal moves for a given piece
                help - with possible commands
                """;
    }

    @Override
    public void updateGame(CGame game) {
        System.out.println("Board received in GameUI");
        drawBoard.main((CBoard) game.getBoard(), Objects.equals(Team, "white") || Objects.equals(Team, "")); //Draw in white direction if true, else black
        this.cachedGame = game; //Save cached game
        System.out.println("Please use specific notation: makeMove <startColumn> <startRow> <endColumn> <endRow> <BLANK|Promotion>");
        System.out.println("White is Red.");
    }

    @Override
    public void printMessage(String message) {
        System.out.println("Message Received in GameUI: " + message);
    }

    private void assertPlaying() throws ResponseException {
        if (Team.isEmpty()) {
            throw new ResponseException(400, "You must be playing to make a move.");
        }
    }

    private void assertTurn() throws ResponseException {
        var boardTeamTurn = cachedGame.getTeamTurn().toString().toLowerCase();
        if (!boardTeamTurn.equals(Team)) {
            throw new ResponseException(400, "It is not your turn.");
        }
    }
}
