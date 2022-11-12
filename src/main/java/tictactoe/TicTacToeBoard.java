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
        int numBoardElements = 0;
        int numCommas = 0;
        int counter = 1;
        int column = 1;
        for (int i = 1; i <= getHeight(); i++) {
            while (numCommas + numBoardElements != 5) {
                if (toParse.charAt(counter) == ',') {
                    if (numCommas != 2) {
                        numCommas++;
                        counter++;
                    }
                } else {
                    setValue(i, column, String.valueOf(toParse.charAt(counter)));
                    if (numCommas == 2) {
                        counter++;
                    } else {
                        counter += 2;
                        numCommas++;
                    }
                }
                numBoardElements++;
                column++;
            }
            numCommas = 0;
            column = 1;
            numBoardElements = 0;
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
