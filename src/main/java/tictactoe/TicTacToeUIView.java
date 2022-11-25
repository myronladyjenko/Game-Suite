package tictactoe;

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

import boardgame.ui.PositionAwareButton;
import game.GameUI;
import game.SavingAndLoadingForGUI;

/**
 * This class is used to create a UI version of TicTacToe
 * 
 * @author Myron Ladyjenko
 */
public class TicTacToeUIView extends JPanel {

    private JLabel turnLabel;
    private TicTacToeGame game;
    private PositionAwareButton[][] buttons;
    private GameUI root;
    private boolean valueOfTurnUpdate;
    private int playerWhoWon;
    private JMenuBar menuBar;

    // This is the constructor for the TicTacToeUIView class. It is used to create a UI version of
    // TicTacToe.
    public TicTacToeUIView(int wide, int tall, GameUI gameFrame) {
        super();
        setLayout(new BorderLayout());
        root = gameFrame;
        root.setTitle("Welcome to TicTacToe Game");
        setGameController(new TicTacToeGame());  
        
        removeActionListenerFromGameButtons();
        makeMenuForSaving();
        root.setJMenuBar(menuBar);

        root.getTicTacToeButton().addActionListener(e->saveBoard());
        root.getNumericalTicTacToeButton().addActionListener(e->saveBoard());

        turnLabel = new JLabel("Turn - " + game.getPlayerTurn() + "\n");
        turnLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        turnLabel.setForeground(Color.BLUE);
        add(turnLabel, BorderLayout.NORTH);

        add(makeButtonGrid(tall, wide));
        root.pack();
    }

    /**
     * This function creates a menu bar with two menu items, one for saving the game and one for
     * loading the game
     */
    public void makeMenuForSaving() {
        menuBar = new JMenuBar();
        menuBar.setLayout(new BorderLayout());
        JMenu menu = new JMenu("File Actions");

        JMenuItem itemTicaTacToeGameSave = new JMenuItem("Save Game");
        JMenuItem itemTicaTacToeGameLoad = new JMenuItem("Load Game");

        itemTicaTacToeGameSave.addActionListener(e->saveBoard());
        itemTicaTacToeGameLoad.addActionListener(e->loadBoard());

        menu.add(itemTicaTacToeGameSave);
        menu.add(itemTicaTacToeGameLoad);

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
        menuBar.setVisible(false);
        this.removeAll();
        removeActionListenerFromGameButtons();
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
            setPlayerWhoWon(game.getWinner());
            playerSelection = JOptionPane.showConfirmDialog(null, game.getGameStateMessage() + "." 
                                                + "\nWould you like to play again?", null, JOptionPane.YES_NO_OPTION);

            if (playerSelection == JOptionPane.NO_OPTION) {
                menuBar.setVisible(false);
                this.removeAll();
                removeActionListenerFromGameButtons();
                root.startGame();
            } else {
                startNewGame();
            }
        } else {
            if (getTurnUpdate()) {
                game.updatePlayerTurn(game.getPlayerTurn());
                turnLabel.setText("Turn - " + game.getPlayerTurn() + "\n");
            }
        }
    }

    private void removeActionListenerFromGameButtons() {
        JButton button = root.getTicTacToeButton();
        if (button.getActionListeners().length == 2) {
            button.removeActionListener(button.getActionListeners()[0]);
        }

        JButton numericalButton = root.getNumericalTicTacToeButton();
        if (numericalButton.getActionListeners().length == 2) {
            numericalButton.removeActionListener(numericalButton.getActionListeners()[0]);
        }
    }

    private void saveBoard() {
        SavingAndLoadingForGUI saveAndLoad = new SavingAndLoadingForGUI();
        saveAndLoad.saveBoardUI(root, game);
    }

    private void loadBoard() {
        SavingAndLoadingForGUI saveAndLoad = new SavingAndLoadingForGUI();
        game = (TicTacToeGame) saveAndLoad.loadBoardUI(root, 0);
        if (game != null) {
            startLoadedGame();
        }
    }

    /**
     * This function resets the values of the board and then sets the text of each button to the value
     * of the cell in the game
     */
    protected void startLoadedGame() {
        resetValuesOfTheBoard();

        for (int i = 0; i < game.getHeight(); i++) {
            for (int j=0; j < game.getWidth(); j++) {
                buttons[i][j].setText(game.getCell(buttons[i][j].getAcross(), buttons[i][j].getDown())); 
            }
        }
        turnLabel.setText("Turn - " + game.getPlayerTurn() + "\n");
    }

    private void resetValuesOfTheBoard() {
        for (int i = 0; i < game.getHeight(); i++) {
            for (int j=0; j < game.getWidth(); j++) {
                buttons[i][j].setText(" "); 
            }
        }
    }

    /**
     * This function starts a new game by calling the newGame() function in the game class, and then
     * updates the turn label and the text on the buttons to reflect the new game
     */
    protected void startNewGame() {
        game.newGame();
        turnLabel = new JLabel("Turn - " + game.getPlayerTurn() + "\n");

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
    protected void setGameController(TicTacToeGame controller) {
        this.game = controller;
    }

    private void makeUserTurn(ActionEvent e) {
        setTurnUpdate(false);

        PositionAwareButton clicked = (PositionAwareButton) (e.getSource());
        try {
            if (game.takeTurn(clicked.getAcross(), clicked.getDown(), Character.toString(game.getPlayerTurn()))) {
                setTurnUpdate(true);
                clicked.setText(game.getCell(clicked.getAcross(), clicked.getDown()));
                root.pack();
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void setTurnUpdate(boolean turnUpdateValue) {
        valueOfTurnUpdate = turnUpdateValue;
    }

    private boolean getTurnUpdate() {
        return valueOfTurnUpdate;
    }

    public void setPlayerWhoWon(int player) {
        playerWhoWon = player;
    }

    /**
     * This function returns the player who won the game
     * 
     * @return The player who won the game.
     */
    public int getPlayerWhoWon() {
        return playerWhoWon;
    }
}
