package webSocketMessages.serverMessages;

import chess.CGame;

/**
 * Used by the server to send the current game state to a client. When a client receives this message, it will redraw the chess board.
 */
public class LoadGameCommand extends ServerMessage{
    private final CGame game;
    private final String message;

    public LoadGameCommand(CGame game, String message) {
        super(ServerMessageType.LOAD_GAME);
        this.game = game;
        this.message = message;
    }

    public CGame getGame() {
        return game;
    }
}
