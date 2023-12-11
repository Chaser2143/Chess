package webSocketMessages.userCommands;

import chess.ChessGame;

/**
 * Used for a user to request to join a game.
 */
public class JoinPlayerCommand extends UserGameCommand{

    private final ChessGame.TeamColor playerColor;
    private final Integer gameID;

    public JoinPlayerCommand(String authToken, Integer gameID, ChessGame.TeamColor team) {
        super(authToken);
        this.commandType = CommandType.JOIN_PLAYER;
        this.playerColor = team;
        this.gameID = gameID;
    }

    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }

    public Integer getGameID() {
        return gameID;
    }
}
