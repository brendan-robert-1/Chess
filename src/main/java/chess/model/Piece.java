package chess.model;

public class Piece {
    private PieceColor pieceColor;
    private PieceType pieceType;
    private boolean hasMoved;

    public Piece(PieceColor pieceColor, PieceType pieceType, boolean hasMoved){
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
        this.hasMoved = hasMoved;
    }

    public Piece(PieceColor pieceColor, PieceType pieceType){
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
        this.hasMoved = false;
    }


    public PieceColor getPieceColor() {
        return pieceColor;
    }
    public boolean hasMoved (){
        return hasMoved;
    }


    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public String toString(){
        return pieceColor+ " " + pieceType + " has moved: " + hasMoved;
    }

}
