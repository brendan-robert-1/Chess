package chess.engine.ai;
import chess.model.BoardFile;
import chess.model.Coordinate;
import chess.model.PieceColor;
import chess.model.PieceType;
import org.junit.Test;
import static org.junit.Assert.*;

public class PieceWeightTest {


    @Test
    public void testBlackKnightf7(){
      Coordinate f6 = new Coordinate(BoardFile.f, 6);
      int actual = PieceWeight.getWeight(PieceType.KNIGHT, PieceColor.BLACK.BLACK, f6, false);
      assertEquals(330, actual);
    }

    @Test
    public void testBlackKnighte7(){
        Coordinate f6 = new Coordinate(BoardFile.e, 7);
        int actual = PieceWeight.getWeight(PieceType.KNIGHT, PieceColor.BLACK.BLACK, f6, false);
        assertEquals(325, actual);
    }


}
