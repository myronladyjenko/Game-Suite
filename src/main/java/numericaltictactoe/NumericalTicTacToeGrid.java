package numericaltictactoe;

public class NumericalTicTacToeGrid extends boardgame.Grid {

    public NumericalTicTacToeGrid() {
        super(3, 3);
    }

    /**
     * It takes a string, and parses it into the board
     * 
     * @param toParse The string to parse into the board.
     */
    public void parseStringIntoBoard(String toParse) {
        int counter = 2;
        int row = 1;
        int column = 1;

        while (row <= getHeight()) {
            if (toParse.charAt(counter) != '\n') {
                if (toParse.charAt(counter) != ',') {
                    setValue(row, column, Integer.valueOf(toParse.charAt(counter)));
                } else {
                    column++;
                }
            } else {
                row++;
                column = 1;
            }
            counter++;
        }
    }
}
