package minesweeper.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    int BOARD_SIZE = 9;
    // A 9x9 2D array.
    private List<List<Cell>> boardMatrix = new ArrayList<>(BOARD_SIZE);
    private final int numMines;
    Random random = new Random();

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
            // We need to account for mines already set up
            while (true) {
                int randomX = random.nextInt(9);
                int randomY = random.nextInt(9);
                if (boardMatrix.get(randomX).get(randomY).isMine()) {
                    continue;
                }
                boardMatrix.get(randomX).get(randomY).setMine(true);
                break;
            }
        }
        // Counting the number of mines
        countNeighbourhoodMines();
    }

    public void printBoard() {
        System.out.println(" |123456789|");
        System.out.println("-|---------|");

        for (int i = 0; i < boardMatrix.size(); i++) {
            System.out.print((i + 1) + "|"); // Row number

            for (Cell cell : boardMatrix.get(i)) {
                if (cell.isMarked()) {
                    System.out.print("*");
                } else if (cell.getNeighbourhoodMines() > 0) {
                    System.out.print(cell.getNeighbourhoodMines());
                } else {
                    System.out.print(".");
                }
            }
            System.out.println("|");
        }

        // Print bottom border
        System.out.println("-|---------|");
    }
    
    /* The method iterates over the 2d array checking if the cells next to the current cell are mines
    * We must be careful with the corners and edges since we could get a NullPointerException
    */
    public void countNeighbourhoodMines() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                // Skip if current cell is a mine
                if (boardMatrix.get(i).get(j).isMine()) {
                    continue;
                }

                int mineCount = 0;

                // Check previous row
                if (i > 0) {
                    if (j > 0 && boardMatrix.get(i-1).get(j-1).isMine()) mineCount++;      // Top-left
                    if (boardMatrix.get(i-1).get(j).isMine()) mineCount++;                 // Top
                    if (j < BOARD_SIZE-1 && boardMatrix.get(i-1).get(j+1).isMine()) mineCount++; // Top-right
                }

                // Check current row
                if (j > 0 && boardMatrix.get(i).get(j-1).isMine()) mineCount++;           // Left
                if (j < BOARD_SIZE-1 && boardMatrix.get(i).get(j+1).isMine()) mineCount++; // Right

                // Check next row
                if (i < BOARD_SIZE-1) {
                    if (j > 0 && boardMatrix.get(i+1).get(j-1).isMine()) mineCount++;      // Bottom-left
                    if (boardMatrix.get(i+1).get(j).isMine()) mineCount++;                 // Bottom
                    if (j < BOARD_SIZE-1 && boardMatrix.get(i+1).get(j+1).isMine()) mineCount++; // Bottom-right
                }

                boardMatrix.get(i).get(j).addNeighbourhoodMines(mineCount);
            }
        }
    }

    public mineStatus markCell(int[] coordinates) {
        // If the field indicating neighbourhood mines is larger than 0, it means there are no mines here, but numbers
        Cell currentCell = boardMatrix.get(coordinates[1] - 1).get(coordinates[0] - 1);
        if (currentCell.getNeighbourhoodMines()> 0) {
            return mineStatus.IS_NUMBER;
        }
        currentCell.changeMarked();
        return mineStatus.MARKED;
    }

    public enum mineStatus {
        IS_NUMBER, IS_MINE, MARKED;
    }

    public boolean verifyIfMinesAreCovered() {
        int marks = 0;
        for (List<Cell> row: boardMatrix) {
            for (Cell cell : row) {
                if (cell.isMarked() && cell.isMine()) {
                    marks++;
                } else if (cell.isMarked() && !cell.isMine()) {
                    marks--;
                }
            }
        }
        return marks == numMines;
    }

}
