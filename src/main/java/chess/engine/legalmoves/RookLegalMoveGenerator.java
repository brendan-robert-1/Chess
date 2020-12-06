package chess.engine.legalmoves;

import chess.engine.Move;
import chess.engine.Offset;
import chess.model.Board;
import chess.model.Coordinate;
import chess.model.Piece;
import chess.model.PieceColor;

import java.util.ArrayList;
import java.util.List;

public class RookLegalMoveGenerator implements LegalMoveGeneratorInterface {
    @Override
    public List<Move> generateMoves(Board board, Coordinate startingCoordinate) {
        List<Move> legalMoves = new ArrayList<>();
        PieceColor friendlyPieceColor = board.getPieceMap().get(startingCoordinate).getPieceColor();
        for(Offset offset : generateOffsets()){
            Coordinate targetCoordinate = startingCoordinate.offsetBy(0, 0);
            boolean moreMoves = true;
            while(moreMoves){
                boolean inBounds = LegalMoveGenerator.isInBounds(offset, targetCoordinate);
                if(inBounds){
                    targetCoordinate = targetCoordinate.offsetBy(offset);
                    Piece pieceAt = board.getPieceMap().get(targetCoordinate);
                    if(pieceAt != null && pieceAt.getPieceColor().equals(PieceColor.opposite(friendlyPieceColor))){
                        Move move = new Move(startingCoordinate, targetCoordinate);
                        legalMoves.add(move);
                        moreMoves = false;
                        break;
                    }
                    if(pieceAt != null && pieceAt.getPieceColor().equals(friendlyPieceColor)){
                        moreMoves = false;
                        break;
                    }
                    if(pieceAt == null){
                        Move move = new Move(startingCoordinate, targetCoordinate);
                        legalMoves.add(move);
                    }
                }else{
                    moreMoves = false;
                    break;
                }

            }
        }
        return legalMoves;
    }
    private List<Offset> generateOffsets() {
        List<Offset> offsets = new ArrayList<>();
        offsets.add(new Offset(0,1));
        offsets.add(new Offset(0,-1));
        offsets.add(new Offset(-1,0));
        offsets.add(new Offset(1,0));
        return offsets;
    }
}
