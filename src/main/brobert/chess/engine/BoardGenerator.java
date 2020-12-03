package chess.engine;


import chess.engine.legalmoves.LegalMoveGenerator;
import chess.engine.pgn.PGNGame;
import chess.model.*;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;


public class BoardGenerator {
    public Board generateStartingBoard(){
        Board board = new Board();
        Map<Coordinate, Piece> map = new HashMap<>();
        map.put(new Coordinate(BoardFile.a, 2), new Piece(PieceColor.WHITE, PieceType.PAWN));
        map.put(new Coordinate( BoardFile.b, 2), new Piece(PieceColor.WHITE, PieceType.PAWN));
        map.put(new Coordinate(BoardFile.c, 2), new Piece(PieceColor.WHITE, PieceType.PAWN));
        map.put(new Coordinate( BoardFile.d, 2), new Piece(PieceColor.WHITE, PieceType.PAWN));
        map.put(new Coordinate( BoardFile.e, 2), new Piece(PieceColor.WHITE, PieceType.PAWN));
        map.put(new Coordinate( BoardFile.f,2), new Piece(PieceColor.WHITE, PieceType.PAWN));
        map.put(new Coordinate( BoardFile.g, 2), new Piece(PieceColor.WHITE, PieceType.PAWN));
        map.put(new Coordinate( BoardFile.h,2), new Piece(PieceColor.WHITE, PieceType.PAWN));

        map.put(new Coordinate( BoardFile.a,7), new Piece(PieceColor.BLACK, PieceType.PAWN));
        map.put(new Coordinate( BoardFile.b,7), new Piece(PieceColor.BLACK, PieceType.PAWN));
        map.put(new Coordinate( BoardFile.c, 7), new Piece(PieceColor.BLACK, PieceType.PAWN));
        map.put(new Coordinate( BoardFile.d, 7), new Piece(PieceColor.BLACK, PieceType.PAWN));
        map.put(new Coordinate( BoardFile.e,7), new Piece(PieceColor.BLACK, PieceType.PAWN));
        map.put(new Coordinate( BoardFile.f, 7), new Piece(PieceColor.BLACK, PieceType.PAWN));
        map.put(new Coordinate( BoardFile.g,7), new Piece(PieceColor.BLACK, PieceType.PAWN));
        map.put(new Coordinate( BoardFile.h,7), new Piece(PieceColor.BLACK, PieceType.PAWN));

        map.put(new Coordinate( BoardFile.b, 1), new Piece(PieceColor.WHITE, PieceType.KNIGHT));
        map.put(new Coordinate( BoardFile.g,1), new Piece(PieceColor.WHITE, PieceType.KNIGHT));
        map.put(new Coordinate( BoardFile.b,8), new Piece(PieceColor.BLACK, PieceType.KNIGHT));
        map.put(new Coordinate( BoardFile.g, 8), new Piece(PieceColor.BLACK, PieceType.KNIGHT));

        map.put(new Coordinate( BoardFile.c,1), new Piece(PieceColor.WHITE, PieceType.BISHOP));
        map.put(new Coordinate( BoardFile.f,1), new Piece(PieceColor.WHITE, PieceType.BISHOP));
        map.put(new Coordinate( BoardFile.c,8), new Piece(PieceColor.BLACK, PieceType.BISHOP));
        map.put(new Coordinate( BoardFile.f, 8), new Piece(PieceColor.BLACK, PieceType.BISHOP));

        map.put(new Coordinate( BoardFile.a,1), new Piece(PieceColor.WHITE, PieceType.ROOK));
        map.put(new Coordinate( BoardFile.h, 1), new Piece(PieceColor.WHITE, PieceType.ROOK));
        map.put(new Coordinate(BoardFile.a, 8), new Piece(PieceColor.BLACK, PieceType.ROOK));
        map.put(new Coordinate( BoardFile.h, 8), new Piece(PieceColor.BLACK, PieceType.ROOK));

        map.put(new Coordinate( BoardFile.d,1), new Piece(PieceColor.WHITE, PieceType.QUEEN));
        map.put(new Coordinate( BoardFile.d,8), new Piece(PieceColor.BLACK, PieceType.QUEEN));

        map.put(new Coordinate( BoardFile.e,1), new Piece(PieceColor.WHITE, PieceType.KING));
        map.put(new Coordinate( BoardFile.e,8), new Piece(PieceColor.BLACK, PieceType.KING));
        board.setPieceMap(map);
        return board;
    }

