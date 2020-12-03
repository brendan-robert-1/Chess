package chess.engine.legalmoves;

import chess.engine.Move;
import chess.engine.Offset;
import chess.model.Board;
import chess.model.Coordinate;
import chess.model.Piece;
import chess.model.PieceColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KnightLegalMoveGenerator implements LegalMoveGeneratorInterface{

    @Override
    public List<Move> generateMoves(Board board, Coordinate startingCoordinate) {
        PieceColor friendlyColor = board.getPieceMap().get(startingCoordinate).getPieceColor();
        List<Move> legalMoves = new ArrayList<>();
        List<Offset> offsets  = generateOffsets();
        for(Offset offset : offsets){
            if(LegalMoveGenerator.isInBounds(offset, startingCoordinate)){
                Coordinate endingCoordinate = startingCoordinate.offsetBy(offset.file, offset.rank);
                Move move = new Move(startingCoordinate, endingCoordinate);
                boolean friendlyPieceAt = false;
                Piece pieceAtEnd = board.getPieceMap().get(endingCoordinate);
                if(pieceAtEnd != null && pieceAtEnd.getPieceColor().equals(friendlyColor)){
                    friendlyPieceAt = true;
                }
                if(!LegalMoveGenerator.putsSelfInCheck(move, board) && !friendlyPieceAt){
                    legalMoves.add(move);
                }
            }

        }
        return legalMoves;
    }



    private List<Offset> generateOffsets() {
        List<Offset> offsets = new ArrayList<>();
        offsets.add(new Offset(2,1));
        offsets.add(new Offset(2,-1));
        offsets.add(new Offset(-2,1));
        offsets.add(new Offset(-2,-1));
        offsets.add(new Offset(1,2));
        offsets.add(new Offset(1,-2));
        offsets.add(new Offset(-1,2));
        offsets.add( new Offset(-1,-2));
        return offsets;
    }
}
