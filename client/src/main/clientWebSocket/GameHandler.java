package clientWebSocket;

import chess.CGame;

public interface GameHandler {
    public void updateGame(CGame game);

    public void printMessage(String message);
}
