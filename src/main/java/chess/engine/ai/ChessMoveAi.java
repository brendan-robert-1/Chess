package chess.engine.ai;

import chess.engine.Move;
import chess.model.Board;
import chess.model.PieceColor;

import java.util.List;

public interface ChessMoveAi {

    Move generateMove( Board board, PieceColor pieceColor);
}
