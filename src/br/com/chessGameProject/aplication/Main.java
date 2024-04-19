package br.com.chessGameProject.aplication;

import br.com.chessGameProject.bordergame.Board;
import br.com.chessGameProject.bordergame.Position;
import br.com.chessGameProject.chess.ChessException;
import br.com.chessGameProject.chess.ChessMatch;
import br.com.chessGameProject.chess.ChessPiece;
import br.com.chessGameProject.chess.ChessPosition;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        while (true){
            try {
                UI.clearScreen();

                UI.printBoard(chessMatch.getPicies());
                System.out.println();
                System.out.println("Source position:");
                ChessPosition source = UI.readChessPosition(in);

                System.out.println();
                System.out.println("Target position:");
                ChessPosition target = UI.readChessPosition(in);


                ChessPiece capturedPice = chessMatch.performChessMove(source,target);
            }catch (ChessException e){
                System.out.println(e.getMessage());
                in.nextLine();

            }catch (InputMismatchException e){
                System.out.println(e.getMessage());
                in.nextLine();
            }


        }
    }
}