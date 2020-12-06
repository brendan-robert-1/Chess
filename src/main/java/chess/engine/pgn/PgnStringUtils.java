package chess.engine.pgn;

import chess.engine.Move;
import chess.model.Board;
import chess.model.Piece;
import chess.model.PieceType;

public class PgnStringUtils {

    public static String pgnFromMove(Move move, Board board){
        StringBuilder sb = new StringBuilder();
        Piece piece = board.pieceAt(move.startingCoordinate);
        Piece target = board.pieceAt(move.endingCoordinate);
        String pieceType = getQualifierFromType(piece.getPieceType());
        String kill = target != null ? "x" : "";
        String targetCoord =move.endingCoordinate.getFile().getFile() + move.endingCoordinate.getRank();
        sb.append(pieceType);
        sb.append(kill);
        sb.append(targetCoord);

        //TODO if check append + if mate append #
        //TODO handle disambiguation
        return sb.toString();
    }



    private static String getQualifierFromType(PieceType pieceType) {
        switch(pieceType){
            case KNIGHT: return "N";
            case BISHOP: return "B";
            case QUEEN: return "Q";
            case ROOK: return "R";
            case PAWN: return "";
            case KING: return "K";
        }
        throw new IllegalArgumentException();
    }
}
