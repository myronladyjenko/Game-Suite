package game;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import numericaltictactoe.NumericalTicTacToeUIView;
import tictactoe.TicTacToeUIView;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

/**
 * This class creates the window for playing the games from GUI
 * 
 * @author Myron Ladyjenko
 */
public class GameUI extends JFrame {

    private final int lengthOfFrame = 650; 
    private final int widthOfFrame = 220; 

    private JPanel gamePanel;
    private String fileLocation;
    private JButton buttonToSave;
    private JButton buttonToLoad;

    private Player playerOne;
    private Player playerTwo;
    private TicTacToeUIView ticTacToeView;

    // The constructor of the class. It is called when an object of the class is created.
    public GameUI(String gameTitle) {
        super(gameTitle);
        setPreferredSize(new Dimension(lengthOfFrame, widthOfFrame));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        playerOne = new Player("Player One");
        playerTwo = new Player("Player Two");
        
        gamePanel = new JPanel();
        add(gamePanel, BorderLayout.CENTER);

        add(makeButtonPanel(), BorderLayout.PAGE_START);
        add(makePanelForSavingLoadingUserProfile(), BorderLayout.PAGE_END);
        startGame();
    }

    private JPanel makePanelForSavingLoadingUserProfile() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(40, 40));
        buttonToSave = new JButton("Save User Profile");
        buttonToLoad = new JButton("Load User Profile");

        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonToSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveUserProfile();
            }
        });
        buttonToLoad.addActionListener(new ActionListener() {
        
            @Override
            public void actionPerformed(ActionEvent e) {
                loadUserProfile();
            }
        });

        buttonToSave.setEnabled(false);
        buttonPanel.add(buttonToSave);
        buttonPanel.add(buttonToLoad);
        buttonPanel.setBackground(Color.red);

        return buttonPanel;
    }

    private void saveUserProfile() {
        selectLocationOfTheFile(1);

        if (ticTacToeView.getPlayerWhoWon() == 1) {
            playerOne.setWins(playerOne.getWins() + 1);
            playerTwo.setLosses(playerOne.getLosses() + 1);
        } else if (ticTacToeView.getPlayerWhoWon() == 2) {
            playerTwo.setWins(playerOne.getWins() + 1);
            playerOne.setLosses(playerOne.getLosses() + 1);
        } else if (ticTacToeView.getPlayerWhoWon() == 0) {
            playerOne.setTies(playerOne.getTies() + 1);
            playerTwo.setTies(playerTwo.getTies() + 1);
        }

        if (ticTacToeView.getPlayerWhoWon() != -1) {
            playerOne.setTotalGames(playerOne.getTotalGames() + 1);
            playerTwo.setTotalGames(playerTwo.getTotalGames() + 1);
        }

        ticTacToeView.setPlayerWhoWon(-1);
        try {
            FileHandling.saveToFile(getFilePath(), playerOne);
            FileHandling.saveToFile(getFilePath(), playerTwo);
        } catch (ThrowExceptionFileActionHasFailed e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void loadUserProfile() {
        selectLocationOfTheFile(0);
        try {
            FileHandling.loadFile(getFilePath(), playerOne);
            FileHandling.loadFile(getFilePath(), playerTwo);
        } catch (ThrowExceptionFileActionHasFailed e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * It removes all the components from the gamePanel, adds a new component to it, and then
     * repaints and revalidates the gamePanel
     */
    public void startGame() {
        setPreferredSize(new Dimension(lengthOfFrame, widthOfFrame));
        buttonToSave.setVisible(true);
        buttonToLoad.setVisible(true);
        gamePanel.removeAll();
        gamePanel.add(startupMessage());
        getContentPane().repaint();
        getContentPane().revalidate();
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * This function is used to select a file from the user's computer
     * 
     * @param choice 1 for saving a file, 2 for opening a file
     */
    public void selectLocationOfTheFile(int choice) {
        int userSelectedFile = 0;
        JFileChooser chooseFile = new JFileChooser();
        if (choice == 1) {
            chooseFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            chooseFile.setDialogTitle("Please enter a file name");
            userSelectedFile = chooseFile.showSaveDialog(null);

            if (userSelectedFile == JFileChooser.APPROVE_OPTION) {
                File fileToSave = chooseFile.getSelectedFile();
                setFilePath(fileToSave.getAbsolutePath());
            }
        } else if (choice == 0) {
            System.setProperty("swing.disableFileChooserSpeedFix", "true");
            chooseFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            chooseFile.setDialogTitle("Please choose a file name");
            chooseFile.setApproveButtonMnemonic('y');

            userSelectedFile = chooseFile.showOpenDialog(buttonToLoad);
                    
            if (userSelectedFile == JFileChooser.APPROVE_OPTION) {
                File fileToLoad = chooseFile.getSelectedFile();
                setFilePath(fileToLoad.getAbsolutePath());
                System.out.println(getFilePath());
            }
        }
    }

    /*
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    chooseFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                    chooseFile.setDialogTitle("Please choose a file name");
                    int userSelectedFile = chooseFile.showOpenDialog(null);
                    
                    if (userSelectedFile == JFileChooser.APPROVE_OPTION) {
                        File fileToLoad = chooseFile.getSelectedFile();
                        setFilePath(fileToLoad.getAbsolutePath());
                        System.out.println(getFilePath());
                    }
                }  
            }); */

    private JPanel makeButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(40, 40));

        buttonPanel.setLayout(new GridLayout(1, 2));

        buttonPanel.add(makeTicTacToeButton());
        buttonPanel.add(makeNumericalTicTacToeButton());
        buttonPanel.setBackground(Color.red);
        return buttonPanel;
    }

    private JButton makeTicTacToeButton() {
        JButton button = new JButton("Start TicTacToe game");
        button.addActionListener(e->ticTacToe());
        return button;
    }

    private JButton makeNumericalTicTacToeButton() {
        JButton button = new JButton("Start Numerical TicTacToe game");
        button.addActionListener(e->numericalTicTacToe());
        return button;
    }

    protected void ticTacToe() {
        buttonToSave.setVisible(false);
        buttonToLoad.setVisible(false);
        gamePanel.removeAll();
        setPreferredSize(new Dimension(650, 650));
        ticTacToeView = new TicTacToeUIView(3,3,this);
        // buttonToSave.setEnabled(true);
        gamePanel.add(ticTacToeView);
        getContentPane().repaint();
        getContentPane().revalidate();
        pack();
        setLocationRelativeTo(null);
    }

    protected void numericalTicTacToe() {
        gamePanel.removeAll();
        gamePanel.add(new NumericalTicTacToeUIView(3, 3, this));
        setPreferredSize(new Dimension(650, 650));

        
        getContentPane().repaint();
        getContentPane().revalidate();
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel startupMessage() {
        JPanel openMessage = new JPanel();
        openMessage.setLayout(new BorderLayout());

        JLabel startText = new JLabel(
            "<html>Welcome to the Myron's Application. It's time to have some fun!</html>");
        startText.setPreferredSize(new Dimension(lengthOfFrame - 20, widthOfFrame - 120));
        startText.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        openMessage.add(startText, BorderLayout.CENTER);

        return openMessage;
    }

    private void setFilePath(String filePath) {
        fileLocation = filePath;
    }

    /**
     * This function returns the file path of the file that is being read
     * 
     * @return The file path of the file.
     */
    public String getFilePath() {
        return fileLocation;
    }

    /**
     * The main function is the entry point of the program
     */
    public static void main(String[] args) {
        GameUI ticTacToGameUI = new GameUI("TicTactoe Games");
        ticTacToGameUI.setVisible(true);
    }
}
