package chess.engine;


import chess.model.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ConsolePrinter {

    public static final String WHITE_KING = "\u2654";
    public static final String WHITE_QUEEN = "\u2655";
    public static final String WHITE_ROOK = "\u2656";
    public static final String WHITE_BISHOP = "\u2657";
    public static final String WHITE_KNIGHT = "\u2658";
    public static final String WHITE_PAWN = "\u2659";

    public static final String BLACK_KING = "\u265A";
    public static final String BLACK_QUEEN = "\u265B";
    public static final String BLACK_ROOK = "\u265C";
    public static final String BLACK_BISHOP = "\u265D";
    public static final String BLACK_KNIGHT = "\u265E";
    public static final String BLACK_PAWN = "\u265F";
    public static final String SPACING =" ";
    public static final String FOUR_SPACE = SPACING + SPACING + SPACING + SPACING;

    public static void prettyPrintBoard(Board board, PieceColor fromThePerspectiveOf){
        String prettyPrintedBoard = prettyPrintedBoard(board, fromThePerspectiveOf);
        System.out.println(prettyPrintedBoard);
    }

    public static String prettyPrintedBoard(Board board, PieceColor fromThePerspectiveOf){
        if(fromThePerspectiveOf.equals(PieceColor.WHITE)){
            return prettyPrintedBoardWhite(board);
        }else{
            return prettyPrintedBoardBlack(board);
        }
    }

    private static String prettyPrintedBoardWhite(Board board){
        StringBuilder sb = new StringBuilder();
        sb.append(SPACING + SPACING + SPACING+"_______________________________________\n");
        for(int rank = 8; rank >= 1; rank--){
            sb.append(rank + SPACING + "|");
            for(int file = 1; file <= 8; file++){
                Piece piece = board.getPieceMap().get(new Coordinate(BoardFile.boardFile(file), rank));
                String lastCharacter = "";
                if(file != 8){lastCharacter = "|";}
                if(piece != null){
                    String pieceId = getUnicode(piece);

                    sb.append(SPACING +pieceId + SPACING + lastCharacter);
                }else{
                    sb.append(SPACING + SPACING + SPACING + SPACING + lastCharacter);
                }
            }
            sb.append("\n");
            if(rank > 1) {
                sb.append(rankDelimiter());
            }
        }
        sb.append(SPACING + SPACING + SPACING+ "---------------------------------------\n");
        sb.append(fileLegendWhite());
        return sb.toString();
    }

    private static String prettyPrintedBoardBlack(Board board){
        StringBuilder sb = new StringBuilder();
        sb.append(SPACING + SPACING + SPACING+"_______________________________________\n");
        for(int rank = 1; rank <= 8; rank++){
            sb.append(rank + SPACING + "|");
            for(int file = 8; file >= 1; file--){
                Piece piece = board.getPieceMap().get(new Coordinate(BoardFile.boardFile(file), rank));
                String lastCharacter = "";
                if(file != 1){lastCharacter = "|";}
                if(piece != null){
                    String pieceId = getUnicode(piece);

                    sb.append(SPACING +pieceId + SPACING + lastCharacter);
                }else{
                    sb.append(SPACING + SPACING + SPACING + SPACING + lastCharacter);
                }
            }
            sb.append("\n");
            if(rank <8) {
                sb.append(rankDelimiter());
            }
        }
        sb.append(SPACING + SPACING + SPACING+ "---------------------------------------\n");
        sb.append(fileLegendBlack());
        return sb.toString();
    }


    public static String boardtoString(Board board){
        StringBuilder sb = new StringBuilder();
        sb.append("   _______________________________________\n");
        for(int rank = 8; rank >= 1; rank--){
            sb.append(rank +" |");
            for(int file = 1; file <= 8; file++){
                Piece piece = board.getPieceMap().get(new Coordinate(BoardFile.boardFile(file), rank));
                String lastCharacter = "";
                if(file != 8){lastCharacter = "|";}
                if(piece != null){
                    String pieceId = getCharacterRepresentations(piece);

                    sb.append(" " +pieceId + " " + lastCharacter);
                }else{
                    sb.append("    " + lastCharacter);
                }
            }
            sb.append("\n");
            if(rank > 1) {
                sb.append(rankDelimiter());
            }
        }
        sb.append("   ---------------------------------------\n");
        sb.append(fileLegendWhite());
        return new String(sb.toString().getBytes(), StandardCharsets.UTF_8);
    }

    public String getWhitesCapturedPieces(Board board){
        return "";
    }

    public String getBlacksCapturedPieces(Board board){
        return "";
    }




    private static String rankDelimiter(){
        return SPACING + SPACING +"|----+----+----+----+----+----+----+----|\n";
    }
    private static String fileLegendWhite(){
        return FOUR_SPACE + "a"+FOUR_SPACE+"b"+FOUR_SPACE+"c"+FOUR_SPACE+"d"+FOUR_SPACE+"e"+FOUR_SPACE+"f"+FOUR_SPACE+"g"+FOUR_SPACE+"h";
    }

    private static String fileLegendBlack(){
        return FOUR_SPACE + "h"+FOUR_SPACE+"g"+FOUR_SPACE+"f"+FOUR_SPACE+"e"+FOUR_SPACE+"d"+FOUR_SPACE+"c"+FOUR_SPACE+"b"+FOUR_SPACE+"a";
    }

    private static String getCharacterRepresentations(Piece piece){
        StringBuilder sb = new StringBuilder();
        switch(piece.getPieceColor()){
            case WHITE: sb.append("w"); break;
            case BLACK: sb.append("b"); break;
        }
        switch(piece.getPieceType()){
            case PAWN: sb.append("P"); break;
            case KNIGHT: sb.append("N"); break;
            case BISHOP: sb.append("B"); break;
            case ROOK: sb.append("R"); break;
            case QUEEN: sb.append("Q"); break;
            case KING: sb.append("K"); break;
        }
        return sb.toString();
    }

    private static String getUnicode(Piece piece) {
        PieceColor pieceColor = piece.getPieceColor();
        PieceType pieceType = piece.getPieceType();
        if (pieceColor.equals(PieceColor.WHITE)) {
            if (pieceType.equals(PieceType.KING)) {
                return WHITE_KING;
            }
            if (pieceType.equals(PieceType.QUEEN)) {
                return WHITE_QUEEN;
            }
            if (pieceType.equals(PieceType.ROOK)) {
                return WHITE_ROOK;
            }
            if (pieceType.equals(PieceType.BISHOP)) {
                return WHITE_BISHOP;
            }
            if (pieceType.equals(PieceType.KNIGHT)) {
                return WHITE_KNIGHT;
            }
            if (pieceType.equals(PieceType.PAWN)) {
                return WHITE_PAWN;
            }
        } else {
            if (pieceType.equals(PieceType.KING)) {
                return BLACK_KING;
            }
            if (pieceType.equals(PieceType.QUEEN)) {
                return BLACK_QUEEN;
            }
            if (pieceType.equals(PieceType.ROOK)) {
                return BLACK_ROOK;
            }
            if (pieceType.equals(PieceType.BISHOP)) {
                return BLACK_BISHOP;
            }
            if (pieceType.equals(PieceType.KNIGHT)) {
                return BLACK_KNIGHT;
            }
            if (pieceType.equals(PieceType.PAWN)) {
                return BLACK_PAWN;
            }
        }
            throw new IllegalArgumentException("No unicode found for piece");
    }
}
/*
   _______________________________________
8 | wR | wN | wB | wQ | wK | wB | wN | wR |
  |----+----+----+----+----+----+----+----|
7 |    |    |    |    |    |    |    |    |
  |----+----+----+----+----+----+----+----|
6 |    |    |    |    |    |    |    |    |
  |----+----+----+----+----+----+----+----|
5 |    |    |    |    |    |    |    |    |
  |----+----+----+----+----+----+----+----|
4 |    |    |    |    |    |    |    |    |
  |----+----+----+----+----+----+----+----|
3 |    |    |    |    |    |    |    |    |
  |----+----+----+----+----+----+----+----|
2 |    |    |    |    |    |    |    |    |
  |----+----+----+----+----+----+----+----|
1 |    |    |    |    |    |    |    |    |
   ---------------------------------------
   a    b    c    d    e    f    g    h

*/
