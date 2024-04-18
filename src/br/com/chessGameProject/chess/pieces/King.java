package br.com.chessGameProject.chess.pieces;

import br.com.chessGameProject.bordergame.Board;
import br.com.chessGameProject.chess.ChessPiece;
import br.com.chessGameProject.chess.Color;

public class King extends ChessPiece {
    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "K";
    }
}
