package Minesweeper;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.*;

/**
 * Created by r-ken on 9/25/2017.
 */
public class Circle implements Icon{


    String colour;
    private int count, num;
    private Circle[] connected = new Circle[2];
    boolean activated;
    String color;
    public Circle(){
        Random generator = new Random();
        num = generator.nextInt(4);
        switch(num){
            case 0:
                color="Blue";
                break;
            case 1:
                color="Red";
                break;
            case 2:
                color="Black";
                break;
            case 3:
                color="Yellow";
                break;
            default:
                color="Null";
                break;
        }
    }


    @Override
    public void paintIcon(Component c, Graphics g, int x, int y){
        Graphics2D g2 = (Graphics2D) g.create();
        Random generator = new Random();
        int diameter = 30;
        x = generator.nextInt(400);
        y = generator.nextInt(400);
        g2.setColor(Color.BLACK);
        g2.drawOval(5,5,getIconWidth(),getIconHeight());
        g2.fillOval(5,5,getIconWidth(),getIconHeight());
        g2.dispose();
    }
    @Override
    public int getIconWidth(){
        return 30;
    }
    @Override
    public int getIconHeight(){
        return 30;
    }
    public void setConnected(Circle connected, Circle connecto){
        this.connected[0] = connected;
        this.connected[1] = connecto;
    }
    
}
