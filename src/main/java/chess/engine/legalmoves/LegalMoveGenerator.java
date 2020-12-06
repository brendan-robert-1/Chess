package chess.engine.legalmoves;

import chess.engine.Move;
import chess.engine.MoveExecutor;
import chess.engine.Offset;
import chess.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LegalMoveGenerator {
    static final KingLegalMoveGenerator kingLegalMoveGenerator = new KingLegalMoveGenerator();
    static final PawnLegalMoveGenerator pawnLegalMoveGenerator = new PawnLegalMoveGenerator();
    static final RookLegalMoveGenerator rookLegalMoveGenerator = new RookLegalMoveGenerator();
    static final QueenLegalMoveGenerator queenLegalMoveGenerator = new QueenLegalMoveGenerator();
    static final BishopLegalMoveGenerator bishopLegalMoveGenerator = new BishopLegalMoveGenerator();
    static final KnightLegalMoveGenerator knightLegalMoveGenerator = new KnightLegalMoveGenerator();
    static final MoveExecutor moveExecutor = new MoveExecutor();

    public static boolean isLegalMove(Move move, Board board, PieceColor pieceColor){
        List<Move> legalMoves = generateLegalMoves(board, move.startingCoordinate);
        List<Move> verifyChecks = verifyDoesntPutInCheck(legalMoves, board, pieceColor);
        if(verifyChecks.contains(move)){
            return true;
        } else {
            return false;
        }
    }

    public static List<Move> generateLegalMovesFor(Board board, PieceColor pieceColor){
        List<Move> allPseudoMoves = generatePseudoLegalMovesFor(board, pieceColor);
        //return allPseudoMoves;
        return verifyDoesntPutInCheck(allPseudoMoves, board, pieceColor);
    }

    private static List<Move> generatePseudoLegalMovesFor(Board board, PieceColor pieceColor){
        List<Move> allPseudoMoves = new ArrayList<>();
        for(Coordinate coordinate : board.getPieceMap().keySet()){
            Piece pieceAt = board.getPieceMap().get(coordinate);
            if(pieceAt != null && pieceAt.getPieceColor().equals(pieceColor)){
                List<Move> movesForPiece = generator(pieceAt.getPieceType()).generateMoves(board, coordinate);
                allPseudoMoves.addAll(movesForPiece);
            }
        }
        return allPseudoMoves;
    }

    public static List<Move> verifyDoesntPutInCheck(List<Move> possibleMoves, Board board, PieceColor pieceColor){
        Set<Move> movesVerifiedForChecks = new HashSet<>();
        for(Move possibleMove : possibleMoves){
            Board boardAfter = moveExecutor.executeMove(possibleMove, board);
            List<Move> enemyOptionsAfterMove = generatePseudoLegalMovesFor(boardAfter, PieceColor.opposite(pieceColor));
            boolean enemyCanMate = false;
            for(Move enemyOption : enemyOptionsAfterMove){
                    Piece targetPiece = boardAfter.getPieceMap().get(enemyOption.endingCoordinate);
                    boolean targetIsOurKing = targetPiece != null && targetPiece.getPieceType().equals(PieceType.KING) && targetPiece.getPieceColor().equals(pieceColor);
                    if(targetIsOurKing){
                        enemyCanMate = true;
                        break;
                    }
                }
            if(enemyOptionsAfterMove.size() == 0 || !enemyCanMate){
                movesVerifiedForChecks.add(possibleMove);
            }
        }
        return new ArrayList<>(movesVerifiedForChecks);
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
}
