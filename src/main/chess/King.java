package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;

public class King extends CPiece{

    private Boolean hasMoved = false; //I need to remember to change this whenever I move a pawn or a king


    public King(ChessGame.TeamColor color){
        //King Constructor
        super(color, PieceType.KING);
    }

    public Boolean hasMoved() {
        //Returns a bool of whether this piece has moved
        return hasMoved;
    }

    @Override
    public PieceType getPieceType() {
        //Returns Piece Type
        return PieceType.KING;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> moves = new HashSet<>();
//        System.out.println("Current Position Row : " + myPosition.getRow() + " , Col : " + myPosition.getColumn());
        //There are only 8 single moves, just going to check all of them individually
        checkStep(moves, board, myPosition, getTeamColor(), 0, +1);//E
        checkStep(moves, board, myPosition, getTeamColor(), +1, +1);//NE
        checkStep(moves, board, myPosition, getTeamColor(), +1, 0);//N
        checkStep(moves, board, myPosition, getTeamColor(), +1, -1);//NW
        checkStep(moves, board, myPosition, getTeamColor(), 0, -1);//W
        checkStep(moves, board, myPosition, getTeamColor(), -1, -1);//SW
        checkStep(moves, board, myPosition, getTeamColor(), -1, 0);//S
        checkStep(moves, board, myPosition, getTeamColor(), -1, +1);//SE

        return moves;
    }

    private void checkStep(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color, int moveRow, int moveCol){
        //Helper function, Checks the given combination of movements (Row and Col Shift) (ONLY CHECKS A SINGLE MOVE)
        //Leaves making a valid move up to the parent function (valid +/-1 combos)
        CPosition advancedPosition = new CPosition(myPosition.getRow()+moveRow, myPosition.getColumn()+moveCol); //Checks if there is anything 1 row ahead

        if(board.inBounds(advancedPosition)) { //Position is in bounds
            ChessPiece spot = board.getPiece(advancedPosition);
            if((null == spot) || (spot.getTeamColor() != color)){ //If the spot is empty, add it
                CMove availableMove = new CMove(myPosition, advancedPosition); //Make it a move and add it
//                System.out.println("New Move Row : " + advancedPosition.getRow() + " , Col : " + advancedPosition.getColumn());
                validMoves.add(availableMove);
            }
        }
    }
}
