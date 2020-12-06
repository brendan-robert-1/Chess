package chess.engine;


import chess.engine.exception.IllegalMoveException;
import chess.engine.pgn.PGNCoordinateFinder;
import chess.engine.pgn.PGNGame;
import chess.model.*;
import com.google.common.collect.ImmutableMap;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
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
        board.setPieceMap(ImmutableMap.<Coordinate, Piece>builder().putAll(map).build());
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
        Type type = new TypeToken<Map<Coordinate, Piece>>(){}.getType();
        Map<Coordinate, Piece> map = new GsonBuilder().create().fromJson(json, type);
        return new Board(ImmutableMap.<Coordinate, Piece>builder().putAll(map).build());
    }

    public Board generateBoardFromPGN(String pgnFile){
        InputStream is = getClass().getClassLoader().getResourceAsStream(pgnFile);
        PGNCoordinateFinder coordinateFinder = new PGNCoordinateFinder();
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
                try{
                    board = moveExecutor.castleShort(pieceColor, board);
                }catch(RuntimeException e){
                    System.err.print("Can not castle as pieces have moved");
                    e.printStackTrace();
                }
            }
            else if(pgnMove.equals("O-O-O")){
                try{
                    board = moveExecutor.castleLong(pieceColor, board);
                }catch(RuntimeException e){
                    System.err.print("Can not castle as pieces have moved");
                    e.printStackTrace();
                };
            } else if(gameOver(pgnMove)){
                printEndGame(pgnMove);
                break;
            } else {
                PieceType pieceType = coordinateFinder.determinePieceType(pgnMove);
                Piece pieceToMove = new Piece(pieceColor, pieceType);
                Coordinate endingCoordinate = coordinateFinder.determineEndingCoordinate(pgnMove);
                Coordinate startingCoordinate = coordinateFinder.determineStartingCoordinateOfPgnMove(pgnMove, pieceToMove, endingCoordinate, board);
                Move move = new Move(startingCoordinate, endingCoordinate);
                board = moveExecutor.executeMove(move, board);
            }


            System.out.println("Move: " + moveNumber + " " + pieceColor + " " + pgnMove);
            ConsolePrinter.prettyPrintBoard(board, PieceColor.WHITE);
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




}
