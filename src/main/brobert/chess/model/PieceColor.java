package chess.model;

public enum PieceColor {
    WHITE,BLACK;

    public static PieceColor opposite(PieceColor color){
        if(PieceColor.WHITE.equals(color)){
            return PieceColor.BLACK;
        }else{
            return PieceColor.WHITE;
        }
    }
}
