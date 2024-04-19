package br.com.chessGameProject.chess;

import br.com.chessGameProject.bordergame.Position;

public class ChessPosition {
    private int column;
    private int row;


    public ChessPosition(int column, int row) {
        if (column < 'a' || column > 'h' || row < 1 || row > 8) {
            throw new ChessException("Error instatiating ChessPosition.Valid values are from a1 to h8.");
        }
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    protected Position toPosition(){

        return new Position(8-this.row,this.column-'a');
    }

    protected ChessPosition fromPosition(Position position){
        return new ChessPosition((char)('a'-position.getColumn()), 8 - position.getRow());
    }

    @Override
    public String toString() {
        return ""+column+row;
    }
}
