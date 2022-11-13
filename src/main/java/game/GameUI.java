package game;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import numericaltictactoe.NumericalTicTacToeUIView;
import tictactoe.TicTacToeUIView;

import java.awt.BorderLayout;
import java.io.File;

/**
 * This class creates the window for playing the games from GUI
 * 
 * @author Myron Ladyjenko
 */
public class GameUI extends JFrame {

    private final int widthOfFrame = 600;
    private final int lengthOfFrame = 500; 

    private JFileChooser chooseFile;
    private JPanel gameContainer;
    private JMenuBar menuBar;
    private String fileLocation;
    private JPanel buttonPanel;
    private JMenu menuForLoadingGames;
    private JMenu menuForSavingGames;

    // The constructor of the class. It is called when an object of the class is created.
    public GameUI(String gameTitle) {
        super(gameTitle);
        this.setSize(widthOfFrame, lengthOfFrame);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        makeMenu();
        setJMenuBar(menuBar);
        gameContainer = new JPanel();
        setLayout(new BorderLayout());
        
        this.add(gameContainer, BorderLayout.CENTER);
        this.add(makeButtonPanel(),BorderLayout.EAST);
        startGame();
    }

    /**
     * It removes all the components from the gameContainer, adds a new component to it, and then
     * repaints and revalidates the gameContainer
     */
    public void startGame() {
        gameContainer.removeAll();
        gameContainer.add(startupMessage());
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
    }

    /**
     * This function is used to select a file from the user's computer
     * 
     * @param choice 1 for saving a file, 2 for opening a file
     */
    public void selectLocationOfTheFile(int choice) {
        chooseFile = new JFileChooser();
        int userSelectedFile = 0;

        if (choice == 1) {
            chooseFile.setDialogTitle("Please enter a file name");
            userSelectedFile = chooseFile.showSaveDialog(this);

            if (userSelectedFile == JFileChooser.APPROVE_OPTION) {
                File fileToSave = chooseFile.getSelectedFile();
                setFilePath(fileToSave.getAbsolutePath());
            }
        } else {
            chooseFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
            userSelectedFile = chooseFile.showOpenDialog(this);
            System.out.println(chooseFile.getSelectedFile().getAbsolutePath());
            setFilePath(chooseFile.getSelectedFile().getAbsolutePath());
        }
    }

    private JPanel makeButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(makeTicTacToeButton());
        buttonPanel.add(makeNumericalTicTacToeButton());
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
        gameContainer.removeAll();
        gameContainer.add(new TicTacToeUIView(3,3,this));
        getContentPane().repaint();
        getContentPane().revalidate();
        pack();
    }

    protected void numericalTicTacToe() {
        gameContainer.removeAll();
        gameContainer.add(new NumericalTicTacToeUIView(3, 3, this));
        getContentPane().repaint();
        getContentPane().revalidate();
        pack();
    }

    private JPanel startupMessage() {
        JPanel temp = new JPanel();
        temp.add(new JLabel("Welcome to the Myron's Application.\nIt's time to have some fun!"));
        return temp;
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
