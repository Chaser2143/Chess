package chess;

public class CMove implements ChessMove{
    private ChessPosition startPos;
    private ChessPosition endPos;
    private ChessPiece promotion;

    public CMove(ChessPosition start, ChessPosition end){ //Overloaded constructor for no pawn promotion
        this(start, end, null);
    }

    public CMove(ChessPosition start, ChessPosition end, ChessPiece promotion){//Constructor
        setStartPos(start);
        setEndPos(end);
        setPromotion(promotion);
    }

    private void setPromotion(ChessPiece promotion) {
        this.promotion = promotion;
    }

    private void setStartPos(ChessPosition startPos) {
        this.startPos = startPos;
    }

    private void setEndPos(ChessPosition endPos) {
        this.endPos = endPos;
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
        return promotion.getPieceType();
    }
}
