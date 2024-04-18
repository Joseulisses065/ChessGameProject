package br.com.chessGameProject.chess;

import br.com.chessGameProject.bordergame.Board;
import br.com.chessGameProject.bordergame.Piece;
import br.com.chessGameProject.bordergame.Position;
import br.com.chessGameProject.chess.pieces.King;
import br.com.chessGameProject.chess.pieces.Rook;

public class ChessMatch {
    private Board board;

    public ChessMatch() {
        board = new Board(8,8);
        initialSetup();
    }

    public ChessPiece[][] getPicies(){
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0;i< board.getRows();i++){
            for (int j=0; j< board.getColumns();j++){
                mat[i][j] = (ChessPiece) board.piece(i,j);
            }
        }
        return mat;
    }

    private void initialSetup(){
        board.placePice(new Rook(board,Color.WHITE),new Position(2,1));
        board.placePice(new King(board,Color.BLACK),new Position(0,4));
        board.placePice(new King(board,Color.WHITE),new Position(7,4));

    }
}
