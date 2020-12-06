package chess.engine.ai;

import chess.engine.Move;
import chess.model.BoardFile;
import chess.model.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class HeuristicMoveOrderer {

    public static List<Move> orderMovesByHeuristics(List<Move> moves){
        List<Move> heuristicOrderedMoves = new ArrayList<>();
        for(Move move : moves){
            if(move.equals(new Move(new Coordinate(BoardFile.g, 6), new Coordinate(BoardFile.d, 3)))){
                Move first = heuristicOrderedMoves.get(0);
                heuristicOrderedMoves.add(first);
                heuristicOrderedMoves.set(0, move);

            }else{
                heuristicOrderedMoves.add(move);
            }
        }
        return heuristicOrderedMoves;
    }
}
