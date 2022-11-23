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

    public Player() {
        setFirstTurn();
    }

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
        String stringBoardForFile = "";

        stringBoardForFile = stringBoardForFile + "Player: " + this.getPlayerName() + "\n";
        stringBoardForFile = stringBoardForFile + "Total games played: " + this.getTotalGames() + "\n";
        stringBoardForFile = stringBoardForFile + "              Wins: " + this.getWins() + "\n";
        stringBoardForFile = stringBoardForFile + "            Losses: " + this.getLosses() + "\n";
        stringBoardForFile = stringBoardForFile + "              Ties: " + this.getTies() + "\n\n";
        return stringBoardForFile;
    }

    @Override
    public void loadSavedString(String toLoad) {
        String nameOfPlayer = this.getPlayerName();
        int[] storeInfoAboutPlayer = new int[4];
        int counter = 0;
        int start = 0;
        int num = 0;

        String[] parsedList = toLoad.split("\\s+|:|\\n");
        for (String elem: parsedList) {
            if (elem.equals(nameOfPlayer)) {
                counter = 4;
            } else {
                continue;
            }

            while (counter > 0) {
                try {
                    num = Integer.parseInt(elem);
                } catch (NumberFormatException e) {
                    continue;
                }
                storeInfoAboutPlayer[start] = num;
                start++;
                counter--;
            }
            break;
        }
        fillPlayerFields(storeInfoAboutPlayer);
    }

    private void fillPlayerFields(int[] playerInfo) {
        setTotalGames(playerInfo[0]);
        setWins(playerInfo[1]);
        setLosses(playerInfo[2]);
        setTies(playerInfo[3]);
    }

    /**
     * The toString() function returns a string representation of the current player's turn
     * 
     * @return The current player's turn.
     */
    public String toString() {
        return "Current player is: " + Character.toString(getTurn());
    }

    public void setWins(int currWins) {
        numWins = currWins;
    }

    public int getWins() {
        return numWins;
    }

    public void setLosses(int currLosses) {
        numLosses = currLosses;
    }

    public int getLosses() {
        return numLosses;
    }

    public void setTies(int currTies) {
        numTies = currTies;
    }

    public int getTies() {
        return numTies;
    }

    public void setPlayerName(String name) {
        playerName = name;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setTotalGames(int gamesPlayed) {
        totalGames = gamesPlayed;
    }

    public int getTotalGames() {
        return totalGames;
    }
}