    public Board generateBoardFromJsonFile(String jsonFile){
        InputStream is = getClass().getClassLoader().getResourceAsStream(jsonFile);
        String json = "";
        try {
            json = IOUtils.toString(is, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new GsonBuilder().create().fromJson(json, Board.class);
    }

    public Board generateBoardFromPGN(String pgnFile){
        InputStream is = getClass().getClassLoader().getResourceAsStream(pgnFile);
        String pgn = "";
        try {
            pgn = IOUtils.toString(is, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Generating board for game: ");
        System.out.println(pgn);
        PGNGame pgnGame = new PGNGame(pgn);
        Board board = generateStartingBoard();
        MoveExecutor moveExecutor = new MoveExecutor();
        PieceColor pieceColor = PieceColor.WHITE;
        int moveNumber = 0;
        for(String pgnMove : pgnGame.getMoves()){
            if(pieceColor.equals(PieceColor.WHITE)){
                moveNumber++;
            }
            if(pgnMove.equals("O-O") ){
                board = moveExecutor.castleShort(pieceColor, board);
            }
            else if(pgnMove.equals("O-O-O")){
                board = moveExecutor.castleLong(pieceColor, board);
            } else if(gameOver(pgnMove)){
                printEndGame(pgnMove);
                break;
            } else {
                PieceType pieceType = determinePieceType(pgnMove);
                Piece pieceToMove = new Piece(pieceColor, pieceType);
                Coordinate endingCoordinate = determineEndingCoordinate(pgnMove, board);
                Coordinate startingCoordinate = determineStartingCoordinate(pgnMove, pieceToMove, endingCoordinate, board);
                Move move = new Move(startingCoordinate, endingCoordinate);
                board = moveExecutor.executeMove(move, board);
            }


            System.out.println("Move: " + moveNumber + " " + pieceColor + " " + pgnMove);
            ConsolePrinter.prettyPrintBoard(board);
            pieceColor = PieceColor.opposite(pieceColor);
        }
        return board;
    }

    private void printEndGame(String pgnMove){
        if(pgnMove.equals("1-0")){
            System.out.println("White wins!");
        } else if(pgnMove.equals("0-1")){
            System.out.println("Black wins!");
        } else if(pgnMove.equals("1/2-1/2")) {
            System.out.println("Draw!");
        }
    }


    private boolean gameOver(String pgnMove) {
        if(pgnMove.equals("1-0")){
            return true;
        } else if(pgnMove.equals("0-1")){
            return true;
        } else if(pgnMove.equals("1/2-1/2")){
            return true;
        }
        return false;
    }



    private Coordinate determineEndingCoordinate(String pgnMove, Board board) {
        String strippedPgnMove = stripPgnMove(pgnMove);
        String substring = strippedPgnMove.substring(Math.max(strippedPgnMove.length() - 2, 0));
        int rank = Integer.parseInt(String.valueOf(substring.charAt(1)));
        BoardFile file = BoardFile.boardFile(String.valueOf(substring.charAt(0)));
        return new Coordinate(file, rank);
    }



    private Coordinate determineStartingCoordinate(String pgnMove, Piece piece, Coordinate endingCoordinate, Board board) {
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
                if (LegalMoveGenerator.isLegalMove(new Move(coordinate, endingCoordinate), board)) {
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



    private boolean isAmbiguousMove(String pgnMove) {
        if(pgnMove.length() == 4){
            if(pgnMove.length() == 4){
                return true;
            }
        }
        return false;
    }

    private String stripPgnMove(String pgnMove){
        String tempString = pgnMove;
        tempString = tempString.replace("x", "");
        tempString = tempString.replace("+", "");
        tempString = tempString.replace("#", "");
        return tempString;
    }



    private PieceType determinePieceType(String pgnMove) {
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
