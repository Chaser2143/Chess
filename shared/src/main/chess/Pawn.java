package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Pawn extends CPiece{
    private Boolean hasMoved = false; //I need to remember to change this whenever I move a pawn or a king

    public Pawn(ChessGame.TeamColor color){
        super(color, PieceType.PAWN);
    }

    public Boolean hasMoved() {
        return hasMoved;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        //Calls all functions to load the moves ArrayList with all possible moves
        HashSet<ChessMove> moves = new HashSet<>();
        // System.out.println("Current Position Row : " + myPosition.getRow() + " , Col : " + myPosition.getColumn());
        checkForward(moves, board, myPosition, this.getTeamColor()); //Checks Single Forward Moves
        checkForwardDiagonal(moves, board, myPosition, this.getTeamColor());//Check Diagonal Attacks
        //checkJumpStart(moves, board, myPosition, this.getTeamColor());//Check Jump +2 from start

        return moves;
    }

    private void checkJumpStart(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
        //Helper function, checks whether we have moved, and if not, if we can move forward 2 spaces

        int forward = 0; //Denotes which way is a forward movement
        if(color == ChessGame.TeamColor.WHITE){
            forward = +2;
        } else if (color == ChessGame.TeamColor.BLACK) {
            forward = -2;
        }

        CPosition traveledPosition = new CPosition(myPosition.getRow()+(forward/2), myPosition.getColumn());
        CPosition advancedPosition = new CPosition(myPosition.getRow()+forward, myPosition.getColumn()); //Checks if there is anything 1 row ahead

        if(board.inBounds(advancedPosition)) { //Position is in bounds
            ChessPiece inBetween = board.getPiece(traveledPosition);
            ChessPiece spot = board.getPiece(advancedPosition);
            if((null == spot) && (null == inBetween)){ //Make sure the landing and intermediate spots are free
                ChessMove availableMove = new CMove(myPosition, advancedPosition);
                validMoves.add(availableMove);
            }
        }
    }

    private void checkForwardDiagonal(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
        //Helper function, checks front diagonal takes

        int forward = 0; //Denotes which way is a forward movement
        if(color == ChessGame.TeamColor.WHITE){
            forward = +1;
        } else if (color == ChessGame.TeamColor.BLACK) {
            forward = -1;
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
                    if (checkPromotion(color, availableMove)) { //Check If there is a promotion, if so, go for it
                        makePromotionMoves(availableMove, validMoves);
                    } else { //Else, just add the move
                        validMoves.add(availableMove);
                    }
                }
            }
        }
    }

    private void checkForward(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
        //Helper function, checks directly in front;

        int forward = 0; //Denotes which way is a forward movement
        if(color == ChessGame.TeamColor.WHITE){
            forward = +1;
        } else if (color == ChessGame.TeamColor.BLACK) {
            forward = -1;
        }

        CPosition advancedPosition = new CPosition(myPosition.getRow()+forward, myPosition.getColumn()); //Checks if there is anything 1 row ahead

        if(board.inBounds(advancedPosition)) { //Position is in bounds
            ChessPiece spot = board.getPiece(advancedPosition);
            if(null == spot){
                CMove availableMove = new CMove(myPosition, advancedPosition); //Make it a move and add it
                if (checkPromotion(color, availableMove)) { //Check If there is a promotion, if so, go for it
                    makePromotionMoves(availableMove, validMoves);
                } else { //Else, just add the move
                    validMoves.add(availableMove);
                }
                if(!hasMovedFromStart(color, myPosition)){ //If I haven't moved from start, and the first move works
                    checkJumpStart(validMoves, board, myPosition, this.getTeamColor());
                }
            }
        }
    }

    private boolean hasMovedFromStart(ChessGame.TeamColor teamColor, ChessPosition startPosition){
        //Returns true if the pawn has moved from the start location
        if(teamColor == ChessGame.TeamColor.WHITE){
            if(startPosition.getRow() == 2){
                return false;
            }
        } else if (teamColor == ChessGame.TeamColor.BLACK) {
            if(startPosition.getRow() == 7){
                return false;
            }
        }
        return true;
    }

    private boolean checkPromotion(ChessGame.TeamColor teamColor, ChessMove move){
        //Returns true if a move involves a promotion, else false
        ChessPosition endMove = move.getEndPosition();
        if(teamColor == ChessGame.TeamColor.WHITE){
            if(endMove.getRow() == 8){
                return true;
            }
        } else if (teamColor == ChessGame.TeamColor.BLACK) {
            if(endMove.getRow() == 1){
                return true;
            }
        }
        return false;
    }

    private Collection<ChessMove> makePromotionMoves(ChessMove move, Collection<ChessMove> validMoves){
        //Returns a collection of chess moves for each promotion
        ChessPosition start = move.getStartPosition();
        ChessPosition end = move.getEndPosition();
        validMoves.add(new CMove(start, end, PieceType.ROOK));
        validMoves.add(new CMove(start, end, PieceType.QUEEN));
        validMoves.add(new CMove(start, end, PieceType.BISHOP));
        validMoves.add(new CMove(start, end, PieceType.KNIGHT));
        return validMoves;
    }
}
