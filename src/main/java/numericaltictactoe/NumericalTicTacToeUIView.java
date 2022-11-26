package numericaltictactoe;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.StandardOpenOption;

import boardgame.ui.PositionAwareButton;
import game.FileHandling;
import game.GameUI;
import game.Player;
import game.SavingAndLoadingForGUI;
import game.ThrowExceptionFileActionHasFailed;

/**
 * This class is used to create a UI version of Numerical TicTacToe
 * 
 * @author Myron Ladyjenko
 */
public class NumericalTicTacToeUIView extends JPanel {

    private JLabel turnLabel;
    private NumericalTicTacToeGame game;
    private PositionAwareButton[][] buttons;
    private GameUI root;
    private boolean valueOfTurnUpdate;
    private JMenuBar menuBar;
    private JButton ticTacToeButton;
    private JButton numericalTicTacToeButton;

    private Player playerOne;
    private Player playerTwo;

    /**
     * This is the constructor for the NumericalTicTacToeUIView class. It is used to create a UI
    // version of Numerical TicTacToe.
     * @param wide the width of the grid to be created
     * @param tall the length of the grid to be created
     * @param gameFrame the main GameUI
     */
    public NumericalTicTacToeUIView(int wide, int tall, GameUI gameFrame) {
        super();
        setLayout(new BorderLayout());
        root = gameFrame;
        root.setTitle("Welcome to Numerical TicTacToe Game");
        setGameController(new NumericalTicTacToeGame(3, 3)); 
        
        playerOne = new Player("PlayerOne");
        playerTwo = new Player("PlayerTwo");

        removeActionListenerFromGameButtons();
        makeMenuForSaving();
        root.setJMenuBar(menuBar);

        ticTacToeButton = root.getTicTacToeButton();
        ticTacToeButton.addActionListener(e->saveBoard()); 
        ticTacToeButton.addActionListener(e->askToSavePlayerStatistics());

        numericalTicTacToeButton = root.getNumericalTicTacToeButton();
        numericalTicTacToeButton.addActionListener(e->saveBoard());

        makePlayerOddStart();
        turnLabel = new JLabel("Turn - " + game.getPlayerTurn() + " (Odd numbers)\n");
        turnLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        turnLabel.setForeground(Color.BLUE);
        add(turnLabel, BorderLayout.NORTH);

        add(makeButtonGrid(tall, wide));
        root.pack();
    }

    private void makePlayerOddStart() {
        if (game.getPlayerTurn() == 'E') {
            game.updatePlayerTurn(game.getPlayerTurn());
        }
    }

    private void makeMenuForSaving() {
        menuBar = new JMenuBar();
        menuBar.setLayout(new BorderLayout());
        JMenu menu = new JMenu("File Actions");

        JMenuItem itemGameSave = new JMenuItem("Save Game");
        JMenuItem itemGameLoad = new JMenuItem("Load Game");

        itemGameSave.addActionListener(e->{
                                      saveBoard();
                                      askToSavePlayerStatistics();
                                      });
        itemGameLoad.addActionListener(e->loadBoard());

        menu.add(itemGameSave);
        menu.add(itemGameLoad);

        menuBar.add(menu, BorderLayout.WEST);
        addButtonToMenuBar();
        menuBar.setVisible(true);
    }

    private void addButtonToMenuBar() {
        JButton returnButton = new JButton("Return to Main Window");
        returnButton.addActionListener(e->returnMain());
        menuBar.add(returnButton, BorderLayout.EAST);
    }

    private void returnMain() {
        saveBoard();
        askToSavePlayerStatistics();
        removeActionListenerFromGameButtons();
        menuBar.setVisible(false);
        this.removeAll();
        root.startGame();
    }

