package tictactoe;

public class TicTacToeGame extends boardgame.BoardGame implements boardgame.Saveable {
    private Player player;

    public TicTacToeGame() {
        super(3, 3);
        setGrid(new TicTacToeBoard());
        player = new Player();
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

    @Override
    public boolean takeTurn(int across, int down, String input) {
        // Check Properly
        setValue(across, down, input);
        return false;
    }

    @Override
    public boolean takeTurn(int across, int down, int input) {
        // Check Properly
        setValue(across, down, input);
        return false;
    }

    @Override
    public boolean isDone() {

        if (!checkTie()) {
            return true;
        }

        return false;
    }

    private boolean checkTie() {
        String cellValue = "";

        cellValue = super.getCell(1, 1);
        if (cellValue.equals("_")) {
            return false;
        }

        while ((cellValue = super.getNextValue()) != null) {
            if (cellValue.equals("_")) {
                return false;
            }
        }
        return true;
    }

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

    @Override
    public String getGameStateMessage() {
        // TODO Auto-generated method stub
        return null;
    }

    // wrapper for player.getTurn()
    public char getPlayerTurn() {
        return player.getTurn(); 
    }

    // wrapper for player.updateTurn()
    public void updatePlayerTurn(char currTurn) {
        player.updateTurn(currTurn);
    }

}
