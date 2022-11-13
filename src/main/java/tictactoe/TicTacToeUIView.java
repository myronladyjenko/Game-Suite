package tictactoe;

// import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import boardgame.ui.PositionAwareButton;
import game.FileHandling;
import game.GameUI;
import game.ThrowExceptionFileActionHasFailed;

public class TicTacToeUIView extends JPanel {

    private JLabel turnLabel;
    private TicTacToeGame game;
    private PositionAwareButton[][] buttons;
    private GameUI root;
    private boolean valueOfTurnUpdate;

    public TicTacToeUIView(int wide, int tall, GameUI gameFrame) {
        super();
        setLayout(new BorderLayout());
        root = gameFrame;
        setGameController(new TicTacToeGame());   

        JLabel startMessageLabel = new JLabel("Welcome to TicTacToe");
        turnLabel = new JLabel("Turn - " + game.getPlayerTurn() + "\n");

        this.add(startMessageLabel, BorderLayout.NORTH);
        this.add(turnLabel, BorderLayout.NORTH);
        this.add(makeNewGameButton());
        this.add(makeButtonGrid(tall, wide));
    }

    private JPanel makeButtonGrid(int tall, int wide) {
        JPanel panel = new JPanel();
        buttons = new PositionAwareButton[game.getHeight()][game.getWidth()];
        panel.setLayout(new GridLayout(game.getWidth(), game.getHeight()));

        for (int i = 0; i < wide; i++) {
            for (int j = 0; j < tall; j++) { 
                buttons[i][j] = new PositionAwareButton();
                buttons[i][j].setAcross(j+1);
                buttons[i][j].setDown(i+1);
                buttons[i][j].addActionListener(e->{
                                        enterCharacter(e);
                                        checkCurrentGameState(e);
                                        });
                panel.add(buttons[i][j]);
            }
        }
        return panel;
    }

    private void checkCurrentGameState(ActionEvent e) {
        int playerSelection= 0;

        if (game.isDone()) {
            playerSelection = JOptionPane.showConfirmDialog(null, game.getGameStateMessage(),
                                                     "Would you like to play again", JOptionPane.YES_NO_OPTION);
            handleStepsToSave();
            if (playerSelection == JOptionPane.NO_OPTION) {
                root.start();
            } else {
                newGame();
            }
        } else {
            if (getTurnUpdate()) {
                game.updatePlayerTurn(game.getPlayerTurn());
                turnLabel.setText("Turn - " + game.getPlayerTurn() + "\n");
            }
        }
    }

    private void handleStepsToSave() {
        FileHandling fileHandlerForBoard = new FileHandling();
        String inputCharacter = JOptionPane.showInputDialog("Would you like to save? Enter y - 'yes' and n - 'no'"); 

        if (inputCharacter.charAt(0) == 'y') {
            root.saveGameState();
            try {
                fileHandlerForBoard.saveToFile(root.getFilePath(), game.getStringToSave());
            } catch (ThrowExceptionFileActionHasFailed e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Board hasn't been saved");
        }
    }

    private JButton makeNewGameButton() {
        JButton button = new JButton("New TicTacToe game");
        button.addActionListener(e->newGame());
        return button;
    }

    protected void newGame() {
        game.newGame();
        updateView();
    }

    protected void updateView() {
        for (int i = 0; i < game.getHeight(); i++) {
            for (int j=0; j < game.getWidth(); j++) {  
                buttons[i][j].setText(game.getCell(buttons[i][j].getAcross(), buttons[i][j].getDown())); 
            }
        }
    }

    public void setGameController(TicTacToeGame controller) {
        this.game = controller;
    }

    private void enterCharacter(ActionEvent e) {
        setTurnUpdate(false);
        String inputCharacter = JOptionPane.showInputDialog("Please input a character: 'X' or 'O'"); 

        PositionAwareButton clicked = (PositionAwareButton) (e.getSource());
        try {
            if (game.takeTurn(clicked.getAcross(), clicked.getDown(), inputCharacter)) {
                setTurnUpdate(true);
                clicked.setText(game.getCell(clicked.getAcross(), clicked.getDown()));
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
}
