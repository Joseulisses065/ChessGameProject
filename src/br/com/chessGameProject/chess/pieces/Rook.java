package br.com.chessGameProject.chess.pieces;

import br.com.chessGameProject.bordergame.Board;
import br.com.chessGameProject.chess.ChessPiece;
import br.com.chessGameProject.chess.Color;

public class Rook extends ChessPiece {
    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        return mat;
    }
}
