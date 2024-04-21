package br.com.chessGameProject.chess;

import br.com.chessGameProject.bordergame.Board;
import br.com.chessGameProject.bordergame.Piece;
import br.com.chessGameProject.bordergame.Position;
import br.com.chessGameProject.chess.pieces.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ChessMatch {
    private Board board;
    private int turn;
    private boolean checkmate;
    private Color currentPlColor;
    private boolean check;
    private List<Piece> piciesOnTheBoard = new ArrayList<>();
    private List<Piece> piciesAutTheBoard = new ArrayList<>();

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlColor() {
        return currentPlColor;
    }

    public boolean isCheckmate() {
        return checkmate;
    }

    public ChessMatch() {
        board = new Board(8, 8);
        initialSetup();
        turn = 1;
        currentPlColor = Color.WHITE;
    }

    public ChessPiece[][] getPicies() {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return mat;
    }


    public boolean isCheck() {
        return check;
    }


    private void nextTurn() {
        turn++;
        currentPlColor = (currentPlColor == Color.WHITE ? Color.BLACK : Color.WHITE);
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePice(piece, new ChessPosition(column, row).toPosition());
        this.piciesOnTheBoard.add(piece);
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        System.out.println(position.getColumn() + "," + position.getRow());
        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);

        if (testeCheck(currentPlColor)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check");
        }
        check = (testeCheck(oponent(currentPlColor)) ? true : false);

        if (testCheckmate(oponent(currentPlColor))) {
            checkmate = true;
        } else {
            nextTurn();
        }
        return (ChessPiece) capturedPiece;


    }


    private void validateSourcePosition(Position position) {
        if (!board.therelsAPiece(position)) {
            throw new ChessException("Error is not exist piece in this position");
        }
        if (currentPlColor != ((ChessPiece) board.piece(position)).getColor()) {
            throw new ChessException("Erro this is not your piece");
        }
        if (!board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("There is no possible moves for the chosen piece");
        }
    }


    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) {
            throw new ChessException("The chosen piece can't move to target position");
        }
    }

    public void undoMove(Position source, Position target, Piece capPiece) {
        ChessPiece p = (ChessPiece) board.removePiece(target);
        p.decriseMoveCount();
        board.placePice(p, source);

        if (capPiece != null) {
            board.placePice(capPiece, target);
            piciesAutTheBoard.remove(capPiece);
            piciesOnTheBoard.add(capPiece);

        }
    }

    private Color oponent(Color color) {
        return (color == Color.WHITE ? Color.BLACK : Color.WHITE);

    }

    private ChessPiece king(Color color) {
        List<Piece> list = piciesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).collect(Collectors.toList());
        for (Piece p : list) {
            if (p instanceof King) {
                return (ChessPiece) p;
            }
        }
        throw new IllegalStateException("There is no " + color + " king on the board");
    }

    private boolean testeCheck(Color color) {
        Position kingPosition = king(color).getChessPosition().toPosition();

        List<Piece> listOponentPiece = piciesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == oponent(color)).collect(Collectors.toList());
        for (Piece p : listOponentPiece) {
            boolean[][] mat = p.possibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testCheckmate(Color color) {
        if (!testeCheck(color)) {
            return false;
        }
        List<Piece> list = piciesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).collect(Collectors.toList());
        for (Piece p : list) {
            boolean[][] mat = p.possibleMoves();

            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {
                    if (mat[i][j]) {
                        Position source = ((ChessPiece) p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean testCheck = testeCheck(color);
                        undoMove(source, target, capturedPiece);
                        if (!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private Piece makeMove(Position source, Position target) {
        ChessPiece p = (ChessPiece)board.removePiece(source);
        p.incriseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePice(p, target);

        if (capturedPiece != null) {
            piciesOnTheBoard.remove(capturedPiece);
            piciesAutTheBoard.add(capturedPiece);
        }
        return capturedPiece;
    }

    private void initialSetup() {
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
    placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK));
       placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
     //   placeNewPiece('a', 8, new King(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK));

    }
}
