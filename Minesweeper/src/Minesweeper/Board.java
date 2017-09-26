package Minesweeper;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.util.Arrays;
import java.util.Random;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.lang.*;
import javax.swing.Timer;

public class Board extends JFrame implements ActionListener{

    private int totalMines;
    private int totalHexMines;
    private Dimension gridSize;
    private int visibleBlocks, visibleHexBlocks;
    private String[] options = {"Standard", "Hexagon", "Colored"};
    private String mode;
    private JComboBox c = new JComboBox<String>(options);
    private JButton startButton = new JButton("Start Game");
    private boolean exists = false;
    private JPanel content = new JPanel();
    private Square[][] squares;
    private Hexagon[][] hexagons;
    private Circle[] circles;
    private Timer timer = new Timer(1000,this);
    private int time;
    private static final String INCREMENT = "incr";
    private JButton[] circlesBtn;

    //Block Types - Numbers correspond to mines around them, 9 is a mine.
    public Board() {
        gridSize = new Dimension(20,20);
        setPreferredSize(new Dimension(600,600));
        setLayout(new GridLayout(2,2));
        JPanel header = new JPanel();


        totalMines = 20;
        totalHexMines = 5;
        visibleBlocks = gridSize.width * gridSize.height;
        visibleHexBlocks = 36;

        startButton.setPreferredSize(new Dimension(100,40));
        startButton.addActionListener(this);
        startButton.setActionCommand("Start");
        header.setMaximumSize(new Dimension(20,20));
        header.add(c);
        header.add(startButton);
        add(header);
        timer.setActionCommand(INCREMENT);


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setResizable(false);
    }


