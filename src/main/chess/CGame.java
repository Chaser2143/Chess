package chess;

import java.util.Collection;

public class CGame implements ChessGame{

    @Override
    public TeamColor getTeamTurn() {
        return null;
    }

    @Override
    public void setTeamTurn(TeamColor team) {

    }

    @Override
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        return null;
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {

    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
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
        //Clears all the board's data (make a board clear function?)
        //Calls Board reset function
        //Sets the teamTurn to White
    }

    @Override
    public ChessBoard getBoard() {
        return null;
    }
}
