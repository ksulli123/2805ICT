package Minesweeper;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel implements ActionListener{

    private int totalMines;
    private Minesweeper.Square[][] squares;
    private Dimension gridSize;
    private int visibleBlocks;

    //Block Types - Numbers correspond to mines around them, 9 is a mine.
    public Board() {
        gridSize = new Dimension(20,20);
        //setPreferredSize(new Dimension(800,600));
        setLayout(new GridLayout(gridSize.height,gridSize.width));

        totalMines = 20;
        visibleBlocks = gridSize.width * gridSize.height;

        squares = new Square[gridSize.height][gridSize.width];
        for (int i = 0; i < gridSize.width; i++) {
            for (int j = 0; j < gridSize.height; j++) {
                squares[i][j] = new Square(i,j);
                squares[i][j].addActionListener(this);
                add(squares[i][j]);
            }
        }

        addMines();

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            showBlock((Square)(e.getSource()));
        } catch(Exception exc) {
            System.err.println(
                    exc.getMessage()+"Board's ActionListener only for Block objects"
            );
        }
    }

    private void showBlock(Square square) {
        square.setEnabled(false);

        if (square.noMines())
            showNeighbours(square);
        else if (square.isMine())
            showGameOver();
        else
            square.setText(Integer.toString(square.getType()));

        visibleBlocks--;

        if (visibleBlocks == totalMines)
            showGameOver();
    }

    private void showMines() {
        for (Square[] rowSquares : squares) {
            for (Square square : rowSquares) {
                if (square.isMine())
                    square.setText("X");
            }
        }
    }

    private void showGameOver() {
        if (visibleBlocks != totalMines) { //Lose
            showMines();
            JOptionPane.showMessageDialog(this,"You lose!");
        } else {
            JOptionPane.showMessageDialog(this,"You win!");
        }

        System.exit(0);
    }

    private void addMines() {
        int mineCount = 0;
        Random random = new Random();

        while (mineCount != totalMines) {
            int i = random.nextInt(squares.length);
            int j = random.nextInt(squares[0].length);
            if (!squares[i][j].isMine()) {
                squares[i][j].setMine();
                for (int row = i-1;row <= i+1; row++) {
                    for (int col = j-1; col <= j+1; col++) {
                        if (row == i && col == j)
                            continue; //Skip Centre
                        if (row < 0 || row > gridSize.height-1 ||
                                col < 0 || col > gridSize.width-1
                                )
                            continue; //Out of Bounds
                        if(!squares[row][col].isMine())
                            squares[row][col].addMine();
                    }
                }

                mineCount++;
            }
        }
    }

    public void showNeighbours(Square square) {
        int row = square.getRow();
        int col = square.getColumn();

        for (int i = row-1;i <= row+1; i++) {
            for (int j = col-1; j <= col+1; j++) {
                if (i == row && j == col)
                    continue; //Skip Centre
                if (i < 0 || i > gridSize.height-1 || j < 0 || j > gridSize.width-1)
                    continue; //Out of Bounds
                if(squares[i][j].isEnabled())
                    showBlock(squares[i][j]);
            }
        }
    }
}