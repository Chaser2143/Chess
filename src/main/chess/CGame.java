package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CGame implements ChessGame{
    TeamColor teamTurn;
    ChessBoard board;

    public CGame(){
        //Initializes a new Chess Game
        teamTurn = TeamColor.WHITE;
        board = new CBoard();
        setBoard(board); //Set up the board

    }

    @Override
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    @Override
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    @Override
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        if(board.getPiece(startPosition) != null) {//Make sure there is a piece in that position
            var possibleMoves = board.getPiece(startPosition).pieceMoves(board, startPosition);//Get all the moves for the piece at that position
            for(ChessMove move : possibleMoves) {//for each move
                //Make a fake board (temporary) (or just keep track of the piece moved and make the move, then check)
                //call isInCheck
                //If not in check, add it to valid Moves
            }
        }
        else{
            return null;
        }
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        //Get the valid moves for that start position
        //If our move is in the return of valid Moves, make the move
        //Else throw the InvalidMoveException
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        //Get all the piece moves of the opposite team
        //See if any of them land on my king's piece location
        //If there is one, then I'm in check, else im good
        return false;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        return false;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        return false;
    }

    @Override
    public void setBoard(ChessBoard board) {
        //Sets the board and the rules to be ready for gameplay
        board.clearBoard();//Clears all the board's data (make a board clear function?)
        board.resetBoard();//Calls Board reset function
    }

    @Override
    public ChessBoard getBoard() {
        return board;
    }
}
