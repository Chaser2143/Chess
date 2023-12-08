package webSocketMessages.userCommands;

import chess.ChessMove;

/**
 * Used to request to make a move in a game.
 */
public class MakeMoveCommand extends UserGameCommand{
    private final Integer gameID;
    private final ChessMove move;

    public MakeMoveCommand(String authToken, Integer gameID, ChessMove move) {
        super(authToken);
        this.commandType = CommandType.MAKE_MOVE;
        this.gameID = gameID;
        this.move = move;
    }
}
