package chess.engine.ai;

import chess.model.*;

public class PieceWeight {

    public static int getStaticWeight(PieceType pieceType, PieceColor pieceColor, Coordinate coordinate, boolean inEndGame){
        switch(pieceType){
            case KING: return 20000;
            case PAWN: return 100;
            case ROOK: return 500;
            case QUEEN: return 900;
            case BISHOP: return 330;
            case KNIGHT: return 320;
            default: return 0;
        }
    }

    public static int getWeight(PieceType pieceType, PieceColor pieceColor, Coordinate coordinate, boolean inEndGame){
        switch(pieceType){
            case KING: return adjustForPosition(20000, pieceType, pieceColor, coordinate, inEndGame);
            case PAWN: return adjustForPosition(100, pieceType, pieceColor, coordinate, inEndGame);
            case ROOK: return adjustForPosition(500, pieceType, pieceColor, coordinate, inEndGame);
            case QUEEN: return adjustForPosition(900, pieceType, pieceColor, coordinate, inEndGame);
            case BISHOP: return adjustForPosition(330, pieceType, pieceColor, coordinate, inEndGame);
            case KNIGHT: return adjustForPosition(320, pieceType, pieceColor, coordinate, inEndGame);
            default: return 0;
        }
    }

    private static int adjustForPosition(int defaultValue, PieceType pieceType, PieceColor pieceColor, Coordinate coordinate, boolean inEndGame){
        if(pieceType.equals(PieceType.PAWN)){
            if(pieceColor.equals(PieceColor.WHITE)){
                return defaultValue + pawnMapWhite(coordinate);
            }else{
                return defaultValue + pawnMapBlack(coordinate);
            }
        }
        if(pieceType.equals(PieceType.BISHOP)){
            if(pieceColor.equals(PieceColor.WHITE)){
                return defaultValue + bishopMapWhite(coordinate);
            }else{
                return defaultValue + bishopMapBlack(coordinate);
            }
        }

        if(pieceType.equals(PieceType.KNIGHT)){
            if(pieceColor.equals(PieceColor.WHITE)){
                return defaultValue + knightMapWhite(coordinate);
            }else{
                return defaultValue + knightMapBlack(coordinate);
            }
        }

        if(pieceType.equals(PieceType.ROOK)){
            if(pieceColor.equals(PieceColor.WHITE)){
                return defaultValue + rookMapWhite(coordinate);
            }else{
                return defaultValue + rookMapBlack(coordinate);
            }
        }
        if(pieceType.equals(PieceType.QUEEN)){
            if(pieceColor.equals(PieceColor.WHITE)){
                return defaultValue + queenMapWhite(coordinate);
            }else{
                return defaultValue + queenMapBlack(coordinate);
            }
        }
        if(pieceType.equals(PieceType.KING)){
            if(pieceColor.equals(PieceColor.WHITE)){
                if(inEndGame){
                    return defaultValue + kingEndGameWhite(coordinate);
                }else{
                    return defaultValue + kingMiddleGameWhite(coordinate);
                }
            }else{
                if(inEndGame){
                    return defaultValue + kingEndGameBlack(coordinate);
                }else{
                    return defaultValue + kingMiddleGameBlack(coordinate);
                }
            }
        }
        return 0;
    }

    //0, 0 == a 8
    // 7, 7 == h 1
    private static int pawnMapWhite(Coordinate coordinate){
        int i = coordinate.getArrayBasedRankWhitePerspective();
        int j = coordinate.getArrayBasedFileWhitePerspective();
            int[][] pawnWhiteMap = new int [][]{
            {0,  0,  0,  0,  0,  0,  0,  0 },
            { 50, 50, 50, 50, 50, 50, 50, 50},
            {10, 10, 20, 30, 30, 20, 10, 10 },
            { 5,  5, 10, 25, 25, 10,  5,  5},
            { 0,  0,  0, 20, 20,  0,  0,  0},
            {5, -5,-10,  0,  0,-10, -5,  5 },
            { 5, 10, 10,-20,-20, 10, 10,  5},
            { 0,  0,  0,  0,  0,  0,  0,  0}
        };
        return pawnWhiteMap[i][j];
    }
    private static int pawnMapBlack(Coordinate coordinate){
        int i = coordinate.getArrayBasedRankWhitePerspective();
        int j = coordinate.getArrayBasedFileWhitePerspective();
        int[][] pawnBlackMap = new int[][]{
                { 0,  0,  0,  0,  0,  0,  0,  0},
                { 5, 10, 10,-20,-20, 10, 10,  5},
                {5, -5,-10,  0,  0,-10, -5,  5 },
                { 0,  0,  0, 20, 20,  0,  0,  0},
                { 5,  5, 10, 25, 25, 10,  5,  5},
                {10, 10, 20, 30, 30, 20, 10, 10 },
                { 50, 50, 50, 50, 50, 50, 50, 50},
                {0,  0,  0,  0,  0,  0,  0,  0 },
        };
        return pawnBlackMap[i][j];
    }

