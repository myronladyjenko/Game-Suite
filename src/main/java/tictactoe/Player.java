package tictactoe;

/**
 * This class represents a player for the TicTacToe game. The purpose of this class is 
 * to know information about current player's turn. The class provides methods to access information
 * about player and switch between players.
 * 
 * @author Myron Ladyjenko
 */
public class Player implements boardgame.Saveable {
    private char playerTurn;

    public Player() {
        setFirstTurn();
    }

    /**
     * This method gets the turn of the player
     * @return next players turn: 'X' or 'O'
     */
    public char getTurn() {
        return playerTurn;
    }

    /**
     * This method sets the next players turn 'X' or 'O' into 
     * the variable playerTurn
     * @param currTurn current player turn
     */
    public void updateTurn(char currTurn) {
        if (currTurn == 'X') {
            playerTurn = 'O';
        } else {
            playerTurn = 'X';
        }
    }

    public char getPreviousPlayerTurn(char currTurn) {
        if (currTurn == 'X') {
            return 'O';
        }
        return 'X';
    }

    private void setFirstTurn() {
        int max = 2;
        int min = 1;
        int range = max - min + 1;

        int rand = (int)(Math.random() * range) + min;
        if (rand == 1) {
            playerTurn = 'X';
        } else {
            playerTurn = 'O';
        }
    }

    public void setCurrentTurn(char currPlayerTurn) {
        playerTurn = currPlayerTurn;
    }

    @Override
    public String getStringToSave() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void loadSavedString(String toLoad) {
        // TODO Auto-generated method stub
    }

    public String toString() {
        return "Current player is: " + Character.toString(getTurn());
    }
}
