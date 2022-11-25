package numericaltictactoe;

/**
 * This class is used to control the build the board for the game 
 * Numerical TicTacToe (from grid)
 * 
 * @author Myron Ladyjenko
 */
public class NumericalTicTacToeGrid extends boardgame.Grid {

    // Calling the constructor of the superclass, which is the Grid class.
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
                    setValue(row, column, Character.getNumericValue(toParse.charAt(counter)));
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
