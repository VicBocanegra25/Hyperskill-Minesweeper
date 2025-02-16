package minesweeper.entity;

public class Cell {

    private boolean isMine;
    private int neighbourhoodMines = 0;

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public int getNeighbourhoodMines() {
        return neighbourhoodMines;
    }

    public void addNeighbourhoodMines(int neighbourhoodMines) {
        this.neighbourhoodMines += neighbourhoodMines;
    }
}
