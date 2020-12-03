package chess.engine;


import chess.model.*;

public class ConsolePrinter {

    public static void prettyPrintBoard(Board board){
        String prettyPrintedBoard = prettyPrintedBoard(board);
        System.out.println(prettyPrintedBoard);
    }

    public static String prettyPrintedBoard(Board board){
        StringBuilder sb = new StringBuilder();
        sb.append("   _______________________________________\n");
        for(int rank = 8; rank >= 1; rank--){
            sb.append(rank + " |");
            for(int file = 1; file <= 8; file++){
                Piece piece = board.getPieceMap().get(new Coordinate(BoardFile.boardFile(file), rank));
                if(piece != null){
                    String pieceId = consoleId(piece);
                    sb.append(" "+pieceId+" |");
                }else{
                    sb.append("    |");
                }

            }
            sb.append("\n");
            if(rank > 1) {
                sb.append(rankDelimiter());
            }
        }



        sb.append("   ---------------------------------------\n");
        sb.append(fileLegend());
        return sb.toString();
    }

    private static String boardTopBottom (){
        return "   _______________________________________\n";
    }

    private static String rankDelimiter(){
        return "  |----+----+----+----+----+----+----+----|\n";
    }
    private static String fileLegend(){
        return "    a    b    c    d    e    f    g    h";
    }
    private static String consoleId(Piece piece){
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
