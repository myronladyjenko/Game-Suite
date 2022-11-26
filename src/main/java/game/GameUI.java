package game;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import numericaltictactoe.NumericalTicTacToeUIView;
import tictactoe.TicTacToeUIView;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

/**
 * This class creates the window (JFrame) for accessing the games from GUI
 * 
 * @author Myron Ladyjenko
 */
public class GameUI extends JFrame {

    private final int lengthOfFrame = 650; 
    private final int widthOfFrame = 220; 

    private JPanel gamePanel;
    private String fileLocation;
    private JButton ticTacToeButton = null;
    private JButton numericalTicTacToeButton = null;

    private TicTacToeUIView ticTacToeView;
    private NumericalTicTacToeUIView numericalTicTacToeView;

    /**
     * Constructor for the main frame
     * @param gameTitle string to set the title of the frame
     */
    public GameUI(String gameTitle) {
        super(gameTitle);
        setPreferredSize(new Dimension(lengthOfFrame, widthOfFrame));
        setMinimumSize(new Dimension(lengthOfFrame, widthOfFrame));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        gamePanel = new JPanel();
        add(gamePanel, BorderLayout.CENTER);

        add(makeButtonPanel(), BorderLayout.PAGE_START);
        add(makePanelForSymmetry(), BorderLayout.PAGE_END);
        startGame();
    }

    private JPanel makePanelForSymmetry() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(40, 40));
        buttonPanel.setBackground(Color.red);
        return buttonPanel;
    }

    /**
     * It removes all the components from the gamePanel, adds a new component to it, and then
     * repaints and revalidates the gamePanel
     */
    public void startGame() {
        setTitle("TicTactoe Games");
        setPreferredSize(new Dimension(lengthOfFrame, widthOfFrame));
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
            chooseFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            chooseFile.setDialogTitle("Please choose a file name");

            userSelectedFile = chooseFile.showOpenDialog(null);
                    
            if (userSelectedFile == JFileChooser.APPROVE_OPTION) {
                File fileToLoad = chooseFile.getSelectedFile();
                setFilePath(fileToLoad.getAbsolutePath());
            }
        }
    }

    private JPanel makeButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(45, 45));

        buttonPanel.setLayout(new GridLayout(1, 2));

        buttonPanel.add(makeTicTacToeButton());
        buttonPanel.add(makeNumericalTicTacToeButton());
        buttonPanel.setBackground(Color.red);
        return buttonPanel;
    }

    private JButton makeTicTacToeButton() {
        JButton button = new JButton("Start TicTacToe game");
        button.addActionListener(e->ticTacToe());
        setTicTacToeButton(button);
        return button;
    }

    private JButton makeNumericalTicTacToeButton() {
        JButton button = new JButton("Start Numerical TicTacToe game");
        button.addActionListener(e->numericalTicTacToe());
        setNumericalTicTacToeButton(button);
        return button;
    }

    /**
     * This function is called when the user clicks on the "Tic Tac Toe" button. It creates a new
     * TicTacToeUIView object and adds it to the gamePanel
     */
    protected void ticTacToe() {
        gamePanel.removeAll();
        setPreferredSize(new Dimension(650, 650));
        ticTacToeView = new TicTacToeUIView(3,3,this);
        gamePanel.add(ticTacToeView);
        getContentPane().repaint();
        getContentPane().revalidate();
        pack();
        setLocationRelativeTo(null);

        int playerSelection = JOptionPane.showConfirmDialog(null, "Would you like to load " 
                                                            + "player's statistics profiles?", 
                                                            "Player Profile Load", JOptionPane.YES_NO_OPTION);
        if (playerSelection == 0) {
            ticTacToeView.loadUserProfile();
        } else {
            JOptionPane.showMessageDialog(null, "Player's profiles haven't been loaded");
        }
    }

    /**
     * This function creates a new NumericalTicTacToeUIView object and adds it to the gamePanel
     */
    protected void numericalTicTacToe() {
        gamePanel.removeAll();

        setPreferredSize(new Dimension(650, 650));
        numericalTicTacToeView = new NumericalTicTacToeUIView(3,3,this);
        gamePanel.add(numericalTicTacToeView);
        getContentPane().repaint();
        getContentPane().revalidate();
        pack();
        setLocationRelativeTo(null);

        int playerSelection = JOptionPane.showConfirmDialog(null, "Would you like to load " 
                                                            + "player's statistics profiles?", 
                                                            "Player Profile Load", JOptionPane.YES_NO_OPTION);
        if (playerSelection == 0) {
            numericalTicTacToeView.loadUserProfile();
        } else {
            JOptionPane.showMessageDialog(null, "Player's profiles haven't been loaded");
        }
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

    private void setTicTacToeButton(JButton button) {
        ticTacToeButton = button;
    }

    /**
     * This function returns the ticTacToeButton.
     * 
     * @return The ticTacToeButton is being returned.
     */
    public JButton getTicTacToeButton() {
        return ticTacToeButton;
    }

    private void setNumericalTicTacToeButton(JButton button) {
        numericalTicTacToeButton = button;
    }

    /**
     * This function returns the numericalTicTacToeButton.
     * 
     * @return The numericalTicTacToeButton is being returned.
     */
    public JButton getNumericalTicTacToeButton() {
        return numericalTicTacToeButton;
    }

    /**
     * The main function is the entry point of the program
     */
    public static void main(String[] args) {
        GameUI ticTacToGameUI = new GameUI("TicTactoe Games");
        ticTacToGameUI.setVisible(true);
    }
}
