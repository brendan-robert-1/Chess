package chess.engine;

import chess.engine.exception.IllegalMoveException;
import chess.engine.legalmoves.KingLegalMoveGenerator;
import chess.model.*;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Set;

public class MoveExecutor {

    KingLegalMoveGenerator kingLegalMoveGenerator = new KingLegalMoveGenerator();

    public Board executeMove(Move move, Board board)  {
        Map<Coordinate, Piece> mutableMap = Maps.newHashMap(board.getPieceMap());
        Piece pieceToMove = mutableMap.get(move.startingCoordinate);
        if(move.isCastleLong){
            return castleShort(pieceToMove.getPieceColor(), board);
        }
        if(move.isCastleShort){
            return castleLong(pieceToMove.getPieceColor(), board);
        }
        boolean isPawn = pieceToMove.getPieceType().equals(PieceType.PAWN);
        boolean isPromotionRank = move.endingCoordinate.getRank() == 8 || move.endingCoordinate.getRank() == 1;
        Piece newPieceToMove = new Piece(pieceToMove.getPieceColor(), pieceToMove.getPieceType(), true);
        if( isPawn && isPromotionRank){
             newPieceToMove = new Piece(pieceToMove.getPieceColor(), PieceType.QUEEN, true);
        }
        mutableMap.remove(move.startingCoordinate);
        mutableMap.put(move.endingCoordinate, newPieceToMove);
        return new Board(ImmutableMap.<Coordinate, Piece>builder().putAll(mutableMap).build());
    }

    public Board castleShort(PieceColor pieceColor, Board board)  {
        boolean piecesHaventMoved = kingLegalMoveGenerator.piecesHaventMovedShort(board, pieceColor);
        boolean noPiecesInWay = kingLegalMoveGenerator.noPiecesInWayShort(board, pieceColor);
        if(piecesHaventMoved == false || noPiecesInWay == false ){
            throw new RuntimeException("cant castle");
        }
        Coordinate kingStart = kingLegalMoveGenerator.getKingStartingCoordinate(pieceColor);
        Coordinate rookStart = kingLegalMoveGenerator.getShortRookStartingCoordinate(pieceColor);
        Coordinate kingEnd = kingLegalMoveGenerator.getKingShortEndingCoordinate(pieceColor);
        Coordinate rookEnd = kingLegalMoveGenerator.getRookShortEndingCoordinate(pieceColor);
        Move kingMove = new Move(kingStart, kingEnd);
        Move rookMove = new Move(rookStart, rookEnd);
        Board afterKing = executeMove(kingMove, board);
        Board afterRook = executeMove(rookMove, afterKing);
        return afterRook;
    }

    public Board castleLong(PieceColor pieceColor, Board board)  {
        boolean piecesHaventMoved = kingLegalMoveGenerator.piecesHaventMovedLong(board, pieceColor);
        boolean noPiecesInWay = kingLegalMoveGenerator.noPiecesInWayLong(board, pieceColor);
        if(piecesHaventMoved == false || noPiecesInWay == false) {
            throw new RuntimeException("cant castle");
        }
        Coordinate kingStart = kingLegalMoveGenerator.getKingStartingCoordinate(pieceColor);
        Coordinate rookStart = kingLegalMoveGenerator.getLongRookStartingCoordinate(pieceColor);
        Coordinate kingEnd = kingLegalMoveGenerator.getKingLongEndingCoordinate(pieceColor);
        Coordinate rookEnd = kingLegalMoveGenerator.getRookLongEndingCoordinate(pieceColor);
        Move kingMove = new Move(kingStart, kingEnd);
        Move rookMove = new Move(rookStart, rookEnd);
        Board afterKing = executeMove(kingMove, board);
        Board afterRook = executeMove(rookMove, afterKing);
        return afterRook;
    }
}
