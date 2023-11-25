package chess;

import java.util.Objects;

public class CMove implements ChessMove{
    private ChessPosition startPos;
    private ChessPosition endPos;
    private ChessPiece.PieceType promotion;

    public CMove(ChessPosition start, ChessPosition end, ChessPiece.PieceType promotion){//Constructor
        startPos = start;
        endPos = end;
        this.promotion = promotion;
    }

    public CMove(ChessPosition start, ChessPosition end){ //Overloaded constructor for no pawn promotion
        this(start, end, null);
    }


    @Override
    public ChessPosition getStartPosition() {
        return startPos;
    }

    @Override
    public ChessPosition getEndPosition() {
        return endPos;
    }

    @Override
    public ChessPiece.PieceType getPromotionPiece() {
        return promotion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CMove cMove = (CMove) o;
        return Objects.equals(startPos, cMove.startPos) && Objects.equals(endPos, cMove.endPos) && promotion == cMove.promotion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPos, endPos, promotion);
    }
}
