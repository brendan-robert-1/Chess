package chess.engine.legalmoves;

import chess.engine.Move;
import chess.model.Board;
import chess.model.Coordinate;

import java.util.List;

public interface LegalMoveGeneratorInterface {
    List<Move> generateMoves(Board board, Coordinate startingCoordinate);
}
