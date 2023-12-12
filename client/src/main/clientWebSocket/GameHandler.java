package clientWebSocket;

import chess.CGame;

/**
 * Simple interface implemented by gameUI for handling WS messages
 */
public interface GameHandler {
    public void updateGame(CGame game);

    public void printMessage(String message);
}
