package br.com.chessGameProject.chess;

import br.com.chessGameProject.bordergame.Board;
import br.com.chessGameProject.bordergame.Piece;
import br.com.chessGameProject.bordergame.Position;

public abstract class ChessPiece extends Piece {

    private Color color;
    private int moveCount;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }
public ChessPosition getChessPosition(){
        return ChessPosition.fromPosition(position);
}

    public Color getColor() {
        return color;
    }


    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece p = (ChessPiece)getBoard().piece(position);
        return p != null && p.getColor() != color;
    }
    protected void incriseMoveCount(){
        moveCount++;
    }
    protected void decriseMoveCount(){
        moveCount--;
    }

    public int getMoveCount() {
        return moveCount;
    }
}
