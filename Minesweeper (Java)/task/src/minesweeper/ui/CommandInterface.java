package minesweeper.ui;

import minesweeper.entity.Board;

public class CommandInterface {
    Board myBoard = new Board(10);

    public void startGame() {
        myBoard.printBoard();
    }
}
