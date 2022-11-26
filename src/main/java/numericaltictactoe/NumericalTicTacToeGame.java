package numericaltictactoe;

import game.Player;
import game.ThrowExceptionTheGameHasEnded;
import game.ThrowExceptionWrongBoardFormat;

/**
 * This class is used to handle the board game for the game NumericalTicTacToe.
 * It allows to make turns on board and determines the players playing the game. 
 * 
 * @author Myron Ladyjenko
 */
public class NumericalTicTacToeGame extends boardgame.BoardGame implements boardgame.Saveable {
    private Player player;
    private String winnerMessage;
    private boolean allowedMove;
    private String exceptionMessage;
    private boolean exceptionOccured;

    // Creating a new NumericalTicTacToeGame object.
    public NumericalTicTacToeGame(int wide, int high) {
        super(wide, high);
        setGrid(new NumericalTicTacToeGrid());
        player = new Player("O");
    }

    private void validateBoardFromFile(String stringBoard) throws ThrowExceptionWrongBoardFormat, 
                                                                 ThrowExceptionTheGameHasEnded {
        checkBasicBoardRequirements(stringBoard);

        if (checkForUnexpectedSymbols(stringBoard)) {
            throw new ThrowExceptionWrongBoardFormat("The read file contains unexpected characters.\n");
        }

        StringBuilder playerWithWrongNumOfMoves = new StringBuilder("");
        if (checkIncorrectNumberOfMoves(stringBoard, playerWithWrongNumOfMoves)) {
            throw new ThrowExceptionWrongBoardFormat("Player: " + playerWithWrongNumOfMoves.toString()
                                                     + " has 2 or more moves over the other player. Please restart.\n");
        }

        NumericalTicTacToeGame testBoard = new NumericalTicTacToeGame(3, 3);
        ((NumericalTicTacToeGrid) testBoard.getGrid()).parseStringIntoBoard(stringBoard);
        if (testBoard.isDone()) {
            throw new ThrowExceptionTheGameHasEnded("The game on this board has finished. Please restart.\n");
        }
    }

    private void checkBasicBoardRequirements(String stringBoard) throws ThrowExceptionWrongBoardFormat, 
                                                                 ThrowExceptionTheGameHasEnded {
        if (stringBoard.length() < 11 || stringBoard.length() > 20) {
            throw new ThrowExceptionWrongBoardFormat("Length of the board read from"
                                                     + " file doesn't match the expected one. Please restart.\n");
        }

        if (countCommas(stringBoard) != 6) {
            throw new ThrowExceptionWrongBoardFormat("Board has incorrect number of commas or newlines.\n");
        }

        if (stringBoard.charAt(0) != 'O' && stringBoard.charAt(0) != 'E') {
            throw new ThrowExceptionWrongBoardFormat("The read file doesn't contain the player information.\n");
        }

        if (checkForTotalNumTurns(stringBoard) > 9) {
            throw new ThrowExceptionWrongBoardFormat("The board has too many moves (> 9).\n");
        }

        if (twoMovesWithNoSeparator(stringBoard)) {
            throw new ThrowExceptionWrongBoardFormat("The board contains input larger then 9.\n");
        }
    }

    private boolean twoMovesWithNoSeparator(String stringBoard) {
        for (int i = 0; i < stringBoard.length() - 1; i++) {
            if (Character.isDigit((stringBoard.charAt(i))) && Character.isDigit((stringBoard.charAt(i + 1)))) {
                return true;
            }
        }
        return false;
    }

    private int checkForTotalNumTurns(String stringBoard) {
        int countTurns = 0;
        for (int i = 0; i < stringBoard.length(); i++) {
            if (stringBoard.charAt(i) != ' ' && stringBoard.charAt(i) != ',' && stringBoard.charAt(i) != '\n') {
                countTurns++;
            }
        }
        return countTurns;
    }

