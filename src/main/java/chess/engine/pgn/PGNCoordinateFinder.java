package chess.engine.pgn;

import chess.engine.Move;
import chess.engine.legalmoves.LegalMoveGenerator;
import chess.model.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class PGNCoordinateFinder {

    public Coordinate determineEndingCoordinate(String pgnMove) {
        String strippedPgnMove = stripPgnMove(pgnMove);
        String substring = strippedPgnMove.substring(Math.max(strippedPgnMove.length() - 2, 0));
        int rank = Integer.parseInt(String.valueOf(substring.charAt(1)));
        BoardFile file = BoardFile.boardFile(String.valueOf(substring.charAt(0)));
        return new Coordinate(file, rank);
    }


    public Coordinate determineStartingCoordinateOfPgnMove(String pgnMove, Piece piece, Coordinate endingCoordinate, Board board) {
        String strippedPgnMove = stripPgnMove(pgnMove);
        Coordinate startingCoordinate = null;
        Map<Coordinate, Piece> pieceMap = board.getPieceMap();
        for(Coordinate coordinate : pieceMap.keySet()){
            Piece currentPiece = pieceMap.get(coordinate);
            String ambiguousChar = "";
            int ambiguousRank = 0;
            boolean isAmbiguousMove  = isAmbiguousMove(strippedPgnMove);
            boolean isDoubleAmbiguous = false;
            if(isAmbiguousMove){
                if(pgnMove.length() == 5){
                    ambiguousChar = String.valueOf(strippedPgnMove.charAt(1));
                    ambiguousRank = Integer.valueOf(strippedPgnMove.charAt(2));
                    isDoubleAmbiguous = true;
                }else {
                    ambiguousChar = String.valueOf(strippedPgnMove.charAt(1));
                    if(StringUtils.isNumeric(ambiguousChar)){
                        ambiguousRank = Integer.valueOf(ambiguousChar);
                    }
                }

            }
            boolean isFriendlyPiece = currentPiece.getPieceType().equals(piece.getPieceType()) && currentPiece.getPieceColor().equals(piece.getPieceColor());
            boolean isStartingPiece = false;
            if(isFriendlyPiece){
                if(isAmbiguousMove){
                    if(isDoubleAmbiguous){
                        if(BoardFile.boardFile(ambiguousChar).equals(coordinate.getFile()) && ambiguousRank == coordinate.getRank()){
                            isStartingPiece = true;
                        }
                    }else{
                        if(ambiguousRank == 0){
                            if(BoardFile.boardFile(ambiguousChar).equals(coordinate.getFile())){
                                isStartingPiece = true;
                            }
                        }else{
                            if(ambiguousRank == coordinate.getRank()){
                                isStartingPiece = true;
                            }
                        }
                    }
                }else{
                    isStartingPiece = true;
                }
            }
            if(isStartingPiece){
                if (LegalMoveGenerator.isLegalMove(new Move(coordinate, endingCoordinate), board, piece.getPieceColor())) {
                    startingCoordinate = coordinate;
                    break;
                }
            }
        }
        if(startingCoordinate == null){
            throw new RuntimeException("Could not determine starting coordinate");
        }
        return startingCoordinate;
    }



    public static String stripPgnMove(String pgnMove){
        String tempString = pgnMove;
        tempString = tempString.replace("x", "");
        tempString = tempString.replace("+", "");
        tempString = tempString.replace("#", "");
        return tempString;
    }

    public static boolean isAmbiguousMove(String pgnMove) {
        if(pgnMove.length() == 4){
            if(pgnMove.length() == 4){
                return true;
            }
        }
        return false;
    }

    public PieceType determinePieceType(String pgnMove) {
        String firstChar = String.valueOf(pgnMove.charAt(0));
        if(firstChar.equals("B")){
            return PieceType.BISHOP;
        }
        if(firstChar.equals("Q")){
            return PieceType.QUEEN;
        }
        if(firstChar.equals("K")){
            return PieceType.KING;
        }
        if(firstChar.equals("R")){
            return PieceType.ROOK;
        }
        if(firstChar.equals("N")){
            return PieceType.KNIGHT;
        }
        return PieceType.PAWN;
    }


}
