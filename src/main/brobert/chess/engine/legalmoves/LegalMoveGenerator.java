package chess.engine.legalmoves;

import chess.engine.Move;
import chess.engine.Offset;
import chess.model.Board;
import chess.model.Coordinate;
import chess.model.Piece;
import chess.model.PieceType;

import java.util.List;

public class LegalMoveGenerator {

    public static boolean isLegalMove(Move move, Board board){
        List<Move> legalMoves = generateLegalMoves(board, move.startingCoordinate);
        if(legalMoves.contains(move)){
            return true;
        }else{
            return false;
        }
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
            case KING: return new KingLegalMoveGenerator();
            case PAWN: return new PawnLegalMoveGenerator();
            case ROOK: return new RookLegalMoveGenerator();
            case QUEEN: return new QueenLegalMoveGenerator();
            case BISHOP: return new BishopLegalMoveGenerator();
            case KNIGHT: return new KnightLegalMoveGenerator();
            default: throw new IllegalArgumentException("invalid pieceType");
        }
    }

    public static boolean isInBounds(Offset offset, Coordinate startingCoordinate){
        int newFile = offset.file + startingCoordinate.getFile().getNumericalFile();
        int newRank = offset.rank + startingCoordinate.getRank();
        return (newFile >=1 && newFile <=8) && (newRank >=1 && newRank <=8);
    }

    public static boolean putsSelfInCheck(Move move, Board board){
        return false;
        //TODO write this
    }
}
