package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Queen extends CPiece{

    public Queen(ChessGame.TeamColor color) {
        //King Constructor
        super(color, PieceType.QUEEN);
    }

    @Override
    public PieceType getPieceType() {
        //Returns Piece Type
        return PieceType.QUEEN;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> moves = new HashSet<>();
        //Check Diagonals
        checkDiagonals(moves, board, myPosition, this.getTeamColor(), +1, +1); //Checks NE Diagonal
        checkDiagonals(moves, board, myPosition, this.getTeamColor(), -1, +1); //Checks SE Diagonal
        checkDiagonals(moves, board, myPosition, this.getTeamColor(), -1, -1); //Checks SW Diagonal
        checkDiagonals(moves, board, myPosition, this.getTeamColor(), +1, -1); //Checks NW Diagonal

        //Check NSEW Moves
        checkNorthOrSouth(moves, board, myPosition, this.getTeamColor(), +1); //Checks North Moves
        checkNorthOrSouth(moves, board, myPosition, this.getTeamColor(), -1); //Checks South Moves
        checkEastOrWest(moves, board, myPosition, this.getTeamColor(), +1); //Checks East Moves
        checkEastOrWest(moves, board, myPosition, this.getTeamColor(), -1); //Checks West Moves

        return moves;
    }

    private void checkDiagonals(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color, int initDirectionRow, int initDirectionCol) {
        //Helper function, checks one diagonal at a time along the input params (call 4 times for all diagonals)
        //(Row,Col) input for each direction goes as follows: NE(-1,+1), SE(+1,+1), SW(+1,-1), NW(-1,-1)
        int moveRow = initDirectionRow;
        int moveCol = initDirectionCol;
        boolean finished = false;

        CPosition advancedPosition = new CPosition(myPosition.getRow() + moveRow, myPosition.getColumn() + moveCol); //Checks if there is anything 1 row ahead


        while (board.inBounds(advancedPosition) && !finished) { //Position is in bounds
            ChessPiece spot = board.getPiece(advancedPosition);
            if (null == spot) { //If the spot is empty, add it
                CMove availableMove = new CMove(myPosition, advancedPosition); //Make it a move and add it
                validMoves.add(availableMove);
            } else if (spot.getTeamColor() != color) { //There is a piece in the spot, but it belongs to the opposite team
                CMove availableMove = new CMove(myPosition, advancedPosition); //Make it a move and add it
                validMoves.add(availableMove);
                finished = true; //Leave the while loop
            } else if (spot.getTeamColor() == color) { //My team is blocking me
                finished = true;
            }
            moveRow = moveRow + initDirectionRow; //Go one more in that direction (Increment by 1)
            moveCol = moveCol + initDirectionCol; //Go one more in that direction (Increment by 1)
            advancedPosition = new CPosition(myPosition.getRow() + moveRow, myPosition.getColumn() + moveCol);//Increase advanced Position
        }
    }

    private void checkNorthOrSouth(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color, int initDirection){
        //Helper function, checks all moves North (input -1) or South(+1) of the rook;
        //Give input direction to specify North or South
        boolean finished = false;
        int moveDirection = initDirection;

        CPosition advancedPosition = new CPosition(myPosition.getRow()+moveDirection, myPosition.getColumn()); //Checks if there is anything 1 row ahead


        while(board.inBounds(advancedPosition) && !finished) { //Position is in bounds
            ChessPiece spot = board.getPiece(advancedPosition);
            if(null == spot){ //If the spot is empty, add it
                CMove availableMove = new CMove(myPosition, advancedPosition); //Make it a move and add it
                validMoves.add(availableMove);
//                System.out.println("New Move Row : " + advancedPosition.getRow() + " , Col : " + advancedPosition.getColumn());
            } else if (spot.getTeamColor() != color) { //There is a piece in the spot, but it belongs to the opposite team
                CMove availableMove = new CMove(myPosition, advancedPosition); //Make it a move and add it
                validMoves.add(availableMove);
//                System.out.println("New Move Row : " + advancedPosition.getRow() + " , Col : " + advancedPosition.getColumn());
                finished = true; //Leave the while loop
            } else if (spot.getTeamColor() == color){ //Spot has someone on my team
                finished = true; //Leave the while loop
            }
            moveDirection = moveDirection + initDirection; //Go one more in that direction
            advancedPosition = new CPosition(myPosition.getRow()+moveDirection, myPosition.getColumn());//Increase advanced Position
        }
    }

    private void checkEastOrWest(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color, int initDirection){
        //Helper function, checks all moves West (input of -1) or East (input of +1) of the rook;
        //Give input direction to specify East or West
        boolean finished = false;
        int moveDirection = initDirection;

        CPosition advancedPosition = new CPosition(myPosition.getRow(), myPosition.getColumn()+moveDirection); //Checks if there is anything 1 row ahead


        while(board.inBounds(advancedPosition) && !finished) { //Position is in bounds
            ChessPiece spot = board.getPiece(advancedPosition);
            if(null == spot){ //If the spot is empty, add it
                CMove availableMove = new CMove(myPosition, advancedPosition); //Make it a move and add it
                validMoves.add(availableMove);
//                System.out.println("New Move Row : " + advancedPosition.getRow() + " , Col : " + advancedPosition.getColumn());
            } else if (spot.getTeamColor() != color) { //There is a piece in the spot, but it belongs to the opposite team
                CMove availableMove = new CMove(myPosition, advancedPosition); //Make it a move and add it
                validMoves.add(availableMove);
//                System.out.println("New Move Row : " + advancedPosition.getRow() + " , Col : " + advancedPosition.getColumn());
                finished = true; //Leave the while loop
            } else if (spot.getTeamColor() == color){ //Spot has someone on my team
                finished = true; //Leave the while loop
            }
            moveDirection = moveDirection + initDirection; //Go one more in that direction
            advancedPosition = new CPosition(myPosition.getRow(), myPosition.getColumn()+moveDirection);//Increase advanced Position
        }
    }
}