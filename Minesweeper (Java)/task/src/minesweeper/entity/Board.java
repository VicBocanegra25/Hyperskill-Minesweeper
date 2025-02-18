package minesweeper.entity;

import java.util.*;

public class Board {
    int BOARD_SIZE = 9;
    // A 9x9 2D array.
    private List<List<Cell>> boardMatrix = new ArrayList<>(BOARD_SIZE);
    private final int numMines;
    Random random = new Random();
    private boolean firstMove = true;

    public enum GameStatus {
        CONTINUE,
        WIN,
        LOSE
    }

    public Board(int numMines) {
        this.numMines = numMines;
        prepareBoard();
    }

    private void prepareBoard() {
        // Initialize empty board without mines
        for (int i = 0; i < BOARD_SIZE; i++) {
            boardMatrix.add(new ArrayList<>());
            for (int j = 0; j < BOARD_SIZE; j++) {
                boardMatrix.get(i).add(new Cell());
            }
        }
    }

    private void placeMines(int excludeX, int excludeY) {

        int minesPlaced = 0;
        while (minesPlaced < numMines) {
            int x = random.nextInt(BOARD_SIZE);
            int y = random.nextInt(BOARD_SIZE);

            // Skip the first clicked cell and cells that already have mines
            if ((x == excludeX && y == excludeY) || boardMatrix.get(y).get(x).isMine()) {
                continue;
            }

            boardMatrix.get(y).get(x).setMine(true);
            minesPlaced++;
        }
        countNeighbourhoodMines();
    }

    public GameStatus makeMove(int x, int y, boolean isFreeCommand) {
        if (x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE) {
            return GameStatus.CONTINUE; // Invalid move
        }

        Cell cell = boardMatrix.get(y).get(x);

        if (isFreeCommand) {
            if (firstMove) {
                placeMines(x, y);
                firstMove = false;
            }

            if (cell.isMine()) {
                revealAllMines();
                return GameStatus.LOSE;
            }

            if (!cell.isMarked()) {
                floodFill(x, y);
            }
        } else {
            // Toggle mark
            cell.changeMarked();
        }

        return checkGameStatus();
    }

    private void floodFill(int x, int y) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{x, y});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int currentX = current[0];
            int currentY = current[1];

            if (currentX < 0 || currentX >= BOARD_SIZE || currentY < 0 || currentY >= BOARD_SIZE) {
                continue;
            }

            Cell currentCell = boardMatrix.get(currentY).get(currentX);
            if (currentCell.isRevealed() || currentCell.isMine()) {
                continue;
            }

            currentCell.setRevealed(true);
            currentCell.setMarked(false);

            // If cell has no adjacent mines, explore neighbors
            if (currentCell.getNeighbourhoodMines() == 0) {
                // Add all 8 neighbors to queue
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dx = -1; dx <= 1; dx++) {
                        if (dx == 0 && dy == 0) continue;
                        queue.offer(new int[]{currentX + dx, currentY + dy});
                    }
                }
            }
        }
    }

    private void revealAllMines() {
        for (List<Cell> row : boardMatrix) {
            for (Cell cell : row) {
                if (cell.isMine()) {
                    cell.setRevealed(true);
                }
            }
        }
    }

    private GameStatus checkGameStatus() {
        boolean allSafeCellsRevealed = true;
        boolean allMinesMarkedCorrectly = true;

        for (List<Cell> row : boardMatrix) {
            for (Cell cell : row) {
                if (cell.isMine()) {
                    if (!cell.isMarked()) {
                        allMinesMarkedCorrectly = false;
                    }
                } else {
                    if (!cell.isRevealed()) {
                        allSafeCellsRevealed = false;
                    }
                    if (cell.isMarked()) {
                        allMinesMarkedCorrectly = false;
                    }
                }
            }
        }

        if (allMinesMarkedCorrectly || allSafeCellsRevealed) {
            return GameStatus.WIN;
        }
        return GameStatus.CONTINUE;
    }

    public void printBoard() {
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print((i + 1) + "|");
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(boardMatrix.get(i).get(j).getDisplayChar());
            }
            System.out.println("|");
        }
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

}
