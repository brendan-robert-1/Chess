package chess.model;

import chess.engine.ConsolePrinter;
import chess.engine.Move;
import chess.engine.legalmoves.LegalMoveGenerator;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

public class Board {
    private ImmutableMap<Coordinate, Piece> pieceMap;

    public Board(ImmutableMap<Coordinate, Piece> pieceMap){
        this.pieceMap = pieceMap;
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
    public void setPieceMap(ImmutableMap<Coordinate, Piece> pieceMap){
        this.pieceMap = ImmutableMap.copyOf(pieceMap);
    }



    @Override
    public String toString() {
        return ConsolePrinter.boardtoString(this);
    }

    public boolean isDraw(){
        return false;
        //TODO implement this
    }



    public boolean isCheckMate() {
        List<Move> legalMoves = LegalMoveGenerator.generateLegalMovesFor(this, PieceColor.WHITE);
        List<Move> opponentMoves = LegalMoveGenerator.generateLegalMovesFor(this, PieceColor.BLACK);
        return legalMoves.size() == 0  || opponentMoves.size() == 0;
    }

    public boolean inEndGame(){
        for(Map.Entry<Coordinate, Piece> entry : getPieceMap().entrySet()){
            if(entry.getValue().getPieceType().equals(PieceType.QUEEN)){
                return false;
            }
        }
        return true;
    }
}
