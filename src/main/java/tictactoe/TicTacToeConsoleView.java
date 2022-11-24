package tictactoe;

import java.util.Scanner;

import game.ThrowExceptionFileActionHasFailed;
import game.ThrowExceptionForInvalidInput;
import game.FileHandling;

/**
 * This class is used to run a text-based version of Tictactoe.
 * It is used to facilitate the process of playing.. It handles the input and 
 * proper structure of the game.
 * 
 * @author Myron Ladyjenko
 */
public class TicTacToeConsoleView {
    private Scanner scanner;
    private TicTacToeGame game;
    private int userIntegerInput;
    private char userCharacterInput;
    private boolean autoSave;
    private boolean skipMenuOption;
    private boolean improperLoading;
    private String userFileNameInput;
    private int[] coordinatesForInputPosition;

    private final int integerInputMenuOption = 0;
    private final int integerInputBoardPosition = 1;
    private final int characterInput = 2;
    private final int fileNameLoad = 3;
    private final int fileNameSave = 4;

    // Creating a new scanner object and setting the skipMenuOption to false.
    public TicTacToeConsoleView() {
        scanner = new Scanner(System.in);
        coordinatesForInputPosition = new int[2];
        setSkipMenuOption(false);
    }

    /**
     * This method is a main method that provides a menu-driven TicTacToe game in the console.
     * It combines methods from other classes to produce and allow the user to play TicTacToe game
     * The function uses a while loop that keeps running until the user chooses to exit the game
     */
    public void playGame() {
        printString("\nWELCOME TO THE TicTacToe GAME!\n");
        while (getSkipMenuOption() || chooseTheMenuOptionAndInitialize() != 3) {
            printString(game.getGridWrapper().toString());
            printString("Turn - " + game.getPlayerTurn() + "\n");
            getUserInput("Please enter the position from 1 to 9 (0 to exit to the main menu): ", 
                                         integerInputBoardPosition);

            if (!parseUserInput()) {
                continue;
            }

            performTurn();
            if (checkIfGameHasFinished()) {
                continue;
            }

            game.updatePlayerTurn(game.getPlayerTurn());
            saveFileManuallyUserPrompts();
        }
        scanner.close();
    }

    private int chooseTheMenuOptionAndInitialize() {
        displayStartGameMenu();

        do {
            if (getImproperLoading()) {
                displayStartGameMenu();
                setImproperLoading(false);
            }

            getUserInput("Please, choose a correct option (1-3): ", integerInputMenuOption);
            printString("\n");
            if (getIntegerInput() == 1) {
                game = new TicTacToeGame();
                break;
            } else if (getIntegerInput() == 2) {
                hadleStepsToLoadFromFile();
                if (getImproperLoading()) {
                    continue;
                }
                break;
            } else if (getIntegerInput() == 3) {
                printString("Thank you for playing!\n");
                break;
            }
        } while (true);
    
        setSkipMenuOption(true);
        promptForAutoSaveToASpecificFile();
        return getIntegerInput();
    }

    private void promptForAutoSaveToASpecificFile() {
        if (getIntegerInput() != 3) {
            if (promptUser("Would you like to turn on auto saving to a specified file?\n" 
                            + "Please enter y for 'yes' and n for 'no': ") == 'y') {
                setAutoSave(true);
                getUserInput("Please enter a name of the file to save to: ", fileNameSave);
            } else {
                setAutoSave(false);
            }
        }
    }

    private void hadleStepsToLoadFromFile() {
        game = new TicTacToeGame();
        getUserInput("Please enter a name of the file to load from: ", fileNameLoad);
        if (game.getExceptionValue()) {
            setImproperLoading(true);
            printString(game.getExceptionMessage());
        }
    }

    private void handleStepsToSaveToFile() {
        if (!getAutoSaveValue()) {
            getUserInput("Please enter a name of the file to save to: ", fileNameSave);
        } else {
            try {
                FileHandling.saveToFile("assets/" + getFileNameInput(), game);
            } catch (ThrowExceptionFileActionHasFailed wrongFileEx) {
                printString(wrongFileEx.getMessage());
            }
        }

        if (!game.getExceptionValue()) {
            printString("Board was successfully saved\n");
        }
    }

    private char promptUser(String questionToAsk) {        
        getUserInput(questionToAsk, characterInput);
        return getCharacterInput();
    }

    private void saveFileManuallyUserPrompts() {
        if (!getAutoSaveValue()) {
            if (promptUser("Would you like to save the board?\n" + "Please enter 'y' or 'n': ") == 'y') {
                handleStepsToSaveToFile();
            } else {
                printString("The Board is not saved\n\n");
            }
        } else {
            handleStepsToSaveToFile();
        }
        printString("\n");
    }

    private boolean checkIfGameHasFinished() {
        if (game.getWinner() != -1) {
            actionsAfterGameEnd();
            return true;
        } else {
            printString("\nCurrent board state: \n");
            printString(game.getGridWrapper().toString() + "\n");
        }

        return false;
    }

    private void actionsAfterGameEnd() {
        displayFinalResultsForGame();

        if (!getAutoSaveValue()) {
            if (promptUser("Would you like to save the board?\n" + "Please enter 'y' or 'n': ") == 'y') {
                handleStepsToSaveToFile();
            } else {
                printString("The Board is not saved\n\n");
            }
        } else {
            printString("The Board is saved\n\n");
        }
        setSkipMenuOption(false);
    }

