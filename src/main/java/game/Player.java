package game;

/**
 * This class represents a player for the TicTacToe game. The purpose of this class is 
 * to know information about current player's turn. The class provides methods to access information
 * about player and switch between players.
 * 
 * @author Myron Ladyjenko
 */
public class Player implements boardgame.Saveable {
    private char playerTurn;
    private int numWins;
    private int numTies;
    private int numLosses;
    private int totalGames;
    private String playerName;

    // This is a constructor that is called when a new Player object is created.
    public Player() {
        setFirstTurn();
    }

    /**
     * Constructor for the player object
     * @param currPlayerName stringg that stores the player 
     */
    public Player(String currPlayerName) {
        setFirstTurn();
        setWins(0);
        setTies(0);
        setLosses(0);
        setTotalGames(0);
        setPlayerName(currPlayerName);
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
        if (currTurn == 'X' || currTurn == 'E') {
            playerTurn = 'O';
        } else {
            playerTurn = 'X';
        }
    }

    /**
     * If the current turn is X, return O, otherwise return X
     * 
     * @param currTurn The current player's turn.
     * @return The previous player's turn.
     */
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

    /**
     * This function sets the current player turn to the current player turn passed in
     * 
     * @param currPlayerTurn The current player's turn.
     */
    public void setCurrentTurn(char currPlayerTurn) {
        playerTurn = currPlayerTurn;
    }

    /**
     * This function returns a string that contains the player's name, total games played, wins,
     * losses, and ties
     * 
     * @return A string of the player's name, total games played, wins, losses, and ties.
     */
    @Override
    public String getStringToSave() {
        String stringBoardForFile = "";

        stringBoardForFile = stringBoardForFile + "PlayerName   W   L   T   TG" + "\n";
        stringBoardForFile = stringBoardForFile + getPlayerName() + "   " + getWins()
                             + "   " + getLosses() + "   " + getTies() + "   " + getTotalGames() + "\n";
        return stringBoardForFile;
    }

    /**
     * It takes a string, parses it, and then fills in the player's fields with the parsed information
     * 
     * @param toLoad the string that is loaded from the file
     */
    @Override
    public void loadSavedString(String toLoad) {
        String nameOfPlayer = this.getPlayerName();
        int flag = 0;

        String[] parsedList = toLoad.split("\n");
        for (String elem: parsedList) {
            if (flag == 0) {
                flag = 1;
                continue;
            }
            String[] playerElem = elem.trim().split("\\s+");

            if (nameOfPlayer.equals(playerElem[0])) {
                setPlayerName(playerElem[0]);
                setWins(Integer.valueOf(playerElem[1]));
                setLosses(Integer.valueOf(playerElem[2]));
                setTies(Integer.valueOf(playerElem[3]));
                setTotalGames(Integer.valueOf(playerElem[4]));
                break;
            } else {
                continue;
            }
        }
    }

    /**
     * If the current turn is X, return E, otherwise return O
     * 
     * @param currTurn The current turn of the game.
     * @return The current player's turn.
     */
    public String getNumericalTTTPlayerTurn(char currTurn) {
        if (currTurn == 'X') {
            return "E";
        }
        return "O";
    }

    /**
     * The toString() function returns a string representation of the current player's turn
     * 
     * @return The current player's turn.
     */
    public String toString() {
        return "Current player is: " + Character.toString(getTurn());
    }

    /**
     * This function sets the number of wins for the player
     * 
     * @param currWins The number of wins the player has.
     */
    public void setWins(int currWins) {
        numWins = currWins;
    }

    /**
     * This function returns the number of wins.
     * 
     * @return The number of wins.
     */
    public int getWins() {
        return numWins;
    }

    /**
     * This function sets the number of losses to the value of the parameter.
     * 
     * @param currLosses The number of losses the player has.
     */
    public void setLosses(int currLosses) {
        numLosses = currLosses;
    }

    /**
     * This function returns the number of losses.
     * 
     * @return The number of losses.
     */
    public int getLosses() {
        return numLosses;
    }

    /**
     * This function sets the number of ties to the value of the parameter
     * 
     * @param currTies The number of ties the player has.
     */
    public void setTies(int currTies) {
        numTies = currTies;
    }

    /**
     * This function returns the number of ties
     * 
     * @return The number of ties.
     */
    public int getTies() {
        return numTies;
    }

    /**
     * This function sets the player's name to the value of the name parameter.
     * 
     * @param name The name of the player.
     */
    public void setPlayerName(String name) {
        playerName = name;
    }

    /**
     * This function returns the name of the player
     * 
     * @return The playerName variable is being returned.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * This function sets the total number of games played by the player
     * 
     * @param gamesPlayed The number of games played.
     */
    public void setTotalGames(int gamesPlayed) {
        totalGames = gamesPlayed;
    }

    /**
     * This function returns the total number of games played
     * 
     * @return The total number of games played.
     */
    public int getTotalGames() {
        return totalGames;
    }
}

