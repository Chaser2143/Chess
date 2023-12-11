package ui;

import chess.CBoard;

public class testDrawBoard {
    public static void main(String[] args) {
        CBoard b = new CBoard();
        b.resetBoard();
        drawBoard.main(b, true);
    }
}
