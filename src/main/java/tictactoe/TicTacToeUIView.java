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
import game.FileHandling;
import game.GameUI;
import game.ThrowExceptionFileActionHasFailed;

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

    public TicTacToeUIView(int wide, int tall, GameUI gameFrame) {
        super();
        setLayout(new BorderLayout());
        root = gameFrame;
        setGameController(new TicTacToeGame());   

        makeMenuForSaving();
        root.setJMenuBar(menuBar);

        turnLabel = new JLabel("Turn - " + game.getPlayerTurn() + "\n");
        turnLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        turnLabel.setForeground(Color.BLUE);
        add(turnLabel, BorderLayout.NORTH);

        add(makeButtonGrid(tall, wide));
        root.pack();
    }

    /**
     * It creates a menu bar with two menus, one for saving games and one for loading games
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
        menuBar.setVisible(false);
        this.removeAll();
        root.startGame();
    }

    private JPanel makeButtonGrid(int tall, int wide) {
        JPanel panel = new JPanel();
        buttons = new PositionAwareButton[game.getHeight()][game.getWidth()];
        panel.setLayout(new GridLayout(game.getWidth(), game.getHeight()));
        panel.setPreferredSize(new Dimension(400, 400));

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

    private void loadBoard() {
        String inputCharacter = JOptionPane.showInputDialog("Would you like to load file? Enter y- 'yes' and n - 'no'");

        if (!inputCharacter.equals("") && inputCharacter != null && inputCharacter.charAt(0) == 'y') {
            root.selectLocationOfTheFile(0);
        
            try {
                TicTacToeGame loadedGame = new TicTacToeGame();
                FileHandling.loadFile(root.getFilePath(), loadedGame);
                game = loadedGame;
                startLoadedGame();
            } catch (ThrowExceptionFileActionHasFailed e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Board hasn't been loaded");
        }
    }

    private void saveBoard() {
        String inputCharacter = JOptionPane.showInputDialog("Would you like to save? Enter y - 'yes' and n - 'no'"); 

        if (!inputCharacter.equals("") && inputCharacter != null && inputCharacter.charAt(0) == 'y') {
            root.selectLocationOfTheFile(1);
        
            try {

                FileHandling.saveToFile(root.getFilePath(), game);
            } catch (ThrowExceptionFileActionHasFailed e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Board hasn't been saved");
        }
    }

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

    protected void startNewGame() {
        game.newGame();
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
    public void setGameController(TicTacToeGame controller) {
        this.game = controller;
    }

    private void makeUserTurn(ActionEvent e) {
        setTurnUpdate(false);
        // String inputCharacter = JOptionPane.showInputDialog("Please input a character: 'X' or 'O'"); 

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

    public int getPlayerWhoWon() {
        return playerWhoWon;
    }
}
