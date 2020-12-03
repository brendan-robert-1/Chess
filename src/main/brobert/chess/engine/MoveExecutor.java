package chess.engine;

import chess.model.*;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Set;

public class MoveExecutor {

    public Board executeMove(Move move, Board board){
        Map<Coordinate, Piece> mutableMap = Maps.newHashMap(board.getPieceMap());
        Piece pieceToMove = mutableMap.get(move.startingCoordinate);
        mutableMap.put(move.endingCoordinate, pieceToMove);
        mutableMap.remove(move.startingCoordinate);
        return new Board(mutableMap);
    }

    public Board castleShort(PieceColor pieceColor, Board board){
        Coordinate kingStart = getKingStartingCoordinate(pieceColor);
        Coordinate rookStart = getShortRookStartingCoordinate(pieceColor);
        Coordinate kingEnd = getKingShortEndingCoordinate(pieceColor);
        Coordinate rookEnd = getRookShortEndingCoordinate(pieceColor);
        Move kingMove = new Move(kingStart, kingEnd);
        Move rookMove = new Move(rookStart, rookEnd);
        Board afterBoard = executeMove(kingMove, board);
        afterBoard = executeMove(rookMove, board);
        return afterBoard;
    }

    public Board castleLong(PieceColor pieceColor, Board board){
        Coordinate kingStart = getKingStartingCoordinate(pieceColor);
        Coordinate rookStart = getLongRookStartingCoordinate(pieceColor);
        Coordinate kingEnd = getKingLongEndingCoordinate(pieceColor);
        Coordinate rookEnd = getRookLongEndingCoordinate(pieceColor);
        Move kingMove = new Move(kingStart, kingEnd);
        Move rookMove = new Move(rookStart, rookEnd);
        Board afterKing = executeMove(kingMove, board);
        Board afterRook = executeMove(rookMove, afterKing);
        return afterRook;
    }





    private Coordinate getKingStartingCoordinate(PieceColor pieceColor) {
        if(pieceColor.equals(PieceColor.WHITE)){
            return new Coordinate(BoardFile.e, 1);
        } else {
            return new Coordinate(BoardFile.e, 8);
        }
    }
    private Coordinate getLongRookStartingCoordinate(PieceColor pieceColor) {
        if(pieceColor.equals(PieceColor.WHITE)){
            return new Coordinate(BoardFile.a, 1);
        }else{
            return new Coordinate(BoardFile.a, 8);
        }
    }
    private Coordinate getShortRookStartingCoordinate(PieceColor pieceColor) {
        if(pieceColor.equals(PieceColor.WHITE)){
            return new Coordinate(BoardFile.g, 1);
        } else {
            return new Coordinate(BoardFile.g, 8);
        }
    }


    private Coordinate getRookShortEndingCoordinate(PieceColor pieceColor) {
        if(pieceColor.equals(PieceColor.WHITE)){
            return new Coordinate(BoardFile.f, 1);
        } else {
            return new Coordinate(BoardFile.f, 8);
        }
    }



    private Coordinate getKingShortEndingCoordinate(PieceColor pieceColor) {
        if(pieceColor.equals(PieceColor.WHITE)){
            return new Coordinate(BoardFile.g, 1);
        } else {
            return new Coordinate(BoardFile.g, 8);
        }
    }


    private Coordinate getKingLongEndingCoordinate(PieceColor pieceColor) {
        if(pieceColor.equals(PieceColor.WHITE)){
            return new Coordinate(BoardFile.c, 1);
        } else {
            return new Coordinate(BoardFile.c, 8);
        }
    }
    private Coordinate getRookLongEndingCoordinate(PieceColor pieceColor) {
        if(pieceColor.equals(PieceColor.WHITE)){
            return new Coordinate(BoardFile.d, 1);
        } else {
            return new Coordinate(BoardFile.d, 8);
        }
    }


}