    private JPanel makeButtonGrid(int tall, int wide) {
        JPanel panel = new JPanel();
        buttons = new PositionAwareButton[game.getHeight()][game.getWidth()];
        panel.setLayout(new GridLayout(game.getWidth(), game.getHeight()));
        panel.setPreferredSize(new Dimension(450, 450));

        for (int i = 0; i < wide; i++) {
            for (int j = 0; j < tall; j++) { 
                buttons[i][j] = new PositionAwareButton();
                buttons[i][j].setFont(new Font("Times New Roman", Font.PLAIN, 40));  
                buttons[i][j].setAcross(i + 1);
                buttons[i][j].setDown(j + 1);
                buttons[i][j].addActionListener(e->{
                                        makeUserTurn(e);
                                        checkCurrentGameState(e);
                                        });
                panel.add(buttons[i][j]);
            }
        }
        return panel;
    }

    private void checkCurrentGameState(ActionEvent e) {
        int playerSelection = 0;

        if (game.isDone()) {
            updatePlayerStats();

            playerSelection = JOptionPane.showConfirmDialog(null, game.getGameStateMessage() + "." 
                                                + "\nWould you like to play again?", null, JOptionPane.YES_NO_OPTION);

            if (playerSelection == JOptionPane.NO_OPTION) {
                askToSavePlayerStatistics();
                removeActionListenerFromGameButtons();
                menuBar.setVisible(false);
                this.removeAll();
                root.startGame();
            } else {
                startNewGame();
            }
        } else {
            if (getTurnUpdate()) {
                game.updatePlayerTurn(game.getPlayerTurn());
                if (game.getPlayerTurn() == 'O') {
                    turnLabel.setText("Turn - " + game.getPlayerTurn() + " (Odd numbers)\n");
                } else {
                    turnLabel.setText("Turn - " + game.getPlayerTurn() + " (Even numbers)\n");
                }
            }
        }
    }

    private void updatePlayerStats() {
        if (game.getWinner() == 1) {
            playerOne.setWins(playerOne.getWins() + 1);
            playerTwo.setLosses(playerTwo.getLosses() + 1);
            playerOne.setTotalGames(playerOne.getTotalGames() + 1);
            playerTwo.setTotalGames(playerTwo.getTotalGames() + 1);
        } else if (game.getWinner() == 2) {
            playerTwo.setWins(playerTwo.getWins() + 1);
            playerOne.setLosses(playerOne.getLosses() + 1);
            playerOne.setTotalGames(playerOne.getTotalGames() + 1);
            playerTwo.setTotalGames(playerTwo.getTotalGames() + 1);
        } else if (game.getWinner() == 0) {
            playerOne.setTies(playerOne.getTies() + 1);
            playerTwo.setTies(playerTwo.getTies() + 1);
            playerOne.setTotalGames(playerOne.getTotalGames() + 1);
            playerTwo.setTotalGames(playerTwo.getTotalGames() + 1);
        }
    }

    private void removeActionListenerFromGameButtons() {
        if (ticTacToeButton != null) {
            ActionListener[] arrayActionListeners = ticTacToeButton.getActionListeners();
            for (int i = 0; i < arrayActionListeners.length - 1; i++) {
                ticTacToeButton.removeActionListener(arrayActionListeners[i]);
            }
        }

        if (numericalTicTacToeButton != null) {
            ActionListener[] arrayActionListeners = numericalTicTacToeButton.getActionListeners();
            for (int i = 0; i < arrayActionListeners.length - 1; i++) {
                numericalTicTacToeButton.removeActionListener(arrayActionListeners[i]);
            }
        }
    }

    private void saveBoard() {
        SavingAndLoadingForGUI saveAndLoad = new SavingAndLoadingForGUI();
        saveAndLoad.saveBoardUI(root, game);
    }

    private void loadBoard() {
        SavingAndLoadingForGUI saveAndLoad = new SavingAndLoadingForGUI();
        game = (NumericalTicTacToeGame) saveAndLoad.loadBoardUI(root, 1);
        if (game != null) {
            startLoadedGame();
        }
    }

