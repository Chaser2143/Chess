package chess;

import java.util.ArrayList;
import java.util.Collection;

public class King implements ChessPiece{ //TODOKing

    private final PieceType type = PieceType.KING;
    private ArrayList<ChessMove> moves;
    private Boolean hasMoved = false; //I need to remember to change this whenever I move a pawn or a king

    private ChessGame.TeamColor color;

    public King(ChessGame.TeamColor color){
        //King Constructor
        moves = new ArrayList<>();
        setColor(color);
    }

    public Boolean hasMoved() {
        //Returns a bool of whether or not this piece has moved
        return hasMoved;
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
        return null;
    }
}
