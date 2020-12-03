package chess.model;

import chess.engine.Offset;
import com.google.gson.GsonBuilder;

import java.util.Objects;

public class Coordinate {

    private BoardFile file;
    private int rank;

    public Coordinate(BoardFile file, int rank){
        this.rank = rank;
        this.file = file;
    }

    public Coordinate (int file, int rank){
        this.file = BoardFile.boardFile(file);
        this.rank = rank;
    }
    public Coordinate offsetBy(int fileOffset, int rankOffset){
       return new Coordinate(BoardFile.boardFile(file.getNumericalFile() + fileOffset), rank + rankOffset);
    }

    public Coordinate offsetBy(Offset offset){
        return new Coordinate(BoardFile.boardFile(file.getNumericalFile() + offset.file), rank + offset.rank);
    }

    public int getRank() {
        return rank;
    }



    public BoardFile getFile() {
        return file;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return rank == that.rank && file == that.file;
    }



    @Override
    public int hashCode() {
        return Objects.hash(rank, file);
    }



    @Override
    public String toString() {
        return ""+file + rank+"";
    }
}
