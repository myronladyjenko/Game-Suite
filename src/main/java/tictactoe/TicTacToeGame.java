package tictactoe;

import game.Player;

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

    // Creating a new TicTacToeGame object.
    public TicTacToeGame() {
        super(3, 3);
        setGrid(new TicTacToeBoard());
        player = new Player();
    }

    /**
     * It checks if the board read from the file is valid
     * 
     * @param stringBoard the string representation of the board read from the file
     * @throws ThrowExceptionWrongBoardFormat an exception that occurs when the board loaded is in the wring format
     * @throws ThrowExceptionTheGameHasEnded an exception that occurs when the game has ended
     */
    public void validateBoardFromFile(String stringBoard) throws ThrowExceptionWrongBoardFormat, 
                                                                 ThrowExceptionTheGameHasEnded {
        if (stringBoard.length() < 11 || stringBoard.length() > 20) {
            throw new ThrowExceptionWrongBoardFormat("Length of the board read from"
                                                     + " file doesn't match the expected one. Please restart.\n");
        }

        if (countCommas(stringBoard) != 6) {
            throw new ThrowExceptionWrongBoardFormat("Board has incorrect number of commas or newlines.\n");
        }
        
        if (stringBoard.charAt(0) != 'O' && stringBoard.charAt(0) != 'X') {
            throw new ThrowExceptionWrongBoardFormat("The read file doesn't contain the player information.\n");
        }

        if (checkForUnexpectedSymbols(stringBoard)) {
            throw new ThrowExceptionWrongBoardFormat("The read file contains unexpected characters.\n");
        }
        StringBuilder playerWithWrongNumOfMoves = new StringBuilder("");
        if (checkIncorrectNumberOfMoves(stringBoard, playerWithWrongNumOfMoves)) {
            throw new ThrowExceptionWrongBoardFormat("Player: " + playerWithWrongNumOfMoves.toString()
                                                     + " did 2 or more moves then the other player. Please restart.\n");
        }
        TicTacToeGame testBoard = new TicTacToeGame();
        testBoard.loadSavedString(stringBoard);
        if (testBoard.isDone()) {
            throw new ThrowExceptionTheGameHasEnded("The game on this board has finished. Please restart.\n");
        }
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
                playerWhoExceedsNumTurns.append("X");
            } else {
                playerWhoExceedsNumTurns.append("O");
            }
            return true;
        }
        return false;
    }

    @Override
    // Creating a string that represents the board and the player turn.
    public String getStringToSave() {
        String stringBoardForFile = "";
        stringBoardForFile = stringBoardForFile + player.getPreviousPlayerTurn(player.getTurn()) + "\n";

        for (int i = 1; i <= super.getHeight(); i++) {
            for (int j = 1; j <= super.getWidth(); j++) {
                if (getCell(i, j).equals("X")) {
                    stringBoardForFile += "X";
                } else if (getCell(i, j).equals("O")) {
                    stringBoardForFile += "O";
                } else {
                    stringBoardForFile += "";
                }

                if (j % super.getWidth() != 0) {
                    stringBoardForFile += ",";
                }
            }
            stringBoardForFile += "\n";
        }
        return stringBoardForFile;
    }

    @Override
    // Loading the saved string into the board.
    public void loadSavedString(String toLoad) {
        player.updateTurn(toLoad.charAt(0));
        player.setCurrentTurn(player.getTurn());
        ((TicTacToeBoard) getGrid()).parseStringIntoBoard(toLoad);
    }

    @Override
    // Checking if the move is allowed and if it is, it is setting the value on the board.
    public boolean takeTurn(int across, int down, String input) {
        // Check Properly
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
        for (int i = 1; i <= super.getHeight(); i++) {
            if (!super.getCell(i, i).equals(" ") && super.getCell(1, i).equals(super.getCell(2, i)) 
                && super.getCell(2, i).equals(super.getCell(3, i))) {
                setGameStateMessage("Congratulations to " + getPlayerTurn() + " player");
                return true;
            }
        }
        for (int i = 1; i <= super.getWidth(); i++) {
            if (!super.getCell(i, i).equals(" ") && super.getCell(i, 1).equals(super.getCell(i, 2)) 
                && super.getCell(i, 2).equals(super.getCell(i, 3))) {
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
        if (!super.getCell(1, 1).equals(" ") && super.getCell(1, 1).equals(super.getCell(2, 2)) 
            && super.getCell(2, 2).equals(super.getCell(3, 3))) {
            setGameStateMessage("Congratulations to " + getPlayerTurn() + " player");
            return true;
        }
        if (!super.getCell(2, 2).equals(" ") && super.getCell(3, 1).equals(super.getCell(2, 2)) 
            && super.getCell(2, 2).equals(super.getCell(1, 3))) {
            setGameStateMessage("Congratulations to " + getPlayerTurn() + " player");
            return true;
        }

        return false;
    }

    private boolean checkTie() {
        resetIterator();
        String cellValue = "";

        cellValue = super.getCell(1, 1);
        if (cellValue.equals(" ")) {
            return false;
        }

        while ((cellValue = super.getNextValue()) != null) {
            if (cellValue.equals(" ")) {
                return false;
            }
        }
        return true;
    }

    /**
     * The method checks whether the move inputed by the user is allowed on the board
     * @param inputPosition position of where to do a move on the board
     * @param charList List that represents values on stored on the board (1 to 9, 'X', or 'O')
     * @return whether the user is allowed to make a move (true) or not
     */
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
        if (super.getCell(inputRow, inputColumn).equals("O")
            || super.getCell(inputRow, inputColumn).equals("X")) {
            setAllowedMove(false);
        }
    }

    private void checkOutOfBounds(int inputRow, int inputColumn) {
        if (inputRow > super.getHeight() ||  inputColumn > super.getWidth()
            || inputRow < 0 || inputColumn < 0) {
            setAllowedMove(false);
        }
    }

    private void resetIterator() {
        int dummyValue = 0;
        while (super.getNextValue() != null) {
            dummyValue = dummyValue + 1;
        }
    }

    @Override
    // Checking if the game is done and if it is, it is checking who the winner is.
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
    public TicTacToeBoard getGridWrapper() {
        return (TicTacToeBoard) super.getGrid();
    }
}
