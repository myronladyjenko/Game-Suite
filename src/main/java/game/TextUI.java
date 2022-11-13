package game;

import tictactoe.TicTacToeConsoleView;

/**
 * This is a runner class that starts the text-based version of TicTacToe
 * @author Myron Ladyjenko
 */
public class TextUI {

    /**
     * The main function creates a new TicTacToeConsoleView object and calls the 
     * playGame function on it
     */
    public static void main(String[] args) {
        TicTacToeConsoleView consoleView = new TicTacToeConsoleView();
        consoleView.playGame();
    }
}