    public void init() {
        timer.start();
        if (mode == "Standard") {
            squares = new Square[gridSize.height][gridSize.width];
            JFrame f = new JFrame();
            f.setLayout(new GridLayout(gridSize.height,gridSize.width));
            f.setSize(new Dimension(400,500));
            f.setMinimumSize(new Dimension(400,500));
            f.setTitle("Minesweeper");



            System.out.println("Test");
            //content.setPreferredSize(new Dimension(200,200));
            for (int i = 0; i < gridSize.width; i++) {
                for (int j = 0; j < gridSize.height; j++) {
                    squares[i][j] = new Square(i, j);
                    squares[i][j].addActionListener(this);
                    f.add(squares[i][j]);
                }
            }
            exists = true;
            addMines();
            f.setVisible(true);
        } else if(mode=="Hexagon") {
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setLayout(new HexagonalLayout(7, new Insets(1, 1, 1, 1), false));
            f.setMinimumSize(new Dimension(500, 500));
            f.setTitle("Hexagonal Minesweeper");
            hexagons = new Hexagon[6][6];

            System.out.println("Does");
            Border border = new LineBorder(Color.white,13);
            for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 6; j++) {


                        hexagons[i][j] = new Hexagon(i, j);
                        hexagons[i][j].setBackground(Color.black);
                        hexagons[i][j].setBorder(border);

                        //"Random" color actionlistener, just for fun.
                        hexagons[i][j].setActionCommand("hex");
                        hexagons[i][j].addActionListener(this);
                        f.add(hexagons[i][j]);
                    }
                }
            addHexMines();
            f.setVisible(true);
        } else if(mode=="Colored"){
            System.out.println("Test123");
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //f.setLayout(new BorderLayout());
            JPanel content = new JPanel();
            content.setLayout(null);
            f.setSize(new Dimension(400,500));
            f.setLocationRelativeTo(null);
            f.setMinimumSize(new Dimension(500, 500));
            circlesBtn = new JButton[10];
            circles = new Circle[10];
            for(int i = 0; i < 10; i++){
                int x,y;
                Random generator = new Random();
                x = generator.nextInt(400);
                y = generator.nextInt(400);
                circles[i] = new Circle();

                circlesBtn[i] = new JButton();
                circlesBtn[i].addActionListener(this);
                circlesBtn[i].setPreferredSize(new Dimension(40,40));
                circlesBtn[i].setMaximumSize(new Dimension(40,40));
                circlesBtn[i].setActionCommand("circle");
                circlesBtn[i].setBounds(x,y,40,40);
                content.add(circlesBtn[i]);
            }
            for(int i = 1; i < 9; i++){
                circles[i].setConnected(circles[i-1],circles[i+1]);
            }
            f.add(content, BorderLayout.CENTER);
            f.setVisible(true);
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

    private void showBlock(Hexagon hexagon) {
        hexagon.setEnabled(false);
        System.out.println(String.valueOf(hexagon.getRow()) + "," + String.valueOf(hexagon.getColumn()));
        if (hexagon.noMines())
            showHexNeighbours(hexagon);
        else if (hexagon.isMine()) {
            showHexGameOver();
        }
        else {
            hexagon.setText(Integer.toString(hexagon.getType()));
        }

        visibleHexBlocks--;

        if (visibleHexBlocks == totalHexMines)
            showHexGameOver();
    }


    private void showMines() {
        for (Square[] rowSquares : squares) {
            for (Square square : rowSquares) {
                if (square.isMine())
                    square.setText("X");
            }
        }
    }

    private void showHexMines() {
        for (Hexagon[] rowHexagons : hexagons) {
            for (Hexagon hexagon: rowHexagons) {
                if (hexagon.isMine()) {
                    hexagon.setText("X");
                }
            }
        }
    }


    private void showGameOver() {
        timer.stop();
        if (visibleBlocks != totalMines) { //Lose
            showMines();
            JOptionPane.showMessageDialog(this,"You lose!");
        } else {
            JOptionPane.showMessageDialog(this,"You win!   Time taken: "+String.valueOf(time) + " Seconds.");
        }

        System.exit(0);
    }

    private void showHexGameOver() {
        if (visibleHexBlocks != totalHexMines) { //Lose
            showHexMines();
            JOptionPane.showMessageDialog(this,"You lose!");
        } else {
            JOptionPane.showMessageDialog(this,"You win!");
        }
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


    private void addHexMines() {
        int mineCount = 0;
        Random random = new Random();

        while (mineCount != totalHexMines) {
            int i = random.nextInt(hexagons.length);
            int j = random.nextInt(hexagons[0].length);
            if (!hexagons[i][j].isMine()) {
                hexagons[i][j].setMine();
                for (int row = i-1;row <= i+1; row++) {
                    for (int col = j-1; col <= j+1; col++) {
                        if (row == i && col == j)
                            continue; //Skip Centre
                        if (row < 0 || row > 5 ||
                                col < 0 || col > 5
                                )
                            continue; //Out of Bounds
                        if(i%2==0){
                            if(row==(i+1)&&col==(j+1)){
                                continue;
                            } else if(row==(i-1)&&col==(j+1)) {
                                continue;
                            } else {
                                if(!hexagons[row][col].isMine())
                                    hexagons[row][col].addMine();
                            }
                        } else {
                            if(row==(i-1)&&col==(j-1)){
                                continue;
                            } else if(row==(i+1)&&col==(j-1)){

                            } else {
                                if(!hexagons[row][col].isMine())
                                    hexagons[row][col].addMine();
                            }
                        }
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

    public void showHexNeighbours(Hexagon hexagon){
        int row = hexagon.getRow();
        int col = hexagon.getColumn();

        for (int i = row-1;i <= row+1; i++) {
            for (int j = col-1; j <= col+1; j++) {

                if (i == row && j == col)
                    continue; //Skip Centre
                if (i < 0 || i > 5 || j < 0 || j > 5)
                    continue; //Out of Bounds
                if(row%2==0){
                    if(i==(row+1)&&j==(col+1)){
                        continue;
                    } else if(i==(row-1)&&j==(col+1)) {
                        continue;
                    } else {
                        if(hexagons[i][j].isEnabled())
                            showBlock(hexagons[i][j]);
                    }
                } else {
                    if(i==(row-1)&&j==(col-1)){
                        continue;
                    } else if(i==(row+1)&&j==(col-1)) {
                        continue;
                    } else {
                        if(hexagons[i][j].isEnabled())
                            showBlock(hexagons[i][j]);
                    }
                }
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(INCREMENT)){
            time++;
        }
        if("Start".equals(e.getActionCommand())) {
            mode = (String) c.getSelectedItem();
            System.out.println(mode);
            init();
        } else if ("hex".equals(e.getActionCommand())) {
            showBlock((Hexagon) (e.getSource()));
        } else if ("circle".equals(e.getActionCommand())){
            System.out.println("he");
            int circleNum = Arrays.asList(circlesBtn).indexOf(e.getSource());
            circlesBtn[circleNum] = new JButton(circles[circleNum]);
            circlesBtn[circleNum].revalidate();
            circlesBtn[circleNum].repaint();
        } else {
            try {
                showBlock((Square) (e.getSource()));
            } catch (Exception exc){
                System.err.println(
                        exc.getMessage() + "Board's ActionListener only for Block objects"
                );
            }
        }
    }
}