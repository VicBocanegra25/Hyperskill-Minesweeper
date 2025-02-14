package minesweeper;

import minesweeper.ui.CommandInterface;

public class Main {
    public static void main(String[] args) {
        // write your code here
        CommandInterface myInterface = new CommandInterface();
        myInterface.startGame();
    }
}