    private int countCommas(String stringBoard) {
        int numCommas = 0;
        for (int i = 0; i < stringBoard.length(); i++) {
            if (stringBoard.charAt(i) == ',') {
                numCommas++;
            }
        }
        return numCommas;
    }

    private boolean checkForUnexpectedSymbols(String stringBoard) {
        String sBoard = stringBoard.replaceAll(",", "");
        sBoard = sBoard.replaceAll("\n", "");
        for (int i = 1; i < sBoard.length(); i++) {
            if (!Character.isDigit(sBoard.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIncorrectNumberOfMoves(String sBoard, StringBuilder playerWhoExceedsNumTurns) {
        int countOnes = 0;
        int countTwos = 0;

        for (int i = 1; i < sBoard.length(); i++) {
            if (Character.isDigit(sBoard.charAt(i)) && Integer.valueOf(sBoard.charAt(i)) % 2 == 1) {
                countOnes++;
            }
            if (Character.isDigit(sBoard.charAt(i)) && Integer.valueOf(sBoard.charAt(i)) % 2 == 0) {
                countTwos++;
            }
        }

        if (Math.abs(countOnes - countTwos) >= 2) {
            if (countOnes >= countTwos) {
                playerWhoExceedsNumTurns.append("O");
            } else {
                playerWhoExceedsNumTurns.append("E");
            }
            return true;
        } else if (countOnes - countTwos == 1 && sBoard.charAt(0) == 'E') {
            playerWhoExceedsNumTurns.append("O");
            return true;
        } else if (countTwos - countOnes == 1 && sBoard.charAt(0) == 'O') {
            playerWhoExceedsNumTurns.append("E");
            return true;
        }
        
        return false;
    }

    @Override
    /**
     * Creating a string that represents the board and the player turn
     * @return String representation of the board
     */
    public String getStringToSave() {
        String stringBoardForFile = "";
        stringBoardForFile = stringBoardForFile
                             + player.getNumericalTTTPlayerTurn(player.getPreviousPlayerTurn(player.getTurn())) + "\n";

        for (int i = 1; i <= getHeight(); i++) {
            for (int j = 1; j <= getWidth(); j++) {
                if (!getCell(i, j).equals(" ")) {
                    stringBoardForFile += getCell(i, j);
                } else {
                    stringBoardForFile += "";
                }

                if (j % getWidth() != 0) {
                    stringBoardForFile += ",";
                }
            }
            stringBoardForFile += "\n";
        }
        return stringBoardForFile;
    }

    @Override
    /**
     * This method creates a board and fills out the cells based on the string
     * @param String representation of the board
     */
    public void loadSavedString(String toLoad) {
        if (prepareForLoading(toLoad)) {
            ((NumericalTicTacToeGrid) getGrid()).parseStringIntoBoard(toLoad);
        }
    }

    private boolean prepareForLoading(String toLoad) {
        try {
            validateBoardFromFile(toLoad);
        } catch (ThrowExceptionWrongBoardFormat wrongFormatEx) {
            setExcpetionOccured(true);
            setExceptionMessage("Couldn't load this file: " + wrongFormatEx.getMessage() + "\n");
            return false;
        } catch (ThrowExceptionTheGameHasEnded gameEndedEx) {
            setExcpetionOccured(true);
            setExceptionMessage("Couldn't load this file: " + gameEndedEx.getMessage());
            return false;
        }

        player.updateTurn(toLoad.charAt(0));
        return true;
    }

    
    /**
     * This function takes in the coordinates of the cell that the user wants to change, and the value
     * that the user wants to change it to. It then sets the value of the cell to the value that the
     * user wants to change it to
     * 
     * @param across The x coordinate of the cell you want to change
     * @param down The row of the cell you want to change
     * @param input The value that the user entered.
     * @return A boolean value.
     */
    @Override
    public boolean takeTurn(int across, int down, String input) {
        setValue(across, down, input);
        return false;
    }

    /**
     * The function takes in three parameters, two integers and one integer, and returns a boolean
     * 
     * @param across the row number of the board
     * @param down the row of the board
     * @param input the number that the user wants to place on the board
     * @return A boolean value.
     */
    @Override
    public boolean takeTurn(int across, int down, int input) {
        // verify that the move suggested by the user is allowed
        checkBoardMove(across, down);
        checkSizeInput(input);
        checkIfCorrectTurn(input);
        checkIfNumberHasBeenUsed(input);

        setValue(across, down, input);
        return true;
    }

    private void checkIfNumberHasBeenUsed(int inputVal) {
         for (int i = 1; i <= getHeight(); i++) {
            for (int j = 1; j <= getWidth(); j++) {
                if (!getCell(i, j).equals(" ") && Integer.valueOf(getCell(i, j)) == inputVal) {
                    throw new RuntimeException("Error - Entered valed has already been played");
                }
            }
        }
    }

    private void checkSizeInput(int inputVal) {
        if (inputVal < 0 || inputVal > 9) {
            throw new RuntimeException("Error - Entered input is out of bounds");
        }
    }

    private boolean checkBoardMove(int inputRow, int inputColumn) {
        setAllowedMove(true);
        
        checkOutOfBounds(inputRow, inputColumn);
        if (!getAllowedMove()) {
            throw new RuntimeException("Error - Entered Board Position is out of Bounds");
        }

        validateMove(inputRow, inputColumn);
        if (!getAllowedMove()) {
            throw new RuntimeException("Error - Illegal Move: entered position is filled");
        }

        return getAllowedMove();
    }

    private void validateMove(int inputRow, int inputColumn) {
        if (!getCell(inputRow, inputColumn).equals(" ")) {
            setAllowedMove(false);
        }
    }

    private void checkOutOfBounds(int inputRow, int inputColumn) {
        if (inputRow > getHeight() ||  inputColumn > getWidth()
            || inputRow < 0 || inputColumn < 0) {
            setAllowedMove(false);
        }
    }

    private void checkIfCorrectTurn(int input) {
        if ((input % 2 == 0 && player.getNumericalTTTPlayerTurn(player.getTurn()).equals("O"))
            || (input % 2 == 1 && player.getNumericalTTTPlayerTurn(player.getTurn()).equals("E"))) {
            throw new RuntimeException("It's turn : " + player.getNumericalTTTPlayerTurn(player.getTurn()) 
                                       + ". Please wait");
        }
    }

    /**
     * If the game is won, return true. If the game is a tie, return true. Otherwise, return false.
     * 
     * @return A boolean value.
     */
    @Override
    public boolean isDone() {
        if (checkHorizontalWin()) {
            return true;
        }

        if (checkVerticalWin()) {
            return true;
        }

        if (checkDiagonalForWin()) {
            return true;
        }
        
        if (checkTie()) {
            setGameStateMessage("The game is a tie!");
            return true; 
        }
        return false;
    }

    private boolean checkHorizontalWin() {
        for (int i = 1; i <= getHeight(); i++) {
            if (!getCell(1, i).equals(" ") && !getCell(2, i).equals(" ") 
                && !getCell(3, i).equals(" ")) {
                if ((Integer.valueOf(getCell(1, i)) 
                    + Integer.valueOf(getCell(2, i)) 
                    + Integer.valueOf(getCell(3, i))) == 15) {
                    setGameStateMessage("Congratulations to " + player.getNumericalTTTPlayerTurn(player.getTurn()));
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkVerticalWin() {
        for (int i = 1; i <= getWidth(); i++) {
            if (!getCell(i, 1).equals(" ") && !getCell(i, 2).equals(" ") 
                && !getCell(i, 3).equals(" ")) {
                if ((Integer.valueOf(getCell(i, 1)) 
                    + Integer.valueOf(getCell(i, 2)) 
                    + Integer.valueOf(getCell(i, 3))) == 15) {
                    setGameStateMessage("Congratulations to " + player.getNumericalTTTPlayerTurn(player.getTurn()));
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonalForWin() {
        if (!getCell(1, 1).equals(" ") && !getCell(2, 2).equals(" ") 
            && !getCell(3, 3).equals(" ")) {
            if ((Integer.valueOf(getCell(1, 1)) 
                + Integer.valueOf(getCell(2, 2)) 
                + Integer.valueOf(getCell(3, 3))) == 15) {
                setGameStateMessage("Congratulations to " + player.getNumericalTTTPlayerTurn(player.getTurn()));
                return true;
            }
        }

        if (!getCell(3, 1).equals(" ") && !getCell(2, 2).equals(" ") 
            && !getCell(1, 3).equals(" ")) {
            if ((Integer.valueOf(getCell(3, 1)) 
                + Integer.valueOf(getCell(2, 2)) 
                + Integer.valueOf(getCell(1, 3))) == 15) {
                setGameStateMessage("Congratulations to " + player.getNumericalTTTPlayerTurn(player.getTurn()));
                return true;
            }
        }
        return false;
    }

    private boolean checkTie() {
        resetIterator();
        String cellValue = "";

        cellValue = getCell(1, 1);
        if (cellValue.equals(" ")) {
            return false;
        }

        while ((cellValue = getNextValue()) != null) {
            if (cellValue.equals(" ")) {
                return false;
            }
        }
        return true;
    }

    private void resetIterator() {
        int dummyValue = 0;
        while (getNextValue() != null) {
            dummyValue = dummyValue + 1;
        }
    }

    /**
     * The method checks if the game is done and if it is, check who the winner is.
     * @return -1 if the game hasn't ended. 1 - if Players who plays odd numbers won; 
     * 2 - if players who plays even numbers won; 0 - if a tie.
     */
    @Override
    public int getWinner() {
        if (isDone()) {
            if (checkTie()) {
                return 0;
            }

            if (player.getTurn() == 'O') {
                return 1;
            } else if (player.getTurn() == 'X') {
                return 2;
            }
        }

        return -1;
    }

    private void setGameStateMessage(String messageToSet) {
        winnerMessage = messageToSet;
    }

    /**
     * This function returns the winner message
     * 
     * @return The winnerMessage is being returned.
     */
    @Override
    public String getGameStateMessage() {
        return winnerMessage;
    }

    /**
     * This function returns the player's turn as a character (wrapper function)
     * 
     * @return The player's turn.
     */
    public char getPlayerTurn() {
        return player.getNumericalTTTPlayerTurn(player.getTurn()).charAt(0);
    }
    
    /**
     * This function updates the player's turn (wrapper function)
     * 
     * @param currTurn The current turn of the game.
     */
    public void updatePlayerTurn(char currTurn) {
        player.updateTurn(currTurn);
    }

    /**
     * Return the grid as a NumericalTicTacToeGrid (wrapper function)
     * 
     * @return The grid is being returned.
     */
    protected NumericalTicTacToeGrid getGridWrapper() {
        return (NumericalTicTacToeGrid) getGrid();
    }

    private void setAllowedMove(boolean booleanSetMove) {
        allowedMove = booleanSetMove;
    }

    private boolean getAllowedMove() {
        return allowedMove;
    }

    private void setExceptionMessage(String exMessage) {
        exceptionMessage = exMessage;
    }

    /**
     * This function returns the exception message
     * 
     * @return The exception message.
     */
    public String getExceptionMessage() {
        return exceptionMessage;
    }

    private void setExcpetionOccured(boolean exValue) {
        exceptionOccured = exValue;
    }

   /**
    * This function returns the value of the boolean variable exceptionOccured
    * 
    * @return The exceptionOccured variable is being returned.
    */
    public boolean getExceptionValue() {
        return exceptionOccured;
    }
}
