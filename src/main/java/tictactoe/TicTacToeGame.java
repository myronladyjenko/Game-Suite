package tictactoe;

import game.Player;
import game.ThrowExceptionTheGameHasEnded;
import game.ThrowExceptionWrongBoardFormat;

/**
 * This class is used to handle the board game for the game TicTacToe.
 * It allows to make turns on board and determines the players playing the game. 
 * 
 * @author Myron Ladyjenko
 */
public class TicTacToeGame extends boardgame.BoardGame implements boardgame.Saveable {
    private Player player;
    private String winnerMessage;
    private boolean allowedMove;
    private String exceptionMessage;
    private boolean exceptionOccured;

    // Creating a new TicTacToeGame object.
    public TicTacToeGame() {
        super(3, 3);
        setGrid(new TicTacToeGrid());
        player = new Player();
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

        TicTacToeGame testBoard = new TicTacToeGame();
        ((TicTacToeGrid) testBoard.getGrid()).parseStringIntoBoard(stringBoard);
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
            throw new ThrowExceptionWrongBoardFormat("Board has incorrect number of commas.\n");
        }

        if (stringBoard.charAt(0) != 'O' && stringBoard.charAt(0) != 'X') {
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
            if ((stringBoard.charAt(i) == 'O' || stringBoard.charAt(i) == 'X') 
                && (stringBoard.charAt(i + 1) == 'O' || stringBoard.charAt(i + 1) == 'X')) {
                return true;
            }
        }
        return false;
    }

    private int checkForTotalNumTurns(String stringBoard) {
        int countTurns = 0;
        for (int i = 0; i < stringBoard.length(); i++) {
            if (stringBoard.charAt(i) == 'X' || stringBoard.charAt(i) == 'O') {
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
        for (int i = 0; i < sBoard.length(); i++) {
            if (sBoard.charAt(i) != 'O' && sBoard.charAt(i) != 'X') {
                return true;
            }
        }
        return false;
    }

    private boolean checkIncorrectNumberOfMoves(String sBoard, StringBuilder playerWhoExceedsNumTurns) {
        int countOnes = 0;
        int countTwos = 0;

        for (int i = 1; i < sBoard.length(); i++) {
            if (sBoard.charAt(i) == 'O') {
                countOnes++;
            }
            if (sBoard.charAt(i) == 'X') {
                countTwos++;
            }
        }

        if (Math.abs(countOnes - countTwos) >= 2) {
            if (countOnes >= countTwos) {
                playerWhoExceedsNumTurns.append("O");
            } else {
                playerWhoExceedsNumTurns.append("X");
            }
            return true;
        } else if (countOnes - countTwos == 1 && sBoard.charAt(0) == 'X') {
            playerWhoExceedsNumTurns.append("O");
            return true;
        } else if (countTwos - countOnes == 1 && sBoard.charAt(0) == 'O') {
            playerWhoExceedsNumTurns.append("X");
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
        stringBoardForFile = stringBoardForFile + player.getPreviousPlayerTurn(player.getTurn()) + "\n";

        for (int i = 1; i <= getHeight(); i++) {
            for (int j = 1; j <= getWidth(); j++) {
                if (getCell(i, j).equals("X")) {
                    stringBoardForFile += "X";
                } else if (getCell(i, j).equals("O")) {
                    stringBoardForFile += "O";
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
            ((TicTacToeGrid) getGrid()).parseStringIntoBoard(toLoad);
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
        player.setCurrentTurn(player.getTurn());
        return true;
    }

    @Override
    /**
     * @param across value of the column
     * @param down value of the row
     * @param input current player turn
     * @return true if all the performed checks and operations were succesful
     */
    public boolean takeTurn(int across, int down, String input) {
        // verify that the move suggested by the user is allowed
        checkBoardMove(across, down);
        checkIfCorrectCharacter(input);
        checkIfCorrectTurn(input);

        setValue(across, down, input);
        return true;
    }

    private void checkIfCorrectCharacter(String input) {
        if (input == null || input.length() != 1 || input.length() == 0 
            || (input.charAt(0) != 'X' && input.charAt(0) != 'O')) {
            throw new RuntimeException("Input is invalid. Please enter 'X' or 'O'");
        }
    }

    private void checkIfCorrectTurn(String input) {
        if (input.charAt(0) != getPlayerTurn()) {
            throw new RuntimeException("It's turn : " + getPlayerTurn() + ". Please wait");
        }
    }

    @Override
    // A method that is required to be implemented by the BoardGame class. It is not used in this game.
    public boolean takeTurn(int across, int down, int input) {
        setValue(across, down, input);
        return false;
    }

    @Override
    // Checking if the game is done and if it is, it is checking who the winner is.
    public boolean isDone() {
        for (int i = 1; i <= getHeight(); i++) {
            if (!getCell(i, i).equals(" ") && getCell(1, i).equals(getCell(2, i)) 
                && getCell(2, i).equals(getCell(3, i))) {
                setGameStateMessage("Congratulations to " + getPlayerTurn() + " player");
                return true;
            }
        }
        for (int i = 1; i <= getWidth(); i++) {
            if (!getCell(i, i).equals(" ") && getCell(i, 1).equals(getCell(i, 2)) 
                && getCell(i, 2).equals(getCell(i, 3))) {
                setGameStateMessage("Congratulations to " + getPlayerTurn() + " player");
                return true;
            }
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

    private boolean checkDiagonalForWin() {
        if (!getCell(1, 1).equals(" ") && getCell(1, 1).equals(getCell(2, 2)) 
            && getCell(2, 2).equals(getCell(3, 3))) {
            setGameStateMessage("Congratulations to " + getPlayerTurn() + " player");
            return true;
        }
        if (!getCell(2, 2).equals(" ") && getCell(3, 1).equals(getCell(2, 2)) 
            && getCell(2, 2).equals(getCell(1, 3))) {
            setGameStateMessage("Congratulations to " + getPlayerTurn() + " player");
            return true;
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
        if (getCell(inputRow, inputColumn).equals("O")
            || getCell(inputRow, inputColumn).equals("X")) {
            setAllowedMove(false);
        }
    }

    private void checkOutOfBounds(int inputRow, int inputColumn) {
        if (inputRow > getHeight() ||  inputColumn > getWidth()
            || inputRow < 0 || inputColumn < 0) {
            setAllowedMove(false);
        }
    }

    private void resetIterator() {
        int dummyValue = 0;
        while (getNextValue() != null) {
            dummyValue = dummyValue + 1;
        }
    }

    /**
     * The method checks if the game is done and if it is, check who the winner is.
     * @return -1 if the game hasn't ended. 1 - if X won, 2 - if O won; 0 - if a tie.
     */
    @Override
    public int getWinner() {
        if (isDone()) {
            if (getPlayerTurn() == 'X') {
                return 1;
            } else if (getPlayerTurn() == 'O') {
                return 2;
            }
            return 0;
        }

        return -1;
    }

    private void setAllowedMove(boolean booleanSetMove) {
        allowedMove = booleanSetMove;
    }

    private boolean getAllowedMove() {
        return allowedMove;
    }

    private void setGameStateMessage(String messageToSet) {
        winnerMessage = messageToSet;
    }

    private void setExceptionMessage(String exMessage) {
        exceptionMessage = exMessage;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    private void setExcpetionOccured(boolean exValue) {
        exceptionOccured = exValue;
    }

    public boolean getExceptionValue() {
        return exceptionOccured;
    }

    @Override
    // Returning the winner message.
    public String getGameStateMessage() {
        return winnerMessage;
    }

    // wrapper for player.getTurn()
    public char getPlayerTurn() {
        return player.getTurn(); 
    }

    // wrapper for player.updateTurn()
    public void updatePlayerTurn(char currTurn) {
        player.updateTurn(currTurn);
    }

    // wrapper for getGrid()
    protected TicTacToeGrid getGridWrapper() {
        return (TicTacToeGrid) getGrid();
    }
}
