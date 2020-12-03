package chess.engine;

import chess.model.Coordinate;
import chess.model.PieceType;

import java.util.Objects;

public class Move{
    public Coordinate startingCoordinate;
    public Coordinate endingCoordinate;
    public PieceType pawnPromotionTo;

    public Move(Coordinate startingCoordinate, Coordinate endingCoordinate){
        this.startingCoordinate = startingCoordinate;
        this.endingCoordinate = endingCoordinate;
    }

    public Move(Coordinate startingCoordinate, Coordinate endingCoordinate, PieceType pawnPromotionTo){
        this.startingCoordinate = startingCoordinate;
        this.endingCoordinate = endingCoordinate;
        this.pawnPromotionTo = pawnPromotionTo;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return Objects.equals(startingCoordinate, move.startingCoordinate) && Objects.equals(endingCoordinate, move.endingCoordinate) && pawnPromotionTo == move.pawnPromotionTo;
    }



    @Override
    public int hashCode() {
        return Objects.hash(startingCoordinate, endingCoordinate, pawnPromotionTo);
    }
}