    private static int knightMapWhite(Coordinate coordinate){
        int i = coordinate.getArrayBasedRankWhitePerspective();
        int j = coordinate.getArrayBasedFileWhitePerspective();
        int[][] knightMap =  new int[][]{
                {-50,-40,-30,-30,-30,-30,-40,-50},
                {-40,-20,  0,  0,  0,  0,-20,-40},
                {-30,  0, 10, 15, 15, 10,  0,-30},
                { -30,  5, 15, 20, 20, 15,  5,-30},
                {-30,  0, 15, 20, 20, 15,  0,-30},
                {-30,  5, 10, 15, 15, 10,  5,-30},
                {-40,-20,  0,  5,  5,  0,-20,-40},
                { -50,-40,-30,-30,-30,-30,-40,-50}
        };
        return knightMap[i][j];
    }

    private static int knightMapBlack(Coordinate coordinate){
        int i = coordinate.getArrayBasedRankWhitePerspective();
        int j = coordinate.getArrayBasedFileWhitePerspective();
        int[][] knightMap =  new int[][]{
            { -50,-40,-30,-30,-30,-30,-40,-50},
            {-40,-20,  0,  5,  5,  0,-20,-40},
            {-30,  5, 10, 15, 15, 10,  5,-30},
            {-30,  0, 15, 20, 20, 15,  0,-30},
            { -30,  5, 15, 20, 20, 15,  5,-30},
            {-30,  0, 10, 15, 15, 10,  0,-30},
            {-40,-20,  0,  0,  0,  0,-20,-40},
            {-50,-40,-30,-30,-30,-30,-40,-50},
        };
        return knightMap[i][j];
    }

    private static int bishopMapWhite(Coordinate coordinate){
        int i = coordinate.getArrayBasedRankWhitePerspective();
        int j = coordinate.getArrayBasedFileWhitePerspective();
        int[][] bishopMapWhite =  new int[][]{
            {-20,-10,-10,-10,-10,-10,-10,-20,},
            {-10,  0,  0,  0,  0,  0,  0,-10,},
            {-10,  0,  5, 10, 10,  5,  0,-10,},
            {-10,  5,  5, 10, 10,  5,  5,-10,},
            {-10,  0, 10, 10, 10, 10,  0,-10,},
            {-10, 10, 10, 10, 10, 10, 10,-10,},
            {-10,  5,  0,  0,  0,  0,  5,-10,},
            {-20,-10,-10,-10,-10,-10,-10,-20,}
        };
        return bishopMapWhite[i][j];
    }

    private static int bishopMapBlack(Coordinate coordinate){
        int i = coordinate.getArrayBasedRankWhitePerspective();
        int j = coordinate.getArrayBasedFileWhitePerspective();
        int[][] bishopMapBlack =  new int[][]{
            {-20,-10,-10,-10,-10,-10,-10,-20,},
            {-10,  5,  0,  0,  0,  0,  5,-10,},
            {-10, 10, 10, 10, 10, 10, 10,-10,},
            {-10,  0, 10, 10, 10, 10,  0,-10,},
            {-10,  5,  5, 10, 10,  5,  5,-10,},
            {-10,  0,  5, 10, 10,  5,  0,-10,},
            {-10,  0,  0,  0,  0,  0,  0,-10,},
            {-20,-10,-10,-10,-10,-10,-10,-20,}
        };
        return bishopMapBlack[i][j];
    }

    private static int rookMapWhite(Coordinate coordinate){
        int i = coordinate.getArrayBasedRankWhitePerspective();
        int j = coordinate.getArrayBasedFileWhitePerspective();
        int[][] rookMapWhite =  new int[][]{
            { 0,  0,  0,  5,  5,  0,  0,  0},
            { -5,  0,  0,  0,  0,  0,  0, -5,},
            {-5,  0,  0,  0,  0,  0,  0, -5,},
            { -5,  0,  0,  0,  0,  0,  0, -5,},
            { -5,  0,  0,  0,  0,  0,  0, -5,},
            {-5,  0,  0,  0,  0,  0,  0, -5,},
            {5, 10, 10, 10, 10, 10, 10,  5,},
            {  0,  0,  0,  0,  0,  0,  0,  0,},
        };
        return rookMapWhite[i][j];
    }

    private static int rookMapBlack(Coordinate coordinate){
        int i = coordinate.getArrayBasedRankWhitePerspective();
        int j = coordinate.getArrayBasedFileWhitePerspective();
        int[][] rookMapBlack =  new int[][]{
            {  0,  0,  0,  0,  0,  0,  0,  0,},
            {5, 10, 10, 10, 10, 10, 10,  5,},
            {-5,  0,  0,  0,  0,  0,  0, -5,},
            { -5,  0,  0,  0,  0,  0,  0, -5,},
            { -5,  0,  0,  0,  0,  0,  0, -5,},
            {-5,  0,  0,  0,  0,  0,  0, -5,},
            { -5,  0,  0,  0,  0,  0,  0, -5,},
            { 0,  0,  0,  5,  5,  0,  0,  0},
        };
        return rookMapBlack[i][j];
    }

