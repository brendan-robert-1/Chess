package chess;


import chess.engine.BoardGenerator;
import chess.engine.ConsolePrinter;
import chess.model.Board;

public class Main {

    public static void main(String[] args){
        BoardGenerator boardGenerator = new BoardGenerator();
        Board board = boardGenerator.generateBoardFromPGN("KarpovKasparov.pgn");
    }

}
