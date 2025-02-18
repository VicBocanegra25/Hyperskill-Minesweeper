package minesweeper.ui;

import minesweeper.entity.Board;
import java.util.Scanner;

public class CommandInterface {
    private final Board board;
    private final Scanner scanner;

    public CommandInterface() {
        scanner = new Scanner(System.in);
        System.out.println("How many mines do you want on the field?");
        int mineCount = Integer.parseInt(scanner.nextLine());
        board = new Board(mineCount);
    }

    public void startGame() {
        board.printBoard();

        while (true) {
            System.out.println("Set/unset mines marks or claim a cell as free:");
            String[] input = scanner.nextLine().split("\\s+");

            if (input.length != 3) {
                continue;
            }

            try {
                int x = Integer.parseInt(input[0]) - 1; // Convert to 0-based indexing
                int y = Integer.parseInt(input[1]) - 1;
                String command = input[2].toLowerCase();

                if (!isValidCoordinate(x, y)) {
                    continue;
                }

                Board.GameStatus status;
                if (command.equals("free")) {
                    status = board.makeMove(x, y, true);
                } else if (command.equals("mine")) {
                    status = board.makeMove(x, y, false);
                } else {
                    System.out.println("Invalid command! Use 'free' or 'mine'");
                    continue;
                }

                board.printBoard();

                if (status == Board.GameStatus.WIN) {
                    System.out.println("Congratulations! You found all the mines!");
                    break;
                } else if (status == Board.GameStatus.LOSE) {
                    System.out.println("You stepped on a mine and failed!");
                    break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Coordinates should be numbers!");
            }
        }
        scanner.close();
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < 9 && y >= 0 && y < 9;
    }
}