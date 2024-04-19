package br.com.chessGameProject.aplication;

import br.com.chessGameProject.bordergame.Board;
import br.com.chessGameProject.bordergame.Position;
import br.com.chessGameProject.chess.ChessException;
import br.com.chessGameProject.chess.ChessMatch;
import br.com.chessGameProject.chess.ChessPiece;
import br.com.chessGameProject.chess.ChessPosition;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> captured = new ArrayList<>();

        while (true) {
            try {
                UI.clearScreen();
                UI.printMatch(chessMatch,captured);
                System.out.println();
                System.out.println("Source position:");
                ChessPosition source = UI.readChessPosition(in);

                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPicies(), possibleMoves);


                System.out.println();
                System.out.println("Target position:");
                ChessPosition target = UI.readChessPosition(in);


                ChessPiece capturedPice = chessMatch.performChessMove(source, target);
                if (capturedPice!=null){
                    captured.add(capturedPice);
                }
            } catch (ChessException e) {
                System.out.println(e.getMessage());
                in.nextLine();

            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                in.nextLine();
            }


        }
    }
}