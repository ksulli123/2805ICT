package Minesweeper;

import javax.swing.JFrame;

public class Main extends JFrame {

    public Main() {
        super("Minesweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new Minesweeper.Board());
        pack();
        setVisible(true);
    }
    public static void main(String[] args) {
        new Main();
    }
}