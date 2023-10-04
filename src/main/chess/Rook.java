package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Rook extends CPiece{

    public Rook(ChessGame.TeamColor color){
        //King Constructor
        super(color, PieceType.ROOK);
    }
    @Override
    public PieceType getPieceType() {
        //Returns Piece Type
        return PieceType.ROOK;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        checkNorthOrSouth(moves, board, myPosition, this.getTeamColor(), +1); //Checks North Moves
        checkNorthOrSouth(moves, board, myPosition, this.getTeamColor(), -1); //Checks South Moves
        checkEastOrWest(moves, board, myPosition, this.getTeamColor(), +1); //Checks East Moves
        checkEastOrWest(moves, board, myPosition, this.getTeamColor(), -1); //Checks West Moves
        return moves;
    }

    private void checkNorthOrSouth(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color, int initDirection){
        //Helper function, checks all moves North (input -1) or South(+1) of the rook;
        //Give input direction to specify North or South
        int moveDirection = initDirection;

        CPosition advancedPosition = new CPosition(myPosition.getRow()+moveDirection, myPosition.getColumn()); //Checks if there is anything 1 row ahead


        while(board.inBounds(advancedPosition)) { //Position is in bounds
            ChessPiece spot = board.getPiece(advancedPosition);
            if(null == spot){ //If the spot is empty, add it
                CMove availableMove = new CMove(myPosition, advancedPosition); //Make it a move and add it
                validMoves.add(availableMove);
            } else if (spot.getTeamColor() != color) { //There is a piece in the spot, but it belongs to the opposite team
                CMove availableMove = new CMove(myPosition, advancedPosition); //Make it a move and add it
                validMoves.add(availableMove);
                break; //Leave the while loop
            }
            moveDirection = moveDirection + initDirection; //Go one more in that direction
            advancedPosition = new CPosition(myPosition.getRow()+moveDirection, myPosition.getColumn());//Increase advanced Position
        }
    }

    private void checkEastOrWest(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color, int initDirection){
        //Helper function, checks all moves West (input of -1) or East (input of +1) of the rook;
        //Give input direction to specify East or West
        int moveDirection = initDirection;

        CPosition advancedPosition = new CPosition(myPosition.getRow(), myPosition.getColumn()+moveDirection); //Checks if there is anything 1 row ahead


        while(board.inBounds(advancedPosition)) { //Position is in bounds
            ChessPiece spot = board.getPiece(advancedPosition);
            if(null == spot){ //If the spot is empty, add it
                CMove availableMove = new CMove(myPosition, advancedPosition); //Make it a move and add it
                validMoves.add(availableMove);
            } else if (spot.getTeamColor() != color) { //There is a piece in the spot, but it belongs to the opposite team
                CMove availableMove = new CMove(myPosition, advancedPosition); //Make it a move and add it
                validMoves.add(availableMove);
                break; //Leave the while loop
            }
            moveDirection = moveDirection + initDirection; //Go one more in that direction
            advancedPosition = new CPosition(myPosition.getRow(), myPosition.getColumn()+moveDirection);//Increase advanced Position
        }
    }
}
