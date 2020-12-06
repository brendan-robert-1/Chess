package chess.engine.legalmoves;

import chess.engine.Move;
import chess.engine.Offset;
import chess.engine.exception.IllegalMoveException;
import chess.model.*;

import java.util.ArrayList;
import java.util.List;

public class KingLegalMoveGenerator implements LegalMoveGeneratorInterface {
    @Override
    public List<Move> generateMoves(Board board, Coordinate startingCoordinate) {
        List<Move> legalMoves = new ArrayList<>();
        PieceColor friendlyPieceColor = board.getPieceMap().get(startingCoordinate).getPieceColor();
        for(Offset offset : generateOffsets()){
            Coordinate targetCoordinate = startingCoordinate.offsetBy(0, 0);
            boolean inBounds = LegalMoveGenerator.isInBounds(offset, targetCoordinate);
            if(inBounds){
                targetCoordinate = targetCoordinate.offsetBy(offset);
                Piece pieceAt = board.getPieceMap().get(targetCoordinate);
                if(pieceAt != null && pieceAt.getPieceColor().equals(PieceColor.opposite(friendlyPieceColor))){
                    Move move = new Move(startingCoordinate, targetCoordinate);
                    legalMoves.add(move);

                }
                if(pieceAt == null){
                    Move move = new Move(startingCoordinate, targetCoordinate);
                    legalMoves.add(move);
                }
            }
        }
        legalMoves.addAll(generateCastles(board, startingCoordinate));
        return legalMoves;
    }

    private List<Move> generateCastles(Board board, Coordinate startingCoordinate){
        List<Move> castles = new ArrayList<>();
        Piece piece = board.getPieceMap().get(startingCoordinate);
        if(piece.getPieceType().equals(PieceType.KING)){
            boolean noPiecesinWayLong = noPiecesInWayLong(board, piece.getPieceColor());
            boolean piecesHaventMovedLong = piecesHaventMovedLong(board, piece.getPieceColor());
            boolean noPiecesinWayShort = noPiecesInWayShort(board, piece.getPieceColor());
            boolean piecesHaventMovedShort = piecesHaventMovedShort(board, piece.getPieceColor());
            if(noPiecesinWayLong && piecesHaventMovedLong){
                castles.add(generateLongCastle(piece.getPieceColor()));
            }

            if(noPiecesinWayShort && piecesHaventMovedShort){
                castles.add(generateShortCastle(piece.getPieceColor()));
            }
        }
        return castles;
    }



    private Move generateShortCastle(PieceColor pieceColor) {
        if(pieceColor.equals(PieceColor.WHITE)){
            return new Move(new Coordinate(BoardFile.e, 1), new Coordinate(BoardFile.g, 2));
        }else {
            return new Move(new Coordinate(BoardFile.e, 8), new Coordinate(BoardFile.g, 8));
        }
    }



    private Move generateLongCastle(PieceColor pieceColor) {
        if(pieceColor.equals(PieceColor.WHITE)){
            return new Move(new Coordinate(BoardFile.e, 1), new Coordinate(BoardFile.c, 2));
        }else {
            return new Move(new Coordinate(BoardFile.e, 8), new Coordinate(BoardFile.c, 8));
        }
    }



    public boolean noPiecesInWayLong(Board board, PieceColor pieceColor){
        Coordinate bishopLongCoordinate = getBishopLongCoordinate(pieceColor);
        Coordinate queenCoordinate = getQueenCoordinate(pieceColor);
        Coordinate knightLongCoordinate = getKnightLongCoordinate(pieceColor);
        Piece bishop = board.pieceAt(bishopLongCoordinate);
        Piece queen = board.pieceAt(queenCoordinate);
        Piece knight = board.pieceAt(knightLongCoordinate);
        if(bishop == null && queen == null && knight == null){
            return true;
        }
        return false;

    }

    public boolean noPiecesInWayShort(Board board, PieceColor pieceColor){
        Coordinate bishopShortCoordinate = getBishopShortCoordinate(pieceColor);
        Coordinate knightShortCoordinate = getKnightShortCoordinate(pieceColor);
        Piece bishop = board.pieceAt(bishopShortCoordinate);
        Piece knight = board.pieceAt(knightShortCoordinate);
        if(bishop == null && knight == null){
            return true;
        }
        return false;
    }


    public boolean piecesHaventMovedLong(Board board, PieceColor pieceColor)  {
        Coordinate kingStart = getKingStartingCoordinate(pieceColor);
        Piece king = board.getPieceMap().get(kingStart);
        if(king == null || king.hasMoved()) {
            return false;
        }
        Coordinate rookStart = getLongRookStartingCoordinate(pieceColor);
        Piece rook = board.getPieceMap().get(rookStart);
        if(rook == null || rook.hasMoved()) {
            return false;
        }
        return true;
    }

