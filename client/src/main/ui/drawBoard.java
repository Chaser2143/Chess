package ui;

import chess.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;

public class drawBoard {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 3;
    private static final int LINE_WIDTH_IN_CHARS = 1;
    private static final String EMPTY = "";
    private static Random rand = new Random();
    private static Boolean alternate = true;


    public static void main(CBoard board) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        alternate = true;
        out.print(ERASE_SCREEN);

        //Pass in the board to both of these eventually

        drawWhite(out, board);

        out.println();

        drawBlack(out, board);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawBlack(PrintStream out, CBoard board){
        String[] headers = { " a ", " b ", " c ", " d ", " e ", " f ", " g ", " h " };

        drawHeaders(out, headers);

        drawBlackTicTacToeBoard(out, board);
    }

    private static void drawWhite(PrintStream out, CBoard board){
        String[] headers = { " h ", " g ", " f ", " e ", " d ", " c ", " b ", " a " };

        drawHeaders(out, headers);

        drawWhiteTicTacToeBoard(out, board);
    }

    private static void drawHeaders(PrintStream out, String[] headers) {

        setBlack(out);

        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            drawHeader(out, headers[boardCol]);

            if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
                out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
            }
        }
        out.println();
    }

    private static void drawHeader(PrintStream out, String headerText) {
        int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
        int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

        out.print(EMPTY.repeat(prefixLength));
        printHeaderText(out, headerText);
        out.print(EMPTY.repeat(suffixLength));
    }

    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_GREEN);

        out.print(player);

        setBlack(out);
    }

    private static void drawBlackTicTacToeBoard(PrintStream out, CBoard board) {

        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
            drawRowOfSquares(out, board, boardRow);
            drawHeader(out, String.valueOf(8-boardRow));//Draw Letter
            out.println();
        }
    }

    private static void drawWhiteTicTacToeBoard(PrintStream out, CBoard board) {

        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
            drawRowOfSquares(out, board, boardRow);
            drawHeader(out, String.valueOf(boardRow+1));//Draw Letter
            out.println();
        }
    }

    private static void drawRowOfSquares(PrintStream out, CBoard board, int boardRow) {

        for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
            alternate = !alternate;
            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                if(alternate){
                    setWhite(out);
                }
                else{
                    setGray(out);
                }
                alternate = !alternate;


                if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
                    int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
                    int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

                    out.print(EMPTY.repeat(prefixLength));
                    printPlayer(out, alternate ? SET_BG_COLOR_LIGHT_GREY : SET_BG_COLOR_WHITE, board, boardRow, boardCol);
                    out.print(EMPTY.repeat(suffixLength));
                }
                setBlack(out);
            }
        }
    }

    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setGray(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void printPlayer(PrintStream out, String Background, CBoard board, int row, int col) {
        //Pass in board location and the actual board
        //Check if anything is at that spot
        //Remember to space your characters
        out.print(Background);
        out.print(SET_TEXT_COLOR_BLACK);
        String player = "   ";

        ChessPiece p = board.getPiece(new CPosition(row, col));
        if(p != null){ //We have something to print
            if(p.getTeamColor() == ChessGame.TeamColor.WHITE){
                out.print(SET_TEXT_COLOR_BLUE); //Set their color!
            }
            else{
                out.print(SET_TEXT_COLOR_RED);
            }

//            switch p.getPieceType();
            return;
        }
        out.print(player);
    }
}