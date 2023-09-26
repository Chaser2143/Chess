package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Pawn implements ChessPiece{
    private final PieceType type = PieceType.PAWN;
    private ArrayList<ChessMove> moves;
    private Boolean hasMoved = false; //I need to remember to change this whenever I move a pawn or a king

    private ChessGame.TeamColor color;

    public Pawn(ChessGame.TeamColor color){
        moves = new ArrayList<>();
        setColor(color);
    }

    public Boolean hasMoved() {
        return hasMoved;
    }

    private void setColor(ChessGame.TeamColor color) {
        this.color = color;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }

    @Override
    public PieceType getPieceType() {
        return type;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        //Calls all functions to load the moves ArrayList with all possible moves
        moves.clear(); //Clear out any old moves
        checkForward(moves, board, myPosition, this.getTeamColor()); //Checks Single Forward Moves
        checkForwardDiagonal(moves, board, myPosition, this.getTeamColor());//Check Diagonal Attacks
        checkJumpStart(moves, board, myPosition, this.getTeamColor());//Check Jump +2 from start

        return moves;
    }

    private void checkJumpStart(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
        //Helper function, checks whether we have moved, and if not, if we can move forward 2 spaces
        if(!hasMoved){ //If we have moved, stop now
            return;
        }

        int forward = 0; //Denotes which way is a forward movement
        if(color == ChessGame.TeamColor.WHITE){
            forward = -2;
        } else if (color == ChessGame.TeamColor.BLACK) {
            forward = 2;
        }

        CPosition traveledPosition = new CPosition(myPosition.getRow()+(forward/2), myPosition.getColumn());
        CPosition advancedPosition = new CPosition(myPosition.getRow()+forward, myPosition.getColumn()); //Checks if there is anything 1 row ahead

        if(board.inBounds(advancedPosition)) { //Position is in bounds
            ChessPiece inBetween = board.getPiece(traveledPosition);
            ChessPiece spot = board.getPiece(advancedPosition);
            if((null == spot) && (null == inBetween)){ //Make sure the landing and intermediate spots are free
                CMove availableMove = new CMove(myPosition, advancedPosition); //Make it a move and add it
                validMoves.add(availableMove);
            }
        }
    }

    private void checkForwardDiagonal(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
        //Helper function, checks front diagonal takes

        int forward = 0; //Denotes which way is a forward movement
        if(color == ChessGame.TeamColor.WHITE){
            forward = -1;
        } else if (color == ChessGame.TeamColor.BLACK) {
            forward = 1;
        }

        ArrayList<CPosition> advancedPositions = new ArrayList<>(); //Data structure to avoid repeat code
        CPosition advancedPosWest = new CPosition(myPosition.getRow()+forward, myPosition.getColumn()-1); //Checks if there is anything 1 row ahead, On West Diagonal
        CPosition advancedPosEast = new CPosition(myPosition.getRow()+forward, myPosition.getColumn()+1); //Checks if there is anything 1 row ahead, On West Diagonal
        advancedPositions.add(advancedPosWest);
        advancedPositions.add(advancedPosEast);

        for(CPosition advancedPosition : advancedPositions) {
            if (board.inBounds(advancedPosition)) { //Position is in bounds
                ChessPiece spot = board.getPiece(advancedPosition);
                if ((null != spot) && (spot.getTeamColor() != color)) { //Something is there, and its on the opposite team
                    CMove availableMove = new CMove(myPosition, advancedPosition); //Make it a move and add it
                    validMoves.add(availableMove);
                }
            }
        }
    }

    private void checkForward(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
        //Helper function, checks directly in front;

        int forward = 0; //Denotes which way is a forward movement
        if(color == ChessGame.TeamColor.WHITE){
            forward = -1;
        } else if (color == ChessGame.TeamColor.BLACK) {
            forward = 1;
        }

        CPosition advancedPosition = new CPosition(myPosition.getRow()+forward, myPosition.getColumn()); //Checks if there is anything 1 row ahead

        if(board.inBounds(advancedPosition)) { //Position is in bounds
            ChessPiece spot = board.getPiece(advancedPosition);
            if(null == spot){
                CMove availableMove = new CMove(myPosition, advancedPosition); //Make it a move and add it
                validMoves.add(availableMove);
            }
        }
    }
}