    public boolean piecesHaventMovedShort(Board board, PieceColor pieceColor){
        Coordinate kingStart = getKingStartingCoordinate(pieceColor);
        Piece king = board.getPieceMap().get(kingStart);
        if(king == null || king.hasMoved()) {
            return false;
        }
        Coordinate rookStart = getShortRookStartingCoordinate(pieceColor);
        Piece rook = board.getPieceMap().get(rookStart);
        if(rook == null || rook.hasMoved()) {
            return false;
        }
        return true;
    }

    public Coordinate getKingStartingCoordinate(PieceColor pieceColor) {
        if(pieceColor.equals(PieceColor.WHITE)){
            return new Coordinate(BoardFile.e, 1);
        } else {
            return new Coordinate(BoardFile.e, 8);
        }
    }
    public Coordinate getLongRookStartingCoordinate(PieceColor pieceColor) {
        if(pieceColor.equals(PieceColor.WHITE)){
            return new Coordinate(BoardFile.a, 1);
        }else{
            return new Coordinate(BoardFile.a, 8);
        }
    }
    public Coordinate getShortRookStartingCoordinate(PieceColor pieceColor) {
        if(pieceColor.equals(PieceColor.WHITE)){
            return new Coordinate(BoardFile.h, 1);
        } else {
            return new Coordinate(BoardFile.h, 8);
        }
    }


    public Coordinate getRookShortEndingCoordinate(PieceColor pieceColor) {
        if(pieceColor.equals(PieceColor.WHITE)){
            return new Coordinate(BoardFile.f, 1);
        } else {
            return new Coordinate(BoardFile.f, 8);
        }
    }



    public Coordinate getKingShortEndingCoordinate(PieceColor pieceColor) {
        if(pieceColor.equals(PieceColor.WHITE)){
            return new Coordinate(BoardFile.g, 1);
        } else {
            return new Coordinate(BoardFile.g, 8);
        }
    }


    public Coordinate getKingLongEndingCoordinate(PieceColor pieceColor) {
        if(pieceColor.equals(PieceColor.WHITE)){
            return new Coordinate(BoardFile.c, 1);
        } else {
            return new Coordinate(BoardFile.c, 8);
        }
    }
    public Coordinate getRookLongEndingCoordinate(PieceColor pieceColor) {
        if(pieceColor.equals(PieceColor.WHITE)){
            return new Coordinate(BoardFile.d, 1);
        } else {
            return new Coordinate(BoardFile.d, 8);
        }
    }


    public Coordinate getKnightShortCoordinate(PieceColor pieceColor) {
        if(pieceColor.equals(PieceColor.WHITE)){
            return new Coordinate(BoardFile.g, 1);
        } else {
            return new Coordinate(BoardFile.g, 8);
        }
    }

    public Coordinate getBishopShortCoordinate(PieceColor pieceColor) {
        if(pieceColor.equals(PieceColor.WHITE)){
            return new Coordinate(BoardFile.f, 1);
        } else {
            return new Coordinate(BoardFile.f, 8);
        }
    }

    public Coordinate getKnightLongCoordinate(PieceColor pieceColor) {
        if(pieceColor.equals(PieceColor.WHITE)){
            return new Coordinate(BoardFile.b, 1);
        } else {
            return new Coordinate(BoardFile.b, 8);
        }
    }

    public Coordinate getQueenCoordinate(PieceColor pieceColor) {
        if(pieceColor.equals(PieceColor.WHITE)){
            return new Coordinate(BoardFile.d, 1);
        } else {
            return new Coordinate(BoardFile.d, 8);
        }
    }

    public Coordinate getBishopLongCoordinate(PieceColor pieceColor) {
        if(pieceColor.equals(PieceColor.WHITE)){
            return new Coordinate(BoardFile.c, 1);
        } else {
            return new Coordinate(BoardFile.c, 8);
        }
    }


    private List<Offset> generateOffsets() {
        List<Offset> offsets = new ArrayList<>();
        offsets.add(new Offset(1,1));
        offsets.add(new Offset(1,-1));
        offsets.add(new Offset(-1,1));
        offsets.add(new Offset(-1,-1));

        offsets.add(new Offset(0,1));
        offsets.add(new Offset(0,-1));
        offsets.add(new Offset(1,0));
        offsets.add(new Offset(-1,0));
        return offsets;
    }
}
