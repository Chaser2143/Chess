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
        ArrayList<ChessMove> validMoves = new ArrayList<>();
        if(board.getPiece(startPosition) != null) {//Make sure there is a piece in that position
            Collection<ChessMove> possibleMoves = board.getPiece(startPosition).pieceMoves(board, startPosition);//Get all the moves for the piece at that position
            for(ChessMove move : possibleMoves) {//for each move
                ChessPiece takenLocation = board.getPiece(move.getEndPosition());//Keep track of the pieces moved
                ChessPiece moving = board.getPiece(move.getStartPosition());

                //Make the move (Fake)
                try {
                    makeMove(move);
                } catch (InvalidMoveException e) {
                    throw new RuntimeException(e);
                }

                //call isInCheck on our team
                if(!isInCheck(getTeamTurn())){
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
        try{
            ChessPiece piece = board.getPiece(move.getStartPosition()); // Find the piece at that start position
            if(move.getPromotionPiece() != null){ //If it is promoted, get the promoted piece
                piece = getNewPiece(piece.getTeamColor(), move.getPromotionPiece());
            }
            board.addPiece(move.getEndPosition(), piece); //Move the piece to that spot
            board.addPiece(move.getStartPosition(), null); //Set the spot where it was to null
        }
        catch(Exception E){
            throw new InvalidMoveException("This is not a valid move");
        }
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

        //Get all the piece moves of the opposite team
        Collection<ChessMove> allMoves = board.getAllMoves(oppositeTeam);
        //Check if myKingLocation is in it or not
        for(ChessMove move : allMoves){
            if(move.getEndPosition() == myKingLocation){
                return true; //If it is, return true and stop
            }
        }
        return false;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        //Check all moves of every piece on this team. If I get null (they all result in check), then I am in checkmate
        return false;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        //I have no possible moves, but I am not currently in check
        //Either no valid moves, or no possible moves
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
