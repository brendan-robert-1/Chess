package chess.engine.legalmoves;

import chess.engine.Move;
import chess.engine.Offset;
import chess.model.Board;
import chess.model.Coordinate;
import chess.model.Piece;
import chess.model.PieceColor;

import java.util.ArrayList;
import java.util.List;

public class PawnLegalMoveGenerator implements LegalMoveGeneratorInterface {

    public List<Move> generateMoves(Board board, Coordinate startingCoordinate){
        List<Move> legalMoves = new ArrayList<>();
        legalMoves.addAll(captures(board, startingCoordinate));
        legalMoves.addAll(moveTwo(board, startingCoordinate));
        legalMoves.addAll(moveOne(board, startingCoordinate));
        /*legalMoves.addAll(promotions(board, startingCoordinate));*/
        return legalMoves;
    }



    private  List<Move> moveOne(Board board, Coordinate startingCoordinate) {
        List<Move> moveOne = new ArrayList<>();
        Piece pieceToMove = board.getPieceMap().get(startingCoordinate);
        Coordinate endingCoordinate = moveOneEndingCoordinate(pieceToMove.getPieceColor(), startingCoordinate);
        if(noPiecesInPath(startingCoordinate, endingCoordinate, board)){
            moveOne.add(new Move(startingCoordinate, endingCoordinate));
        }
        return moveOne;
    }



    private List<Move> moveTwo(Board board, Coordinate startingCoordinate) {
        List<Move> moveTwo = new ArrayList<>();
        Piece pieceToMove = board.getPieceMap().get(startingCoordinate);
        Coordinate endingCoordinate = moveTwoEndingCoordinate(pieceToMove.getPieceColor(), startingCoordinate);
        if(noPiecesInPath(startingCoordinate, endingCoordinate, board) && pieceToMove.hasMoved() == false){
            moveTwo.add(new Move(startingCoordinate, endingCoordinate));
        }
        return moveTwo;
    }



    private List<Move> captures(Board board, Coordinate startingCoordinate) {
        List<Move> captures = new ArrayList<>();
        PieceColor pieceColor = board.getPieceMap().get(startingCoordinate).getPieceColor();
        Coordinate leftCapture = getLeftCaptureCoordinate(board, startingCoordinate);
        Coordinate rightCapture = getRightCaptureCoordinate(board, startingCoordinate);
        if(leftCapture != null){
            Move leftCaptureMove  = new Move(startingCoordinate, leftCapture);
            if(capturablePiece(leftCapture, board, PieceColor.opposite(pieceColor))){
                captures.add(leftCaptureMove);
            }
        }
        if(rightCapture != null){
            Move rightCaptureMove  = new Move(startingCoordinate, rightCapture);
            if(capturablePiece(rightCapture, board, PieceColor.opposite(pieceColor))){
                captures.add(rightCaptureMove);
            }
        }
        return captures;
    }



    private boolean noPiecesInPath(Coordinate startingCoordinate, Coordinate endingCoordinate, Board board) {
        boolean pieceAtEnd = false;
        boolean pieceAtMoveOne = false;
        if(board.getPieceMap().get(endingCoordinate) != null){
            pieceAtEnd = true;
        }
        Coordinate moveOneCoordinate = moveOneEndingCoordinate(board.getPieceMap().get(startingCoordinate).getPieceColor(), startingCoordinate);
        if(board.getPieceMap().get(moveOneCoordinate) != null){
            pieceAtEnd = true;
        }
        return !pieceAtEnd && !pieceAtMoveOne;
    }

    private Coordinate moveOneEndingCoordinate(PieceColor pieceColor, Coordinate startingCoordinate){
        int rankOffset = 1;
        if(pieceColor.equals(PieceColor.WHITE)){
            rankOffset = 1;
        }else{
            rankOffset = -1;
        }
        return startingCoordinate.offsetBy(0, rankOffset);
    }


    private Coordinate moveTwoEndingCoordinate(PieceColor pieceColor, Coordinate startingCoordinate) {
        int rankOffset = 0;
        if(pieceColor.equals(PieceColor.WHITE)){
            rankOffset = 2;
        }else{
            rankOffset = -2;
        }
        return startingCoordinate.offsetBy(0, rankOffset);
    }



    private boolean capturablePiece(Coordinate endingCoordinate, Board board, PieceColor pieceColor) {
        boolean isCapturablePiece = false;
        Piece capturablePiece = board.getPieceMap().get(endingCoordinate);
        if(capturablePiece != null && capturablePiece.getPieceColor().equals(pieceColor)){
            isCapturablePiece = true;
        }
        return isCapturablePiece;
    }



    private Coordinate getRightCaptureCoordinate(Board board, Coordinate startingCoordinate) {
        PieceColor pieceColor = board.getPieceMap().get(startingCoordinate).getPieceColor();
        int rankOffset = 0;
        int fileOffset = 0;
        if(pieceColor.equals(PieceColor.WHITE)){
            rankOffset = 1;
            fileOffset = 1;
        }else{
            rankOffset = -1;
            fileOffset = -1;
        }
        if(!LegalMoveGenerator.isInBounds(new Offset(fileOffset, rankOffset), startingCoordinate)){
            return null;
        }
        return startingCoordinate.offsetBy(fileOffset, rankOffset);
    }



    private Coordinate getLeftCaptureCoordinate(Board board, Coordinate startingCoordinate) {
        PieceColor pieceColor = board.getPieceMap().get(startingCoordinate).getPieceColor();
        int rankOffset = 0;
        int fileOffset = 0;
        if(pieceColor.equals(PieceColor.WHITE)){
            rankOffset = 1;
            fileOffset = -1;
        }else{
            rankOffset = -1;
            fileOffset = 1;
        }

        if(!LegalMoveGenerator.isInBounds(new Offset(fileOffset, rankOffset), startingCoordinate)){
            return null;
        }
        return startingCoordinate.offsetBy(fileOffset, rankOffset);
    }
}
