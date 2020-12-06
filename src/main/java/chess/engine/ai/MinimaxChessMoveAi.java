package chess.engine.ai;

import chess.engine.Move;
import chess.engine.MoveExecutor;
import chess.engine.exception.IllegalMoveException;
import chess.engine.legalmoves.LegalMoveGenerator;
import chess.model.*;

import java.util.*;

public class MinimaxChessMoveAi implements ChessMoveAi{

    public static final int DEPTH = 4;
    private MoveExecutor moveExecutor = new MoveExecutor();
    private Random rand = new Random();

    @Override
    public Move generateMove(Board board, PieceColor pieceColor) {
        boolean isWhite = pieceColor.equals(PieceColor.WHITE)  ;
        return minimax(board, DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, isWhite).move;
    }


    private ScoredMove minimax(Board board, int depth, int alpha, int beta, boolean maximizing) {
        PieceColor pieceColor = returnColorForMaximizing(maximizing);
        List<Move> legalMoves = LegalMoveGenerator.generateLegalMovesFor(board, pieceColor);
        List<Move> heuristicOrdered = HeuristicMoveOrderer.orderMovesByHeuristics(legalMoves);
        if(depth == 0 || board.isDraw() || board.isCheckMate()){
            return new ScoredMove(null, evaluateBoard(board));
        }
        Move bestMove = heuristicOrdered.get(0);
        if(maximizing){
            int maxEval = Integer.MIN_VALUE;
            for(Move move : heuristicOrdered){
                Board boardAfter = moveExecutor.executeMove(move, board);
                ScoredMove currentEval = minimax(boardAfter, depth - 1, alpha, beta, false);
                if(currentEval.score > maxEval){
                    maxEval = currentEval.score;
                    bestMove = move;
                }
                if(currentEval.score == maxEval && rand.nextBoolean()){
                    maxEval = currentEval.score;
                    bestMove = move;
                }
                alpha = Math.max(alpha, currentEval.score);
                if(beta <= alpha){
                    break;
                }
            }
            return new ScoredMove(bestMove, maxEval);
        } else {
            int minEval = Integer.MAX_VALUE;
            for(Move move : heuristicOrdered){
                Board boardAfter = moveExecutor.executeMove(move, board);
                ScoredMove currentEval = minimax(boardAfter, depth - 1, alpha, beta, true);
                if(currentEval.score < minEval){
                    minEval = currentEval.score;
                    bestMove = move;
                }
                if(currentEval.score == minEval && rand.nextBoolean()){
                    minEval = currentEval.score;
                    bestMove = move;
                }
                beta = Math.min(beta, currentEval.score);
                if(beta <= alpha){
                    break;
                }
            }
            return new ScoredMove(bestMove, minEval);
        }
    }

    /*
        Higher value white is doing better lower black is
     */
    public int evaluateBoard(Board board){
        int score = 0;
        for(Map.Entry<Coordinate, Piece> entry : board.getPieceMap().entrySet()){
            Coordinate coordinate = entry.getKey();
            Piece piece = entry.getValue();
            if(piece.getPieceColor().equals(PieceColor.WHITE)){
                score += PieceWeight.getStaticWeight(piece.getPieceType(), piece.getPieceColor(), coordinate, board.inEndGame());
            } else {
                score += -PieceWeight.getStaticWeight(piece.getPieceType(), piece.getPieceColor(), coordinate, board.inEndGame());
            }
        }
        return score;
    }

    private PieceColor returnColorForMaximizing(boolean isMax){
        if(isMax){
            return PieceColor.WHITE;
        }else{
            return PieceColor.BLACK;
        }
    }
}
