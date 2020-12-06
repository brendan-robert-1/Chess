package chess;


import chess.engine.BoardGenerator;

import chess.engine.Game;
import chess.engine.ai.PieceWeight;
import chess.engine.exception.IllegalMoveException;
import chess.model.BoardFile;
import chess.model.Coordinate;
import chess.model.PieceColor;
import chess.model.PieceType;


public class Main {

    public static void main(String[] args) throws IllegalMoveException {
        BoardGenerator boardGenerator = new BoardGenerator();
        Game game = Game.newRandomGameVsMinimax();
        game.startAiversusAiGame();
    }
}
