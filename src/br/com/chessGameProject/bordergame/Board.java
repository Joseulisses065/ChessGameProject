package br.com.chessGameProject.bordergame;

public class Board {

    private int rows;
    private int columns;
    private Piece[][] pieces;

    public Board(int rows, int columns) {
        if(rows < 1 || columns<1){
            throw new BordException("Error creating board: there must be at last 1 row an 1 column");
        }
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }

    public Piece removePiece(Position position) {
        if (!positionExists(position)) {
            throw new BordException("Position not on the board");
        }
        if (piece(position) == null) {
            return null;
        }
        Piece aux = piece(position);
        aux.position = null;
        pieces[position.getRow()][position.getColumn()] = null;
        return aux;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Piece piece(int row, int column){
        if(!positionExists(row,column)){
            throw new BordException("Error position not on the board");
        }

        return pieces[row][column];
    }

    public Piece piece(Position position){
        if(!positionExists(position)){
            throw new BordException("Error position not on the board");
        }
        return pieces[position.getRow()][position.getColumn()];
    }

    public void placePice(Piece piece, Position position){
        if (therelsAPiece(position)){
            throw new BordException("There is already a piece on position"+position);

        }
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;

    }

    private boolean positionExists(int row, int column){
        return row>=0 && row < rows && column >= 0 && column < columns;
    }

    public boolean positionExists(Position position){
        return positionExists(position.getRow(),position.getColumn());
    }

    public boolean therelsAPiece(Position position){
        if(!positionExists(position)){
            throw new BordException("Error position not on the board");
        }
        return piece(position)!= null;
    }


}