    protected void startLoadedGame() {
        resetValuesOfTheBoard();

        for (int i = 0; i < game.getHeight(); i++) {
            for (int j=0; j < game.getWidth(); j++) {
                buttons[i][j].setText(game.getCell(buttons[i][j].getAcross(), buttons[i][j].getDown())); 
            }
        }
        if (game.getPlayerTurn() == 'O') {
            turnLabel.setText("Turn - " + game.getPlayerTurn() + " (Odd numbers)\n");
        } else {
            turnLabel.setText("Turn - " + game.getPlayerTurn() + " (Even numbers)\n");
        }
    }

    private void resetValuesOfTheBoard() {
        for (int i = 0; i < game.getHeight(); i++) {
            for (int j=0; j < game.getWidth(); j++) {
                buttons[i][j].setText(" "); 
            }
        }
    }

    protected void startNewGame() {
        game.newGame();
        makePlayerOddStart();
        turnLabel = new JLabel("Turn - " + game.getPlayerTurn() + " (Odd numbers)\n");

        for (int i = 0; i < game.getHeight(); i++) {
            for (int j=0; j < game.getWidth(); j++) {
                buttons[i][j].setText(game.getCell(buttons[i][j].getAcross(), buttons[i][j].getDown())); 
            }
        }
    }

    /**
     * This function sets the game controller to the controller passed in as a parameter.
     * 
     * @param controller The controller object that will be used to control the game.
     */
    public void setGameController(NumericalTicTacToeGame controller) {
        this.game = controller;
    }

    private void makeUserTurn(ActionEvent e) {
        setTurnUpdate(false);
        String inputDigit = JOptionPane.showInputDialog("Please input a number between 0 or 9"); 
        int input = 0;

        do {
            try {
                input = Integer.valueOf(inputDigit);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Input cannot be converted to an integer");
                break;
            }

            PositionAwareButton clicked = (PositionAwareButton) (e.getSource());
            try {
                if (game.takeTurn(clicked.getAcross(), clicked.getDown(), input)) {
                    setTurnUpdate(true);
                    clicked.setText(game.getCell(clicked.getAcross(), clicked.getDown()));
                    root.pack();
                    break;
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                break;
            }
        } while(true);
    }

    private void askToSavePlayerStatistics() {
         int playerSelection = JOptionPane.showConfirmDialog(null, "Would you like to save " 
                                                            + "player's statistics profiles?", 
                                                            "Player Profile Save", JOptionPane.YES_NO_OPTION);
        if (playerSelection == 0) {
            saveUserProfile();
        } else {
            JOptionPane.showMessageDialog(null, "Player's profiles haven't been saved");
        }
    }

    /**
     * The function is used to load the user profile from a file
     */
    public void loadUserProfile() {
        root.selectLocationOfTheFile(0);

        do {
            if (root.getFilePath() == null) {
                JOptionPane.showMessageDialog(null, "Please select a correct file.");
                break;
            }

            try {
                FileHandling.loadFile(root.getFilePath(), playerOne);
                FileHandling.loadFile(root.getFilePath(), playerTwo);
            } catch (ThrowExceptionFileActionHasFailed e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            break;
        } while(true);
    }

    private void saveUserProfile() {
        root.selectLocationOfTheFile(1);

        do {
            if (root.getFilePath() == null) {
                JOptionPane.showMessageDialog(null, "Please select a correct file.");
                break;
            }

            try {
                FileHandling.saveToFile(root.getFilePath(), playerOne, StandardOpenOption.TRUNCATE_EXISTING);
                FileHandling.saveToFile(root.getFilePath(), playerTwo, StandardOpenOption.APPEND);
            } catch (ThrowExceptionFileActionHasFailed e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            break;
        } while(true);
    }

    private void setTurnUpdate(boolean turnUpdateValue) {
        valueOfTurnUpdate = turnUpdateValue;
    }

    private boolean getTurnUpdate() {
        return valueOfTurnUpdate;
    }
}
