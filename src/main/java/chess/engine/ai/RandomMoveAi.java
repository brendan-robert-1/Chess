package chess.engine.ai;

import chess.engine.Move;
import chess.engine.ai.ChessMoveAi;
import chess.engine.legalmoves.LegalMoveGenerator;
import chess.model.Board;
import chess.model.PieceColor;

import java.util.List;
import java.util.Random;

public class RandomMoveAi implements ChessMoveAi {
        public Move generateMove(Board board, PieceColor pieceColor){
            List<Move> allLegalComputerMoves = LegalMoveGenerator.generateLegalMovesFor(board, pieceColor);
            Random r = new Random();
            int i = r.nextInt(allLegalComputerMoves.size());
            return allLegalComputerMoves.get(i);
        }
    }

