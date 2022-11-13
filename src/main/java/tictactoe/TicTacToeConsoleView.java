package tictactoe;

import java.util.Scanner;

import game.ThrowExceptionFileActionHasFailed;
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
    private boolean fileProperlySaved;
    private String userFileNameInput;

    private final int integerInputMenuOption = 0;
    private final int integerInputBoardPosition = 1;
    private final int characterInput = 2;
    private final int fileNameLoad = 3;
    private final int fileNameSave = 4;

    // Creating a new scanner object and setting the skipMenuOption to false.
    public TicTacToeConsoleView() {
        scanner = new Scanner(System.in);
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
                                         getThisTypeInput(integerInputBoardPosition));
            int[] coordinates = new int[2];
            if (!parseUserInput(coordinates)) {
                continue;
            }
            performTurn(coordinates);
            if (checkIfGameHasFinished()) {
                continue;
            }
            game.updatePlayerTurn(game.getPlayerTurn());

            saveFileManuallyUserPrompts();
            if (getFileProperlySaved()) {
                if (promptUser("Would you like to continue?\n" + "Please enter 'y' or 'n': ") == 'y') {
                    setSkipMenuOption(true);
                } else {
                    setSkipMenuOption(false);
                }
            } else {
                setSkipMenuOption(true);
            }
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

            getUserInput("Please, choose a correct option (1-3): ", getThisTypeInput(integerInputMenuOption));
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
        if (FileHandling.getLoadFromFileResult()) {
            try {
                game.validateBoardFromFile(FileHandling.getStringBoard());
            } catch (ThrowExceptionWrongBoardFormat wrongFormatEx) {
                setImproperLoading(true);
                printString("Couldn't load this file: " + wrongFormatEx.getMessage() + "\n");
            } catch (ThrowExceptionTheGameHasEnded gameEndedEx) {
                setImproperLoading(true);
                printString(gameEndedEx.getMessage());
            }
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

        if (FileHandling.getLoadFromFileResult()) {
            setFileProperlySaved(true);
            printString("Board was successfully saved\n");
        }
    }

    private char promptUser(String questionToAsk) {        
        getUserInput(questionToAsk, getThisTypeInput(characterInput));
        return getCharacterInput();
    }

    private void saveFileManuallyUserPrompts() {
        if (!getAutoSaveValue()) {
            if (promptUser("Would you like to save the board?\n" + "Please enter 'y' or 'n': ") == 'y') {
                handleStepsToSaveToFile();
            } else {
                setFileProperlySaved(false);
                printString("The Board is not saved\n\n");
            }
        } else {
            handleStepsToSaveToFile();
        }
        printString("\n");
    }

    private boolean checkIfGameHasFinished() {
        if (game.isDone()) {
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

        if (promptUser("Would you like to save the board?\n" + "Please enter 'y' or 'n': ") == 'y') {
            handleStepsToSaveToFile();
        } else {
            printString("The Board is not Saved\n\n");
        }
        setSkipMenuOption(false);
    }

    private boolean parseUserInput(int[] coordinates) {
        if (getIntegerInput() == 0) {
            saveFileManuallyUserPrompts();
            printString("Exiting...\n");
            setSkipMenuOption(false);
            return false;
        }

        splitUserInputToCoordinates(getIntegerInput(), coordinates);
        return true;
    }

    private void performTurn(int[] coordinates) {
        String currTurn = String.valueOf(game.getPlayerTurn());
        try {
            game.takeTurn(coordinates[0], coordinates[1], currTurn);
        } catch (RuntimeException e) {
            printString(e.getMessage());
        }
    }

    private void splitUserInputToCoordinates(int inputPosition, int[] coordinates) {
        if (inputPosition == 1) {
            fillArray(1, 1, coordinates);
        } else if (inputPosition == 2) {
            fillArray(1, 2, coordinates);
        } else if (inputPosition == 3) {
            fillArray(1, 3, coordinates);
        } else if (inputPosition == 4) {
            fillArray(2, 1, coordinates);
        } else if (inputPosition == 5) {
            fillArray(2, 2, coordinates);
        } else if (inputPosition == 6) {
            fillArray(2, 3, coordinates);
        } else if (inputPosition == 7) {
            fillArray(3, 1, coordinates);
        } else if (inputPosition == 8) {
            fillArray(3, 2, coordinates);
        } else if (inputPosition == 9) {
            fillArray(3, 3, coordinates);
        } else {
            fillArray(-1, -1, coordinates);
        }
    }

    private void fillArray(int row, int column, int[] coordinates) {
        coordinates[0] = row;
        coordinates[1] = column;
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
            } catch (ThrowExceptionForInvalidInput e) {
                printString(e.getMessage());
            } catch (ThrowExceptionFileActionHasFailed e) {
                printString(e.getMessage() + "\n");
            }
        } while (true);
    }

    private void validateInputForSavingAndLoading(String userChoice) throws ThrowExceptionForInvalidInput {
        if (userChoice.length() != 1) {
            throw new ThrowExceptionForInvalidInput("The input value is: " + userChoice
                                                    + ", which not the right length: " + userChoice.length() + "\n");
        } else {
            if (userChoice.charAt(0) != 'y' && userChoice.charAt(0) != 'n') {
                throw new ThrowExceptionForInvalidInput("The input:" + userChoice
                                                        + " is not 'y' or 'n'. Please enter correct format");
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
            throw new ThrowExceptionForInvalidInput("The input wasn't able to be converted to an integer.\n");
        }

        if (userInputInteger < 1 || userInputInteger > 3) {
            throw new ThrowExceptionForInvalidInput("The inputed option is out-of-range for menu options (1-3).\n");
        }
    }

    private boolean tryLoadingFromFile(String userString) throws ThrowExceptionFileActionHasFailed {
        FileHandling.loadFile("assets/" + userString, game);
        return true;
    }

    private boolean trySavingToFile(String userString) throws ThrowExceptionFileActionHasFailed {
        FileHandling.saveToFile("assets/" + userString, game);
        return true;
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

    private int getThisTypeInput(int inputOption) {
        return inputOption;
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

    private void setFileProperlySaved(boolean boolValue) {
        fileProperlySaved = boolValue;
    }

    private boolean getFileProperlySaved() {
        return fileProperlySaved;
    }

    private void setFileNameInput(String userInput) {
        userFileNameInput = userInput;
    }

    private String getFileNameInput() {
        return userFileNameInput;
    }
}
