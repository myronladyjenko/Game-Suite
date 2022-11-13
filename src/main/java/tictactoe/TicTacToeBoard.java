package tictactoe;

public class TicTacToeBoard extends boardgame.Grid {

    public TicTacToeBoard() {
        super(3, 3);
    }

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
