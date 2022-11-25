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

import boardgame.ui.PositionAwareButton;
import game.GameUI;
import game.SavingAndLoadingForGUI;

public class NumericalTicTacToeUIView extends JPanel {

    private JLabel turnLabel;
    private NumericalTicTacToeGame game;
    private PositionAwareButton[][] buttons;
    private GameUI root;
    private boolean valueOfTurnUpdate;
    private int playerWhoWon;
    private JMenuBar menuBar;

    public NumericalTicTacToeUIView(int wide, int tall, GameUI gameFrame) {
        super();
        setLayout(new BorderLayout());
        root = gameFrame;
        root.setTitle("Welcome to Numerical TicTacToe Game");
        setGameController(new NumericalTicTacToeGame(3, 3));   

        removeActionListenerFromGameButtons();
        makeMenuForSaving();
        root.setJMenuBar(menuBar);

        root.getTicTacToeButton().addActionListener(e->saveBoard());
        root.getNumericalTicTacToeButton().addActionListener(e->saveBoard());

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

    /**
     * It creates a menu bar with two menus, one for saving games and one for loading games
     */
    public void makeMenuForSaving() {
        menuBar = new JMenuBar();
        menuBar.setLayout(new BorderLayout());
        JMenu menu = new JMenu("File Actions");

        JMenuItem itemGameSave = new JMenuItem("Save Game");
        JMenuItem itemGameLoad = new JMenuItem("Load Game");

        itemGameSave.addActionListener(e->saveBoard());
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
                if (game.getPlayerTurn() == 'O') {
                    turnLabel.setText("Turn - " + game.getPlayerTurn() + " (Odd numbers)\n");
                } else {
                    turnLabel.setText("Turn - " + game.getPlayerTurn() + " (Even numbers)\n");
                }
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
