package minesweeper.entity;

import java.util.ArrayList;
import java.util.List;

public class Board {
    int BOARD_SIZE = 9;
    // A 9x9 2D array.
    private List<List<Cell>> boardMatrix = new ArrayList<>(9);
    private int numMines = 10;

    public Board(int numMines) {
        this.numMines = numMines;
        prepareBoard();
    }

    private void prepareBoard() {
        // Iterating over the board, initializing cells at each position and randomizing mines
        for (int i = 0; i < BOARD_SIZE ; i++) {
            List<Cell> rows = new ArrayList<>(boardMatrix.size());
            for (int j = 0; j < BOARD_SIZE; j++) {
                rows.add(new Cell());
            }
            boardMatrix.add(rows);
        }
        // Setting up the mines
        for (int i = 0; i < numMines; i++) {
            int randomX = (int) (Math.random() * 9);
            int randomY = (int) (Math.random() * 9);
            boardMatrix.get(randomX).get(randomY).setMine(true);
        }
    }

    public void printBoard() {
        for (List<Cell> row : boardMatrix) {
            for (Cell cell : row) {
                if (cell.isMine()) {
                    System.out.print("X");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }
}
