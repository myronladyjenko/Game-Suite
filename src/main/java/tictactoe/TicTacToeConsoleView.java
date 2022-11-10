package tictactoe;

import java.util.Scanner;

public class TicTacToeConsoleView {
    private Scanner scanner;
    private TicTacToeBoard board;
    private TicTacToeGame game;
    private int userInputPosition;

    public TicTacToeConsoleView() {
        scanner = new Scanner(System.in);
        board = new TicTacToeBoard();
        game = new TicTacToeGame();
    }

    /**
     * This method is a main method that provides a menu-driven TicTacToe game in the console.
     * It combines methods from other classes to produce and allow the user to play TicTacToe game
     */
    public void playGame() {
        printString("\nWELCOME TO THE TicTacToe GAME!\n");

        do {
            printString(board.toString());
            printString("Turn - " + game.getPlayerTurn());
            getUserInput();
            
            if (getInputPosition() == 0) {
                printString("Exiting...");
                break;
            }
            
            String currTurn = "";
            currTurn += game.getPlayerTurn();
            if (game.takeTurn(1, 1, currTurn)) {
                game.updatePlayerTurn(game.getPlayerTurn());
            }
        } while (!game.isDone());


        printString("\nFinal board:");
        printString(board.toString());
        scanner.close();
    }

    private void getUserInput() {
        setInputPosition(-1);

        do {
            System.out.print("Please enter the position from 1 to 9 (0 to exit): ");
            try {
                setInputPosition(scanner.nextInt());
            } catch (Exception e) {
                scanner.nextLine();
            }
        } while (getInputPosition() == -1);
        //clear the input in case multiple values are inputed on the same line
        scanner.nextLine();
    }

    private void printString(String stringToPrint) {
        System.out.println(stringToPrint);
    }

    private void setInputPosition(int currentPosition) {
        userInputPosition = currentPosition;
    }

    private int getInputPosition() {
        return userInputPosition;
    }
}
