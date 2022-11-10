package tictactoe;

public class TicTacToeBoard extends boardgame.Grid {

    public TicTacToeBoard() {
        super(3, 3);
    }

    @Override
    public String toString() {
        StringBuilder strBoardBuilder = new StringBuilder();
        strBoardBuilder.append(" ----- ----- ----- \n");
 
        for (int i = 1; i <= super.getHeight(); i++) {
            strBoardBuilder.append("|     |     |     |\n");

            for (int j = 1; j <= super.getWidth(); j++) {
                strBoardBuilder.append("|  " + getProperEmptyCharacter(super.getValue(i, j)) + "  ");
            }

            strBoardBuilder.append("|\n|     |     |     |\n");
            strBoardBuilder.append(" ----- ----- ----- \n");
        }

        return strBoardBuilder.toString();
    }

    private String getProperEmptyCharacter(String currCellValue) {
        if (currCellValue == " ") {
            return "_";
        }

        return currCellValue;
    }
}
