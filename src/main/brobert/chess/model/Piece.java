package chess.model;

public class Piece {
    private PieceColor pieceColor;
    private PieceType pieceType;

    public Piece(PieceColor pieceColor, PieceType pieceType){
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }



    public PieceType getPieceType() {
        return pieceType;
    }

}