    private boolean parseUserInput() {
        if (getIntegerInput() == 0) {
            saveFileManuallyUserPrompts();
            printString("Exiting...\n");
            setSkipMenuOption(false);
            return false;
        }

        splitUserInputToCoordinates(getIntegerInput());
        return true;
    }

    private void performTurn() {
        String currTurn = String.valueOf(game.getPlayerTurn());
        try {
            game.takeTurn(coordinatesForInputPosition[0], coordinatesForInputPosition[1], currTurn);
        } catch (RuntimeException e) {
            printString(e.getMessage());
        }
    }

    private void splitUserInputToCoordinates(int inputPosition) {
        int row = 0;
        for (int boardPosition = 0; boardPosition < game.getHeight() * game.getWidth(); boardPosition++) {
            if (boardPosition == 3 || boardPosition == 6) {
                row++;
            }
            if (inputPosition - 1 == boardPosition) {
                fillArray(row + 1, boardPosition % 3 + 1);
            }
        }
    }

    private void fillArray(int row, int column) {
        coordinatesForInputPosition[0] = row;
        coordinatesForInputPosition[1] = column;
    }

    /**
     * It asks the user to enter a number from 1 to 9, and if the user enters a number that is not in
     * that range, it will ask the user to enter a number again
     */
    private void getUserInput(String promptForUser, int typeOfInput) {
        setIntegerInput(-1);
        setCharacterInput('\0');
        do {
            printString(promptForUser);
            try {
                String input = scanner.nextLine();
                if (typeOfInput == integerInputMenuOption) {
                    validateInputForMenuOption(input);
                    setIntegerInput(Integer.parseInt(input));
                } else if (typeOfInput == integerInputBoardPosition) {
                    validateInputOnBoard(input);
                    setIntegerInput(Integer.parseInt(input));
                } else if (typeOfInput == characterInput) {
                    validateInputForSavingAndLoading(input);
                    setCharacterInput(input.charAt(0));
                } else if (typeOfInput == fileNameLoad) {
                    tryLoadingFromFile(input);
                } else if (typeOfInput == fileNameSave) {
                    trySavingToFile(input);
                    setFileNameInput(input);    
                }
                break;
            } catch (ThrowExceptionForInvalidInput ex) {
                printString(ex.getMessage());
            } catch (ThrowExceptionFileActionHasFailed ex) {
                printString(ex.getMessage() + "\n");
            }
        } while (true);
    }

    private void validateInputForSavingAndLoading(String userChoice) throws ThrowExceptionForInvalidInput {
        if (userChoice.length() != 1) {
            throw new ThrowExceptionForInvalidInput("The input value is: " + userChoice
                                                    + ", which not the right length: " + userChoice.length() + "\n");
        } else {
            if (userChoice.charAt(0) != 'y' && userChoice.charAt(0) != 'n') {
                throw new ThrowExceptionForInvalidInput("The input value: " + userChoice
                                                        + " is not 'y' or 'n'. Please enter correct format\n");
            }
        }
    }

    private void validateInputOnBoard(String inputToValidate) throws ThrowExceptionForInvalidInput {
        try {
            Integer.parseInt(inputToValidate);
        } catch (NumberFormatException ex) {
            throw new ThrowExceptionForInvalidInput("The input wasn't able to be converted to an integer.\n");
        }
    }

    private void validateInputForMenuOption(String inputToValidate) throws ThrowExceptionForInvalidInput {
        int userInputInteger;
        try {
            userInputInteger = Integer.parseInt(inputToValidate);
        } catch (NumberFormatException ex) {
            throw new ThrowExceptionForInvalidInput("The input cannot be converted into an integer.\n");
        }

        if (userInputInteger < 1 || userInputInteger > 3) {
            throw new ThrowExceptionForInvalidInput("The inputed option is out-of-range for menu options (1-3).\n");
        }
    }

    private void tryLoadingFromFile(String userString) throws ThrowExceptionFileActionHasFailed {
        FileHandling.loadFile("assets/" + userString, game);
    }

    private void trySavingToFile(String userString) throws ThrowExceptionFileActionHasFailed {
        FileHandling.saveToFile("assets/" + userString, game);
    }

    private void displayStartGameMenu() {
        printString("\nChoose one of the options below to proceed:\n");
        printString("1) Start a new game\n");
        printString("2) Load a game from a file\n");
        printString("3) Exit\n");
    }

    private void displayFinalResultsForGame() {
        printString(game.getGameStateMessage() + "\n");
        printString("\nFinal board:\n");
        printString(game.getGridWrapper().toString());
    }

    private void printString(String stringToPrint) {
        System.out.print(stringToPrint);
    }

    private void setIntegerInput(int userInput) {
        userIntegerInput = userInput;
    }

    private int getIntegerInput() {
        return userIntegerInput;
    }

    private void setCharacterInput(char userInput) {
        userCharacterInput = userInput;
    }

    private char getCharacterInput() {
        return userCharacterInput;
    }

    private void setAutoSave(boolean boolValue) {
        autoSave = boolValue;
    }

    private boolean getAutoSaveValue() {
        return autoSave;
    }

    private void setSkipMenuOption(boolean boolValue) {
        skipMenuOption = boolValue;
    }

    private boolean getSkipMenuOption() {
        return skipMenuOption;
    }

    private void setImproperLoading(boolean boolValue) {
        improperLoading = boolValue;
    }

    private boolean getImproperLoading() {
        return improperLoading;
    }

    private void setFileNameInput(String userInput) {
        userFileNameInput = userInput;
    }

    private String getFileNameInput() {
        return userFileNameInput;
    }
}
