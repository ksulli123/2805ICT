package Minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.*;

/**
 * Simple hexagonal button class.
 *
 * @author Kristian Johansen
 *
 */
public class Hexagon extends JButton {

    /**
     * autoGenerated serial-version.
     */
    private static final long serialVersionUID = -7142502695252118612L;
    Polygon hexagonalShape;
    int row, col;
    int type;

    public Hexagon(int row, int col) {
        this.setOpaque(false);
        this.row = row;
        this.col = col;
        this.type = 0;
        hexagonalShape = getHexPolygon();
    }

    /**
     * Generates the buttons Hexagonal shape
     *
     * @return Polygon with the buttons hexagonal shape.
     */
    private Polygon getHexPolygon() {
        Polygon hex = new Polygon();
        int w = 60;
        int h = 60;
        int ratio = (int) (h * .25);



        hex.addPoint(w / 2, 0);
        hex.addPoint(w, ratio);
        hex.addPoint(w, h - ratio);
        hex.addPoint(w / 2, h);
        hex.addPoint(0, h - ratio);
        hex.addPoint(0, ratio);

        return hex;
    }

    /*
     * (non-Javadoc)
     * @see java.awt.Component#contains(java.awt.Point)
     */
    @Override
    public boolean contains(Point p) {
        return hexagonalShape.contains(p);
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.JComponent#contains(int, int)
     */
    @Override
    public boolean contains(int x, int y) {
        return hexagonalShape.contains(x, y);
    }

    /*
     * (non-Javadoc)
     * @see java.awt.Component#setSize(java.awt.Dimension)
     */
    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        hexagonalShape = getHexPolygon();
    }

    /*
     * (non-Javadoc)
     * @see java.awt.Component#setSize(int, int)
     */
    @Override
    public void setSize(int w, int h) {
        super.setSize(w, h);
        hexagonalShape = getHexPolygon();
    }

    @Override
    public void setText(String text){
        super.setText(text);
        super.repaint();
    }

    /*
     * (non-Javadoc)
     * @see java.awt.Component#setBounds(int, int, int, int)
     */
    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        hexagonalShape = getHexPolygon();
    }

    /*
     * (non-Javadoc)
     * @see java.awt.Component#setBounds(java.awt.Rectangle)
     */
    @Override
    public void setBounds(Rectangle r) {
        super.setBounds(r);
        hexagonalShape = getHexPolygon();
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.JComponent#processMouseEvent(java.awt.event.MouseEvent)
     */
    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (contains(e.getPoint()))
            super.processMouseEvent(e);
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.0f));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.DARK_GRAY);
        g.drawPolygon(hexagonalShape);
        g.setColor(getBackground());
        g.fillPolygon(hexagonalShape);

            g.setColor(Color.WHITE);
            String rowcol = String.valueOf(row) + "," + String.valueOf(col) + "," + String.valueOf(type);
            g.drawString(String.valueOf(rowcol), 20,20);



    }
    @Override
    public void setEnabled(boolean b){
        if(b){
            setBackground(Color.black);
        } else {
            setBackground(null);
        }
        super.setEnabled(b);
    }




    /*
     * (non-Javadoc)
     * @see javax.swing.AbstractButton#paintBorder(java.awt.Graphics)
     */
    @Override
    protected void paintBorder(Graphics g) {
        // Does not print border
    }

    public void setMine() { type = 9; }

    public boolean isMine() { return type == 9 ? true : false; }

    public boolean noMines() { return type == 0 ? true : false; }

    public void addMine() { type++; }

    public int getRow() { return row; }
    public int getColumn() { return col; }

    public int getType() { return type; }

}