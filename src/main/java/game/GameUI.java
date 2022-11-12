package game;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import tictactoe.TicTacToeUIView;

import java.awt.BorderLayout;
import java.awt.Container;

public class GameUI extends JFrame {

    private final int widthOfFrame = 600;
    private final int lengthOfFrame = 500; 

    private JPanel gameContainer;
    private JLabel messageLabel;
    private JMenuBar menuBar;

    public GameUI(String gameTitle) {
        super(gameTitle);
        this.setSize(widthOfFrame, lengthOfFrame);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        makeMenu();
        //setJMenuBar(menuBar);
        //gameContainer = new JPanel();

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JLabel("Border Layout on contentPane"));

        //setLayout(new BorderLayout());

        // make a new label to store messages
        
        //add(gameContainer, BorderLayout.CENTER);
        //add(makeButtonPanel(),BorderLayout.EAST);
        //start();

    }

    private JPanel makeButtonPanel(){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(makeKakuroButton());
        buttonPanel.add(makeSecondGameButton());
        return buttonPanel;
    }

    private JButton makeKakuroButton(){
        JButton button = new JButton("PlayKakuro");
        button.addActionListener(e->kakuro());
        return button;
    }

    private JButton makeSecondGameButton(){
        JButton button = new JButton("Play Other Game");
        button.addActionListener(e->secondGame());
        return button;
    }

    protected void kakuro() {
        gameContainer.removeAll();
        gameContainer.add(new TicTacToeUIView(3,3,this));
        getContentPane().repaint();
        getContentPane().revalidate();
        pack();
    }

    protected void secondGame() {
        gameContainer.removeAll();
        gameContainer.add(startupMessage());
        getContentPane().repaint();
        getContentPane().revalidate();
        pack();
        JOptionPane.showMessageDialog(null,"Judi didn't make a second game"); 
    }

    public void makeMenu() {
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("A submenu");
        JMenuItem item = new JMenuItem("an item (e.g. save)");
        menu.add(item);
        menuBar.add(menu);
        item.addActionListener(e->saveSomething());
    }

    protected void saveSomething() {
        JOptionPane.showMessageDialog(null,"This should prompt for save files"); 
    }

    public void start() {
        gameContainer.removeAll();
        gameContainer.add(startupMessage());
        getContentPane().repaint();
        getContentPane().revalidate();
        pack();
    }

    private JPanel startupMessage(){
        JPanel temp = new JPanel();
        temp.add(new JLabel("Welcome to the Myron's Application.\nIt's time to have some fun!"));
        return temp;
    }

    public static void main(String[] args) {
        GameUI ticTacToGameUI = new GameUI("TicTactoe Games");
        ticTacToGameUI.setVisible(true);
    }
}
