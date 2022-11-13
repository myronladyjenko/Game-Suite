package tictactoe;

/**
 * This class is used to control the build the board for the games (from grid)
 * 
 * @author Myron Ladyjenko
 */
public class TicTacToeBoard extends boardgame.Grid {

    // Calling the constructor of the super class, which is Grid.
    public TicTacToeBoard() {
        super(3, 3);
    }

    // Overriding the toString method of the super class, Grid.
    @Override
    public String toString() {
        StringBuilder strBoardBuilder = new StringBuilder();
        strBoardBuilder.append(" ----- ----- ----- \n");
        int counter = 1;

        for (int i = 1; i <= super.getHeight(); i++) {
            strBoardBuilder.append("|     |     |     |\n");

            for (int j = 1; j <= super.getWidth(); j++) {
                strBoardBuilder.append("|  " + getProperEmptyCharacter(super.getValue(i, j), counter) + "  ");
                counter++;
            }

            strBoardBuilder.append("|\n|     |     |     |\n");
            strBoardBuilder.append(" ----- ----- ----- \n");
        }

        return strBoardBuilder.toString();
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
                    setValue(row, column, String.valueOf(toParse.charAt(counter)));
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

    private String getProperEmptyCharacter(String currCellValue, int value) {
        String str = "";
        if (currCellValue == " ") {
            return str + value;
        }

        return currCellValue;
    }
}
