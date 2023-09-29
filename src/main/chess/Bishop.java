package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Bishop implements ChessPiece {

    private final PieceType type = PieceType.BISHOP;
    private ArrayList<ChessMove> moves;

    private ChessGame.TeamColor color;

    public Bishop(ChessGame.TeamColor color) {
        //King Constructor
        moves = new ArrayList<>();
        setColor(color);
    }

    private void setColor(ChessGame.TeamColor color) {
        //Sets Team Color
        this.color = color;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        //Returns Team Color
        return color;
    }

    @Override
    public PieceType getPieceType() {
        //Returns Piece Type
        return type;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        moves.clear(); //Clear out any old moves
        checkDiagonals(moves, board, myPosition, this.getTeamColor(), -1, +1); //Checks NE Diagonal
        checkDiagonals(moves, board, myPosition, this.getTeamColor(), +1, +1); //Checks SE Diagonal
        checkDiagonals(moves, board, myPosition, this.getTeamColor(), +1, -1); //Checks SW Diagonal
        checkDiagonals(moves, board, myPosition, this.getTeamColor(), -1, -1); //Checks NW Diagonal
        return moves;
    }

    private void checkDiagonals(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color, int initDirectionRow, int initDirectionCol) {
        //Helper function, checks one diagonal at a time along the input params (call 4 times for all diagonals)
        //(Row,Col) input for each direction goes as follows: NE(-1,+1), SE(+1,+1), SW(+1,-1), NW(-1,-1)
        int moveRow = initDirectionRow;
        int moveCol = initDirectionCol;

        CPosition advancedPosition = new CPosition(myPosition.getRow() + moveRow, myPosition.getColumn() + moveCol); //Checks if there is anything 1 row ahead


        while (board.inBounds(advancedPosition)) { //Position is in bounds
            ChessPiece spot = board.getPiece(advancedPosition);
            if (null == spot) { //If the spot is empty, add it
                CMove availableMove = new CMove(myPosition, advancedPosition); //Make it a move and add it
                validMoves.add(availableMove);
            } else if (spot.getTeamColor() != color) { //There is a piece in the spot, but it belongs to the opposite team
                CMove availableMove = new CMove(myPosition, advancedPosition); //Make it a move and add it
                validMoves.add(availableMove);
                break; //Leave the while loop
            }
            moveRow = moveRow + initDirectionRow; //Go one more in that direction (Increment by 1)
            moveCol = moveCol + initDirectionCol; //Go one more in that direction (Increment by 1)
            advancedPosition = new CPosition(myPosition.getRow() + moveRow, myPosition.getColumn() + moveCol);//Increase advanced Position
        }
    }

}