package chess.engine.legalmoves;

import chess.engine.Move;
import chess.engine.MoveExecutor;
import chess.engine.Offset;
import chess.model.*;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LegalMoveGenerator {
    static final KingLegalMoveGenerator kingLegalMoveGenerator = new KingLegalMoveGenerator();
    static final PawnLegalMoveGenerator pawnLegalMoveGenerator = new PawnLegalMoveGenerator();
    static final RookLegalMoveGenerator rookLegalMoveGenerator = new RookLegalMoveGenerator();
    static final QueenLegalMoveGenerator queenLegalMoveGenerator = new QueenLegalMoveGenerator();
    static final BishopLegalMoveGenerator bishopLegalMoveGenerator = new BishopLegalMoveGenerator();
    static final KnightLegalMoveGenerator knightLegalMoveGenerator = new KnightLegalMoveGenerator();
    static final MoveExecutor moveExecutor = new MoveExecutor();

    public static boolean isLegalMove(Move move, Board board){
        List<Move> legalMoves = generateLegalMoves(board, move.startingCoordinate);
        if(legalMoves.contains(move)){
            return true;
        } else {
            return false;
        }
    }

    public static List<Move> generateLegalMovesFor(Board board, PieceColor pieceColor){
        List<Move> allMoves = new ArrayList<>();
        for(Coordinate coordinate : board.getPieceMap().keySet()){
            Piece pieceAt = board.getPieceMap().get(coordinate);
            if(pieceAt != null && pieceAt.getPieceColor().equals(pieceColor)){

            }
        }
        return allMoves;
    }

    public static List<Move> generateLegalMoves(Board board, Coordinate startingCoordinate){
        Piece piece = board.getPieceMap().get(startingCoordinate);
        if(piece == null){
            throw new IllegalArgumentException("no piece at starting coordinate: " + startingCoordinate);
        }
        LegalMoveGeneratorInterface generator = generator(piece.getPieceType());
        return generator.generateMoves(board, startingCoordinate);
    }


    private static LegalMoveGeneratorInterface generator(PieceType pieceType){
        switch(pieceType){
            case KING: return kingLegalMoveGenerator;
            case PAWN: return pawnLegalMoveGenerator;
            case ROOK: return rookLegalMoveGenerator;
            case QUEEN: return queenLegalMoveGenerator;
            case BISHOP: return bishopLegalMoveGenerator;
            case KNIGHT: return knightLegalMoveGenerator;
            default: throw new IllegalArgumentException("invalid pieceType");
        }
    }

    public static boolean isInBounds(Offset offset, Coordinate startingCoordinate){
        int newFile = offset.file + startingCoordinate.getFile().getNumericalFile();
        int newRank = offset.rank + startingCoordinate.getRank();
        return (newFile >=1 && newFile <=8) && (newRank >=1 && newRank <=8);
    }

    public static boolean putsSelfInCheck(Move move, Board board, PieceColor pieceColor){
        Board boardAfter = moveExecutor.executeMove(move, board);
        List<Move> opponentResponses = generateLegalMovesFor(board, pieceColor);
        return false;
    }
    public static boolean isCheck(Move move, Board Board, PieceColor pieceColor){
        return false;
    }
}
