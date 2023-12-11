package webSocketMessages.userCommands;

import chess.ChessGame;
import chess.ChessMove;

/**
 * Used to request to make a move in a game.
 */
public class MakeMoveCommand extends UserGameCommand{
    private final Integer gameID;
    private final ChessMove move;
    private final ChessGame.TeamColor team;

    public MakeMoveCommand(String authToken, Integer gameID, ChessMove move, ChessGame.TeamColor team) {
        super(authToken);
        this.commandType = CommandType.MAKE_MOVE;
        this.gameID = gameID;
        this.move = move;
        this.team = team;
    }

    public ChessGame.TeamColor getTeam(){
        return team;
    }

    public Integer getGameID() {
        return gameID;
    }

    public ChessMove getMove() {
        return move;
    }
}
