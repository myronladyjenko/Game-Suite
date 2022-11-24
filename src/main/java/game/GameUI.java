package game;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import numericaltictactoe.NumericalTicTacToeUIView;
import tictactoe.TicTacToeUIView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;

/**
 * This class creates the window for playing the games from GUI
 * 
 * @author Myron Ladyjenko
 */
public class GameUI extends JFrame {

    private final int widthOfFrame = 600;
    private final int lengthOfFrame = 500; 

    private JPanel gamePanel;
    private JMenuBar menuBar;
    private String fileLocation;
    private JPanel buttonPanel;
    private JMenu menuForLoadingGames;
    private JMenu menuForSavingGames;
    private JButton buttonToSave;
    private JButton buttonToLoad;

    private Player playerOne;
    private Player playerTwo;
    private TicTacToeUIView ticTacToeView;

    // The constructor of the class. It is called when an object of the class is created.
    public GameUI(String gameTitle) {
        super(gameTitle);
        this.setSize(widthOfFrame, lengthOfFrame);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        playerOne = new Player("PlayerOne");
        playerTwo = new Player("PlayerTwo");

        makeMenu();
        setJMenuBar(menuBar);
        gamePanel = new JPanel();
        setLayout(new BorderLayout());
        
        this.add(gamePanel, BorderLayout.CENTER);
        this.add(makeButtonPanel(), BorderLayout.PAGE_START);
        this.add(makePanelForSavingLoadingProfile(), BorderLayout.PAGE_END);

        startGame();
    }

    private JPanel makePanelForSavingLoadingProfile() {
        buttonPanel = new JPanel();
        buttonToSave = new JButton("Save User Profile");
        buttonToLoad = new JButton("Load User Profile");

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
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
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
        buttonToSave.setVisible(true);
        buttonToLoad.setVisible(true);
        menuBar.setVisible(true);
        gamePanel.removeAll();
        gamePanel.add(startupMessage());
        getContentPane().repaint();
        getContentPane().revalidate();
        pack();
    }

    /**
     * It creates a menu bar with two menus, one for saving games and one for loading games
     */
    public void makeMenu() {
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("File Actions");
        menuForSavingGames = new JMenu("Save game");
        menuForLoadingGames = new JMenu("Load game");

        JMenuItem itemTicaTacToeGameSave = new JMenuItem("TicTacToe");
        JMenuItem itemNumericalTicTacToeGameSave = new JMenuItem("Numerical TicTacToe");
        JMenuItem itemTicaTacToeGameLoad = new JMenuItem("TicTacToe");
        JMenuItem itemNumericalTicTacToeGameLoad = new JMenuItem("Numerical TicTacToe");

        menuForLoadingGames.add(itemNumericalTicTacToeGameLoad);
        menuForLoadingGames.add(itemTicaTacToeGameLoad);
        menuForSavingGames.add(itemNumericalTicTacToeGameSave);
        menuForSavingGames.add(itemTicaTacToeGameSave);

        menu.add(menuForLoadingGames);
        menu.add(menuForSavingGames);
        menuBar.add(menu);
        menuBar.setVisible(false);
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
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(makeTicTacToeButton());
        buttonPanel.add(makeNumericalTicTacToeButton());
        buttonPanel.setBackground(Color.red);
        return buttonPanel;
    }

    private JButton makeTicTacToeButton() {
        JButton button = new JButton("Start new TicTacToe game");
        button.addActionListener(e->ticTacToe());
        return button;
    }

    private JButton makeNumericalTicTacToeButton() {
        JButton button = new JButton("Start new Numerical TicTacToe");
        button.addActionListener(e->numericalTicTacToe());
        return button;
    }

    protected void ticTacToe() {
        buttonToSave.setVisible(false);
        buttonToLoad.setVisible(false);
        gamePanel.removeAll();
        ticTacToeView = new TicTacToeUIView(3,3,this);
        buttonToSave.setEnabled(true);
        gamePanel.add(ticTacToeView);
        getContentPane().repaint();
        getContentPane().revalidate();
        pack();
    }

    protected void numericalTicTacToe() {
        gamePanel.removeAll();
        gamePanel.add(new NumericalTicTacToeUIView(3, 3, this));
        getContentPane().repaint();
        getContentPane().revalidate();
        pack();
    }

    private JPanel startupMessage() {
        JPanel openMessage = new JPanel();
        openMessage.add(new JLabel("Welcome to the Myron's Application.\nIt's time to have some fun!"));

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
     * This function returns the JMenuItem at the specified position in the menuForSavingGames JMenu.
     * 
     * @param position The position of the menu item in the menu.
     * @return The JMenuItem at the specified position.
     */
    public JMenuItem getJMenuItemForSave(int position) {
        return menuForSavingGames.getItem(position);
    }

    /**
     * This function returns the JMenuItem at the specified position in the menuForLoadingGames JMenu.
     * 
     * @param position The position of the menu item in the menu.
     * @return The JMenuItem at the specified position.
     */
    public JMenuItem getJMenuItemForLoad(int position) {
        return menuForLoadingGames.getItem(position);
    }


    /**
     * The main function is the entry point of the program
     */
    public static void main(String[] args) {
        GameUI ticTacToGameUI = new GameUI("TicTactoe Games");
        ticTacToGameUI.setVisible(true);
    }
}
