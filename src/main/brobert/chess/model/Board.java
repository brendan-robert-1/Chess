package chess.model;

import chess.engine.ConsolePrinter;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class Board {
    private ImmutableMap<Coordinate, Piece> pieceMap;

    public Board(ImmutableMap<Coordinate, Piece> pieceMap){
        this.pieceMap = pieceMap;
    }

    public Board(Map<Coordinate, Piece> pieceMap){
        this.pieceMap = ImmutableMap.copyOf(pieceMap);

    }
    public Board(){
        this.pieceMap = ImmutableMap.<Coordinate, Piece>builder().build();
    }

    public Piece pieceAt(Coordinate coordinate){
        return pieceMap.get(coordinate);
    }

    public ImmutableMap<Coordinate, Piece> getPieceMap(){
        return this.pieceMap;
    }
    public void setPieceMap(Map<Coordinate, Piece> pieceMap){
        this.pieceMap = ImmutableMap.copyOf(pieceMap);
    }



    @Override
    public String toString() {
        return ConsolePrinter.prettyPrintedBoard(this);
    }
}
