package game;

import javax.swing.JOptionPane;

import boardgame.Saveable;
import numericaltictactoe.NumericalTicTacToeGame;
import tictactoe.TicTacToeGame;

public class SavingAndLoadingForGUI {

    public Saveable loadBoardUI(GameUI gameUI, int gameOption) {
        do {
            int optionSelected = JOptionPane.showConfirmDialog(null, "Would you like to load the board?", 
                                                            "User Choice", JOptionPane.YES_NO_CANCEL_OPTION);
            if (optionSelected == 0) {
                gameUI.selectLocationOfTheFile(0);
                if (gameUI.getFilePath() == null) {
                    continue;
                }

                Saveable currentGame = loadTheCorrectGame(gameUI, gameOption);
                if (currentGame != null) {
                    return currentGame;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Board hasn't been loaded");
                break;
            }
        } while(true);

        return null;
    }

    private Saveable loadTheCorrectGame(GameUI gameUI, int gameOption) {
        try {
            if (gameOption == 0) {
                TicTacToeGame loadedGame = new TicTacToeGame();
                FileHandling.loadFile(gameUI.getFilePath(), loadedGame);
                if (loadedGame.getExceptionValue()) {
                    JOptionPane.showMessageDialog(null, loadedGame.getExceptionMessage());
                } else {
                    return (Saveable) loadedGame;
                }
            } else {
                NumericalTicTacToeGame loadedGame = new NumericalTicTacToeGame(3, 3);
                FileHandling.loadFile(gameUI.getFilePath(), loadedGame);
                if (loadedGame.getExceptionValue()) {
                    JOptionPane.showMessageDialog(null, loadedGame.getExceptionMessage());
                } else {
                    return (Saveable) loadedGame;
                }
            }
        } catch (ThrowExceptionFileActionHasFailed e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return null;
    }

    public void saveBoardUI(GameUI gameUI, Saveable gameToSave) { 
        do {
            int optionSelected = JOptionPane.showConfirmDialog(null, "Would you like to save the board?", 
                                                           "User Choice", JOptionPane.YES_NO_CANCEL_OPTION);
            if (optionSelected == 0) {
                gameUI.selectLocationOfTheFile(1);
                if (gameUI.getFilePath() == null) {
                    continue;
                }
            
                try {
                    FileHandling.saveToFile(gameUI.getFilePath(), gameToSave);
                    break;
                } catch (ThrowExceptionFileActionHasFailed e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Board hasn't been saved");
                break;
            }
        } while(true);
    }
}
