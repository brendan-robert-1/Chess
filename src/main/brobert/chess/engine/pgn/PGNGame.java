package chess.engine.pgn;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PGNGame {

    private String pgn;
    public PGNGame(String pgn){
        this.pgn = pgn;
    }
    public List<String> getMoves() {
        return tokenizeMoves(pgn);
    }

    private List<String> tokenizeMoves(String pgn) {
        pgn = pgn.replaceAll("\\[.*\\]", "");
        pgn = pgn.replaceAll("\\{.*\\}", "");
        pgn = pgn.replaceAll("[\\n\\r]", " ");
        String[] movesarr = pgn.split("\\ ");
        List<String> movesList = new ArrayList<>(Arrays.asList(movesarr));
        movesList.remove(0);
        List<String> removals = new ArrayList<>();
        for(String move : movesList){
            if(move.contains(".") || move.isEmpty()){
                removals.add(move);
            }
        }
        movesList.removeAll(removals);
        return movesList;
    }
}
