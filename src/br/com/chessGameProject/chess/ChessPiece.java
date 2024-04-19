package br.com.chessGameProject.chess;

import br.com.chessGameProject.bordergame.Board;
import br.com.chessGameProject.bordergame.Piece;
import br.com.chessGameProject.bordergame.Position;

public abstract class ChessPiece extends Piece {

    private Color color;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }


    public Color getColor() {
        return color;
    }


    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece p = (ChessPiece)getBoard().piece(position);
        return p != null && p.getColor() != color;
    }


}
