package minesweeper.entity;

public class Cell {

    private boolean isMine;
    private int neighbourhoodMines = 0;
    private boolean marked;
    private boolean isRevealed;

    public char getDisplayChar() {
        if (!isRevealed) {
            return marked ? '*' : '.';
        }

        if (isMine) {
            return 'X';
        }

        if (neighbourhoodMines == 0) {
            return '/';
        }

        return (char) ('0' + neighbourhoodMines);
    }


    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

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

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public void changeMarked() {
        this.marked = !this.marked;
    }

    // Add a method to check if the cell can be safely revealed
    public boolean isSafeToReveal() {
        return !isMine && !isRevealed;
    }

}
