package minesweeper.ui;

import minesweeper.entity.Board;

import java.util.Scanner;

public class CommandInterface {
    Board myBoard;
    Scanner scan = new Scanner(System.in);

    public void startGame() {
        myBoard = new Board(requestMineNumberFromUser());
        myBoard.printBoard();
        while (true) {
            if (myBoard.markCell(askForCoordinates()).equals(Board.mineStatus.IS_NUMBER)) {
                System.out.println("There is a number here!");
            }
            myBoard.printBoard();
            if (myBoard.verifyIfMinesAreCovered()) {
                System.out.println("Congratulations! You found all the mines!");
                break;
            }
        }
    }

    public int requestMineNumberFromUser() {
        System.out.println("How many mines do you want on the field?");
        return Integer.parseInt(scan.nextLine());

    }

    public int[] askForCoordinates() {
        System.out.println("Set/delete mines marks (x and y coordinates):");
        String[] userInput = scan.nextLine().split(" ");
        return new int[]{Integer.parseInt(userInput[0]), Integer.parseInt(userInput[1])};
    }
}
