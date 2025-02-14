package minesweeper.ui;

import minesweeper.entity.Board;

import java.util.Scanner;

public class CommandInterface {
    Board myBoard;
    Scanner scan = new Scanner(System.in);

    public void startGame() {
        myBoard = new Board(requestMineNumberFromUser());
        myBoard.printBoard();
    }

    public int requestMineNumberFromUser() {
        System.out.println("How many mines do you want on the field?");
        return scan.nextInt();
    }
}
