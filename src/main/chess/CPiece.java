package chess;

public abstract class CPiece implements ChessPiece{

    ChessGame.TeamColor pieceColor;

    ChessPiece.PieceType type;

    public CPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type){
        //Constructor for child classes to call
        this.pieceColor = pieceColor;
        this.type = type;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }
}
