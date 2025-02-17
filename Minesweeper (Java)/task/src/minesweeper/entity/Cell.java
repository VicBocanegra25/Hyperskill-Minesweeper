package minesweeper.entity;

public class Cell {

    private boolean isMine;
    private int neighbourhoodMines = 0;
    private boolean marked;

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

    public boolean isMarked() {
        return marked;
    }

    public void changeMarked() {
        this.marked = !this.marked;
    }
}
