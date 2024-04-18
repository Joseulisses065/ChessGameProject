package br.com.chessGameProject.aplication;

import br.com.chessGameProject.bordergame.Board;
import br.com.chessGameProject.bordergame.Position;
import br.com.chessGameProject.chess.ChessMatch;

public class Main {
    public static void main(String[] args) {
        ChessMatch chessMatch = new ChessMatch();
        UI.printBoard(chessMatch.getPicies());
    }
}