package webSocketMessages.userCommands;

import chess.ChessGame;

/**
 * Used for a user to request to join a game.
 */
public class JoinPlayerCommand extends UserGameCommand{

    private final ChessGame.TeamColor team;
    private final Integer gameID;

    public JoinPlayerCommand(String authToken, Integer gameID, ChessGame.TeamColor team) {
        super(authToken);
        this.commandType = CommandType.JOIN_PLAYER;
        this.team = team;
        this.gameID = gameID;
    }

    public ChessGame.TeamColor getTeam() {
        return team;
    }

    public Integer getGameID() {
        return gameID;
    }
}
