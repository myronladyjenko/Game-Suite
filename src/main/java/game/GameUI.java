package game;

import java.util.Scanner;

public class GameUI {
    private Scanner scanner;

    public GameUI() {
        scanner = new Scanner(System.in);
    }

    private void playGame() {
        System.out.println("In GameUI");
    }

    public static void main(String[] args) {
        GameUI textGame = new GameUI();
        textGame.playGame();
    }
}
