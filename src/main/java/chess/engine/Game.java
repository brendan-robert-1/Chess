package chess.engine;

import chess.engine.ai.MinimaxChessMoveAi;
import chess.engine.ai.RandomMoveAi;
import chess.engine.exception.IllegalMoveException;
import chess.engine.exception.InvalidPGNMoveSyntaxException;
import chess.engine.legalmoves.LegalMoveGenerator;
import chess.engine.pgn.PGNCoordinateFinder;
import chess.engine.pgn.PgnStringUtils;
import chess.model.*;

import java.util.Scanner;

public class Game {

    private Board board;
    private PieceColor human;
    private PieceColor computer;
    private PieceColor currentPlayer = PieceColor.WHITE;
    private MoveExecutor moveExecutor = new MoveExecutor();
    private PGNCoordinateFinder coordinateFinder = new PGNCoordinateFinder();
    private boolean gameOver = false;
    private MinimaxChessMoveAi minimax = new MinimaxChessMoveAi();
    private RandomMoveAi randomMoveAi = new RandomMoveAi();
    private int moveNumber = 0;

    private Game(PieceColor playingAs){
        this.human = playingAs;
        this.computer = PieceColor.opposite(human);
        board = new BoardGenerator().generateStartingBoard();
    }

    private Game(PieceColor playingAs, String jsonFile){
        this.human = playingAs;
        this.computer = PieceColor.opposite(human);
        board = new BoardGenerator().generateBoardFromJsonFile(jsonFile);
    }

    private Game(PieceColor playingAs, String pgnFile, boolean pgn){
        this.human = playingAs;
        this.computer = PieceColor.opposite(human);
        board = new BoardGenerator().generateBoardFromPGN(pgnFile);
    }

    public static Game newGame(PieceColor playingAs){
        return new Game(playingAs);
    }

    public static Game newRandomGameVsMinimax(){
        return new Game(PieceColor.WHITE);
    }

    public static Game newGame(PieceColor playingAs, String jsonFile, boolean isPGN){
        if(isPGN){
            return new Game(playingAs, jsonFile, isPGN);
        } else {
            return new Game(playingAs, jsonFile);
        }
    }


    public void startGame() throws IllegalMoveException {
        System.out.println("Playing as " + human);
        printBoard();
        while(!gameOver){
            if(human == PieceColor.WHITE){
                executeHumanMove();
                executeAIMove();
            } else{
                executeAIMove();
                executeHumanMove();
            }
        }
    }
    public void startAiversusAiGame() throws IllegalMoveException {
        System.out.println("Playing as " + human);
        printBoard();
        while(!gameOver){
            if(human == PieceColor.WHITE){
                executeRandomMove();
                executeAIMove();
            } else{
                executeAIMove();
                executeRandomMove();
            }
        }
    }

    private void executeHumanMove() throws InvalidPGNMoveSyntaxException {
        Scanner scanner = new Scanner(System.in);
        boolean enteredALegalMove = false;
        while(!enteredALegalMove) {
            System.out.println("Please enter a valid move...");
            String pgnMove = scanner.next();
            try {
                if(pgnMove.equals("O-O")){
                    board = moveExecutor.castleShort(currentPlayer, board);
                    enteredALegalMove = true;
                }else if(pgnMove.equals("O-O-O")){
                    board = moveExecutor.castleLong(currentPlayer, board);
                    enteredALegalMove = true;
                }else{
                    PieceType pieceType = coordinateFinder.determinePieceType(pgnMove);
                    Piece piece = new Piece(currentPlayer, pieceType);
                    Coordinate endingCoordinate = coordinateFinder.determineEndingCoordinate(pgnMove);
                    Coordinate startingCoordinate = coordinateFinder.determineStartingCoordinateOfPgnMove(pgnMove, piece, endingCoordinate, board);
                    Move move = new Move(startingCoordinate, endingCoordinate);
                    LegalMoveGenerator.isLegalMove(move, board, human);
                    board = moveExecutor.executeMove(move, board);
                    printBoard();
                    enteredALegalMove = true;
                }
            } catch (Exception e) {
            }
        }
        currentPlayer = PieceColor.opposite(currentPlayer);
    }

    private void executeRandomMove() throws InvalidPGNMoveSyntaxException, IllegalMoveException {
        Move move = randomMoveAi.generateMove(board, currentPlayer);
        String pgnMove = PgnStringUtils.pgnFromMove(move, board);
        board = moveExecutor.executeMove(move, board);
        printBoard();
        System.out.println("AI chose move: " + move.toString() + " ("+pgnMove+")");
        currentPlayer = PieceColor.opposite(currentPlayer);
    }



    private void executeAIMove() throws IllegalMoveException {
        Move move = minimax.generateMove(board, currentPlayer);
        String pgnMove = PgnStringUtils.pgnFromMove(move, board);
        board = moveExecutor.executeMove(move, board);
        printBoard();
        System.out.println("AI chose move: " + move.toString() + " ("+pgnMove+")");
        currentPlayer = PieceColor.opposite(currentPlayer);
    }

    public GameStatus isGameOver() {
        GameStatus gameStatus = new GameStatus();
        gameStatus.gameOver = false;
        gameStatus.winner = null;
        return gameStatus;
    }

    public void printBoard(){
        ConsolePrinter.prettyPrintBoard(board, human);
    }

}