    private static int queenMapWhite(Coordinate coordinate){
        int i = coordinate.getArrayBasedRankWhitePerspective();
        int j = coordinate.getArrayBasedFileWhitePerspective();
        int[][] queenMapWhite =  new int[][]{
            { -20,-10,-10, -5, -5,-10,-10,-20,},
            {-10,  0,  0,  0,  0,  0,  0,-10,},
            {-10,  0,  5,  5,  5,  5,  0,-10,},
            { -5,  0,  5,  5,  5,  5,  0, -5,},
            {  0,  0,  5,  5,  5,  5,  0, -5,},
            {-10,  5,  5,  5,  5,  5,  0,-10,},
            {-10,  0,  5,  0,  0,  0,  0,-10,},
            {-20,-10,-10, -5, -5,-10,-10,-20}
        };
        return queenMapWhite[i][j];
    }

    private static int queenMapBlack(Coordinate coordinate){
        int i = coordinate.getArrayBasedRankWhitePerspective();
        int j = coordinate.getArrayBasedFileWhitePerspective();
        int[][] queenMapBlack =  new int[][]{
            {-20,-10,-10, -5, -5,-10,-10,-20},
            {-10,  0,  5,  0,  0,  0,  0,-10,},
            {-10,  5,  5,  5,  5,  5,  0,-10,},
            {  0,  0,  5,  5,  5,  5,  0, -5,},
            { -5,  0,  5,  5,  5,  5,  0, -5,},
            {-10,  0,  5,  5,  5,  5,  0,-10,},
            {-10,  0,  0,  0,  0,  0,  0,-10,},
            { -20,-10,-10, -5, -5,-10,-10,-20,},
        };
        return queenMapBlack[i][j];
    }

    private static int kingMiddleGameWhite(Coordinate coordinate){
        int i = coordinate.getArrayBasedRankWhitePerspective();
        int j = coordinate.getArrayBasedFileWhitePerspective();
        int[][] kingMiddleGameWhite =  new int[][]{
                { -30,-40,-40,-50,-50,-40,-40,-30,},
                {-30,-40,-40,-50,-50,-40,-40,-30,},
                {-30,-40,-40,-50,-50,-40,-40,-30,},
                {-30,-40,-40,-50,-50,-40,-40,-30,},
                {-20,-30,-30,-40,-40,-30,-30,-20,},
                {-10,-20,-20,-20,-20,-20,-20,-10,},
                { 20, 20,  0,  0,  0,  0, 20, 20,},
                {20, 30, 10,  0,  0, 10, 30, 20}
        };
        return kingMiddleGameWhite[i][j];
    }

    private static int kingMiddleGameBlack(Coordinate coordinate){
        int i = coordinate.getArrayBasedRankWhitePerspective();
        int j = coordinate.getArrayBasedFileWhitePerspective();
        int[][] kingMiddleGameBlack =  new int[][]{
            {20, 30, 10,  0,  0, 10, 30, 20},
            { 20, 20,  0,  0,  0,  0, 20, 20,},
            {-10,-20,-20,-20,-20,-20,-20,-10,},
            {-20,-30,-30,-40,-40,-30,-30,-20,},
            {-30,-40,-40,-50,-50,-40,-40,-30,},
            {-30,-40,-40,-50,-50,-40,-40,-30,},
            {-30,-40,-40,-50,-50,-40,-40,-30,},
            { -30,-40,-40,-50,-50,-40,-40,-30,},
        };
        return kingMiddleGameBlack[i][j];
    }

    private static int kingEndGameWhite(Coordinate coordinate){
        int i = coordinate.getArrayBasedRankWhitePerspective();
        int j = coordinate.getArrayBasedFileWhitePerspective();
        int[][] kingEndGameWhite =  new int[][]{
                {-50,-40,-30,-20,-20,-30,-40,-50,},
                {-30,-20,-10,  0,  0,-10,-20,-30,},
                {-30,-10, 20, 30, 30, 20,-10,-30,},
                {-30,-10, 30, 40, 40, 30,-10,-30,},
                {-30,-10, 30, 40, 40, 30,-10,-30,},
                {-30,-10, 20, 30, 30, 20,-10,-30,},
                {-30,-30,  0,  0,  0,  0,-30,-30,},
                {-50,-30,-30,-30,-30,-30,-30,-50}
        };
        return kingEndGameWhite[i][j];
    }

    private static int kingEndGameBlack(Coordinate coordinate){
        int i = coordinate.getArrayBasedRankWhitePerspective();
        int j = coordinate.getArrayBasedFileWhitePerspective();
        int[][] kingEndGameBlack =  new int[][]{
            {-50,-30,-30,-30,-30,-30,-30,-50},
            {-30,-30,  0,  0,  0,  0,-30,-30,},
            {-30,-10, 20, 30, 30, 20,-10,-30,},
            {-30,-10, 30, 40, 40, 30,-10,-30,},
            {-30,-10, 30, 40, 40, 30,-10,-30,},
            {-30,-10, 20, 30, 30, 20,-10,-30,},
            {-30,-20,-10,  0,  0,-10,-20,-30,},
            {-50,-40,-30,-20,-20,-30,-40,-50,},
        };
        return kingEndGameBlack[i][j];
    }
}
