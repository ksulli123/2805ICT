package Minesweeper;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.*;

/**
 * Created by r-ken on 9/25/2017.
 */
public class Circle extends JButton{
    String colour;
    private int count, num;
    Circle connected;
    boolean activated;
    public Circle(){
        Random randColour = new Random();
        num = randColour.nextInt(4);
    }

    @Override
    protected void paintComponent(Graphics g){
        Random generator = new Random();
        int x, y;
        int diameter = 30;
        x = generator.nextInt(30);
        y = generator.nextInt(30);
        g.drawOval(x,y,diameter,diameter);
    }
    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (contains(e.getPoint()))
            super.processMouseEvent(e);
    }
}
