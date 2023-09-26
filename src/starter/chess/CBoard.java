package chess;

public class CBoard implements ChessBoard{

    private ChessPiece[][] board;
    private int countingOffset = -1;

    public CBoard(){
        board = new ChessPiece[7][7]; //2D array anyone? Counting starts at ZERO
    }

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        //Sets a specific position to a chess piece
        int row = position.getRow() + countingOffset; //Positions start at 1, kinda gross
        int col = position.getColumn() + countingOffset;
        board[row][col] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        //Returns the chess piece at a position, else null
        int row = position.getRow() + countingOffset; //Positions start at 1, kinda gross
        int col = position.getColumn() + countingOffset;
        return board[row][col];
    }

    @Override
    public Boolean inBounds(ChessPosition position) {
        //Checks if a given position is within the bounds of the board
        if ((inBoundsHelper(position.getRow())) && (inBoundsHelper(position.getColumn()))) {
            return true;
        }
        else{
            return false;
        }
    }

    private Boolean inBoundsHelper(int position){
        //Checks if an int is within the range of 0 to 7 inclusive (board boundaries)
        if((0 <= position) && (position <= 7)) {
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void resetBoard() {
        //TODO
    }
}
