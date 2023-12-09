package webSocketMessages.serverMessages;

import chess.CGame;

/**
 * Used by the server to send the current game state to a client. When a client receives this message, it will redraw the chess board.
 */
public class LoadGameCommand extends ServerMessage{
    private final CGame game;

    public LoadGameCommand(CGame game) {
        super(ServerMessageType.LOAD_GAME);
        this.game = game;
    }

    public CGame getGame() {
        return game;
    }
}
