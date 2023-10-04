package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Knight implements ChessPiece{

    private final PieceType type = PieceType.KNIGHT;
    private ArrayList<ChessMove> moves;

    private ChessGame.TeamColor color;

    public Knight(ChessGame.TeamColor color){
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
        //Unfortunately I felt the best way to do this is to just check all 8 individual moves; shouldn't be too computationally expensive
        //South Jumps
        checkJump(moves, board, myPosition, getTeamColor(), -2,+1); //SE
        checkJump(moves, board, myPosition, getTeamColor(), -2,-1); //SW
        //North Jumps
        checkJump(moves, board, myPosition, getTeamColor(), +2,+1); //NE
        checkJump(moves, board, myPosition, getTeamColor(), +2,-1); //NW
        //East Jumps
        checkJump(moves, board, myPosition, getTeamColor(), +1,-2); //EastSouth
        checkJump(moves, board, myPosition, getTeamColor(), +1,+2); //EastNorth
        //West Jumps
        checkJump(moves, board, myPosition, getTeamColor(), -1,-2); //WestSouth
        checkJump(moves, board, myPosition, getTeamColor(), -1,+2); //WestNorth
        return moves;
    }

    private void checkJump(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color, int moveRow, int moveCol){
        //Helper function, Checks the given combination of movements (Row and Col Shift) (ONLY CHECKS A SINGLE MOVE)
        //Leaves making a valid move up to the parent function (valid +2/+1 combos)

        CPosition advancedPosition = new CPosition(myPosition.getRow()+moveRow, myPosition.getColumn()+moveCol); //Checks if there is anything 1 row ahead

        if(board.inBounds(advancedPosition)) { //Position is in bounds
            ChessPiece spot = board.getPiece(advancedPosition);
            if((null == spot) || (spot.getTeamColor() != color)){ //If the spot is empty, add it
                CMove availableMove = new CMove(myPosition, advancedPosition); //Make it a move and add it
                validMoves.add(availableMove);
            }
        }
    }
}
