package chess;

import java.util.ArrayList;
import java.util.Collection;

public class CBoard implements ChessBoard{

    private ChessPiece[][] board;

    private int countingOffset = -1;

    public CBoard(){
        board = new ChessPiece[8][8]; //2D array anyone? Counting starts at ZERO
    }

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        //Sets a specific position to a chess piece
        int row = position.getRow() + countingOffset; //Positions start at 1, gross counting offset
        int col = position.getColumn() + countingOffset;
        board[row][col] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        //Returns the chess piece at a position, else null
        int row = position.getRow() + countingOffset; //Positions start at 1, gross counting offset
        int col = position.getColumn() + countingOffset;
        return board[row][col];
    }

    @Override
    public ChessPosition findPiece(ChessPiece.PieceType type, ChessGame.TeamColor team){
        //Iterate through the chessboard till we find this piece. Used for getting kings. Returns the position of that piece
        for(int i=1; i<=8; i++) { //Iterate through the board
            for (int j = 1; j <= 8; j++) {
                ChessPosition tempPosition = new CPosition(i, j);
                ChessPiece tempPiece = getPiece(tempPosition); //Use getPiece on every spot until we get the piece that matches our input params
                if (tempPiece != null) {
                    if((tempPiece.getPieceType() == type) && tempPiece.getTeamColor() == team){
                        return tempPosition;
                    }
                }
            }
        }
        return null; //Piece is no longer on the board
    }

    @Override
    public Collection<ChessMove> getAllMoves(ChessGame.TeamColor teamColor){
        //Returns a collection of every move (no validation checked) of the given teamColor
        ArrayList<ChessMove> moves = new ArrayList<>();
        for(int i=1; i<=8; i++) { //Iterate through the board
            for (int j = 1; j <= 8; j++) {
                ChessPosition tempPosition = new CPosition(i, j);
                ChessPiece tempPiece = getPiece(tempPosition); //Use getPiece on every spot until we get the piece that matches our input params
                if (tempPiece != null) {
                    if(tempPiece.getTeamColor() == teamColor){
                        moves.addAll(tempPiece.pieceMoves(this, tempPosition)); //Add all moves to overall collection
                    }
                }
            }
        }
        return moves;
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
        //Instantiates all pieces in proper location

        //Black Team
        //Pawns
        for(int i=1; i<=8; i++){
            addPiece(new CPosition(7,i), new Pawn(ChessGame.TeamColor.BLACK));
        }
        //The Rest
        addPiece(new CPosition(8,1), new Rook(ChessGame.TeamColor.BLACK));
        addPiece(new CPosition(8,2), new Knight(ChessGame.TeamColor.BLACK));
        addPiece(new CPosition(8,3), new Bishop(ChessGame.TeamColor.BLACK));
        addPiece(new CPosition(8,4), new Queen(ChessGame.TeamColor.BLACK));
        addPiece(new CPosition(8,5), new King(ChessGame.TeamColor.BLACK));
        addPiece(new CPosition(8,6), new Bishop(ChessGame.TeamColor.BLACK));
        addPiece(new CPosition(8,7), new Knight(ChessGame.TeamColor.BLACK));
        addPiece(new CPosition(8,8), new Rook(ChessGame.TeamColor.BLACK));

        //White Team
        //Pawns
        for(int i=1; i<=8; i++){
            addPiece(new CPosition(2,i), new Pawn(ChessGame.TeamColor.WHITE));
        }
        //The Rest
        addPiece(new CPosition(1,1), new Rook(ChessGame.TeamColor.WHITE));
        addPiece(new CPosition(1,2), new Knight(ChessGame.TeamColor.WHITE));
        addPiece(new CPosition(1,3), new Bishop(ChessGame.TeamColor.WHITE));
        addPiece(new CPosition(1,4), new Queen(ChessGame.TeamColor.WHITE));
        addPiece(new CPosition(1,5), new King(ChessGame.TeamColor.WHITE));
        addPiece(new CPosition(1,6), new Bishop(ChessGame.TeamColor.WHITE));
        addPiece(new CPosition(1,7), new Knight(ChessGame.TeamColor.WHITE));
        addPiece(new CPosition(1,8), new Rook(ChessGame.TeamColor.WHITE));
    }

    @Override
    public void clearBoard(){
        //Clears all the board's data by setting it to null
        //I hope this is better than just instantiating a new board. I think it is.
        for(int i=0; i<=7; i++){
            for(int j=0; j<=7; j++){
                board[i][j] = null;
            }
        }
    }
}
