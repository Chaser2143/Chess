package chess;

import java.util.ArrayList;
import java.util.Collection;

public class CGame implements ChessGame{
    TeamColor teamTurn;
    ChessBoard board;

    public CGame(){
        //Initializes a new Chess Game
        teamTurn = TeamColor.WHITE;
        board = new CBoard();
        board.resetBoard();
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
        ArrayList<ChessMove> validMoves = new ArrayList<>();
        ChessPiece piece = board.getPiece(startPosition);
        if(piece != null) {//Make sure there is a piece in that position
            Collection<ChessMove> possibleMoves = piece.pieceMoves(board, startPosition);//Get all the moves for the piece at that position
            for(ChessMove move : possibleMoves) {//for each move
                ChessPiece takenLocation = board.getPiece(move.getEndPosition());//Keep track of the pieces moved
                ChessPiece moving = board.getPiece(move.getStartPosition());

                //Make the move (Fake)
                board.addPiece(move.getEndPosition(), moving); //Move the piece to that spot
                board.addPiece(move.getStartPosition(), null); //Set the spot where it was to null

                //call isInCheck on our team
                if(!isInCheck(piece.getTeamColor())){
                    validMoves.add(move);//If not in check, add it to valid Moves
                }

                //Undo the move
                board.addPiece(move.getStartPosition(), moving); //Put the OG piece back at the start position
                board.addPiece(move.getEndPosition(), takenLocation); //Put the taken piece back at the end position
            }
            return validMoves;
        }
        else{
            return null;
        }
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        //Makes a chess move
        ChessPiece piece = board.getPiece(move.getStartPosition()); // Find the piece at that start position
        //Check if the move is in the valid moves list
        if(piece != null) {
            if (piece.getTeamColor() == getTeamTurn()) { //Check if it's our turn
                Collection<ChessMove> validMoves = validMoves(move.getStartPosition());
                if (validMoves.contains(move)) { //Move is valid
                    if(move.getPromotionPiece() != null){ //If it has a promotion, make it now
                        piece = getNewPiece(piece.getTeamColor(), move.getPromotionPiece());
                    }
                    board.addPiece(move.getEndPosition(), piece); //Move the piece to that spot
                    board.addPiece(move.getStartPosition(), null); //Set the spot where it was to null
                    //Set the team turn
                    if(getTeamTurn() == TeamColor.WHITE){
                        setTeamTurn(TeamColor.BLACK);
                    }
                    else{
                        setTeamTurn(TeamColor.WHITE);
                    }
                    return;
                }
            }
        }
        throw new InvalidMoveException("This is not a valid move");
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        //Check if the given team is in check
        TeamColor oppositeTeam;
        if(teamColor == TeamColor.WHITE){
            oppositeTeam = TeamColor.BLACK;
        } else{
            oppositeTeam = TeamColor.WHITE;
        }
        //Get my team's king's position
        ChessPosition myKingLocation = board.findPiece(ChessPiece.PieceType.KING, teamColor);
        if(myKingLocation == null){ //This would only happen in a test environment
            return false;
        }
//        System.out.println("King Location : " + myKingLocation.toString());
        //Get all the piece moves of the opposite team
        Collection<ChessMove> allMoves = board.getAllMoves(oppositeTeam);
        //Check if myKingLocation is in it or not
        for(ChessMove move : allMoves){
//            System.out.println("Piece End Location : " + move.getEndPosition().toString());
            if(move.getEndPosition().getRow() == myKingLocation.getRow() && move.getEndPosition().getColumn() == myKingLocation.getColumn()){
//                System.out.println("Move putting king in check ; Start : " + move.getStartPosition().toString() + " End : " + move.getEndPosition().toString());
                return true; //If it is, return true and stop
            }
        }
        return false;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        //Check all moves of every piece on this team. If I get null (they all result in check), then I am in checkmate

        //Get all valid moves of my team
        Collection<ChessMove> allValidMoves = getAllValidMoves(teamColor);
        //If I am in check and all valid moves is null, I am in check mate
        if((isInCheck(teamColor)) && (allValidMoves.isEmpty())){
            return true;
        }
        return false;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        //I have no possible moves, but I am not currently in check
        //Either no valid moves, or no possible moves

        //Get all valid moves of my team
        Collection<ChessMove> allValidMoves = getAllValidMoves(teamColor);
        //If I am not in check, but valid moves is null, I am in stalemate
        if((!isInCheck(teamColor)) && (allValidMoves.isEmpty())){
            return true;
        }
        return false;
    }

    private Collection<ChessMove> getAllValidMoves(TeamColor teamColor){
        //Returns a collection of every valid move of the given teamColor
        ArrayList<ChessMove> moves = new ArrayList<>();
        for(int i=1; i<=8; i++) { //Iterate through the board
            for (int j = 1; j <= 8; j++) {
                ChessPosition tempPosition = new CPosition(i, j); //Make a position
                ChessPiece tempPiece = board.getPiece(tempPosition);
                if((tempPiece != null) && (tempPiece.getTeamColor() == teamColor))
                { //Does that position have a piece that is on my team
                    Collection<ChessMove> vMoveList = validMoves(tempPosition); //If so, get all its valid moves
                    for (ChessMove vMove : vMoveList) {
                        if (vMove != null) {
                            moves.add(vMove); //Double verification, make sure not to add moves that are null
                        }
                    }
                }
            }
        }
        return moves;
    }

    @Override
    public void setBoard(ChessBoard board) {
        //Sets the games chessboard with the given board
        this.board = board;
    }

    @Override
    public ChessBoard getBoard() {
        return board;
    }

    private ChessPiece getNewPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type){
        switch (type){
            case PAWN -> {
                return new Pawn(pieceColor);
            }
            case ROOK -> {
                return new Rook(pieceColor);
            }
            case KNIGHT -> {
                return new Knight(pieceColor);
            }
            case KING -> {
                return new King(pieceColor);
            }
            case QUEEN -> {
                return new Queen(pieceColor);
            }
            case BISHOP -> {
                return new Bishop(pieceColor);
            }
            default -> {
                return null;
            }
        }
    }
}
