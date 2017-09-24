package Minesweeper;

/**
 * Created by r-ken on 9/23/2017.
 */
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

/**
 * A layoutmanager for interleaved hexagons. This layout is based on GridLayout. It
 * divides the parent component into equal parts and resizes the subcomponents to fit
 * these. Since every other row will hold one element less than than the one before
 * (or after), this LM only register how many columns the user wants, and then calculates
 * the number of rows needed to fit this need according to the number of gui elements
 * that needs to be allocated. The number of columns given by the user is the number of
 * elements in a big row:
 *
 * Example:
 *
 * *** *** *** *** *** ***    big row
 *   *** *** *** *** ***      small row
 * *** *** *** *** *** ***    big row
 *   *** *** *** *** ***      etc.
 * *** *** *** *** *** ***
 *
 * The user can also specify wether or not to begin the layout with a small row, and can
 * specify the insets between gui elements via a Insets object.
 *
 * @author Kristian Johansen
 *
 */
public class HexagonalLayout implements LayoutManager {

    private Insets insets;
    private int rows;
    private int cols;
    private boolean beginWithSmallRow;

    private Dimension minSize;
    private Dimension prefSize;


    /**
     * Generates a HexagonalLayout with the number of columns given. The layout
     * divides the componenets into equal portions of the parent container. The
     * rows are arranged in big rows and small rows (every other). The layout
     * calculates how many rows it need to hold the number of items the parent has.
     * The number of columns represent the number of items in a long row.
     * @param cols Number of items in a big row
     */
    public HexagonalLayout(int cols) {
        checkColInput(cols);
        minSize = new Dimension(400, 300); //Standard size. Can be changed with setter.
        prefSize = new Dimension(400, 300); //Standard size. Can be changed with setter.
        insets = new Insets(0, 0, 0, 0);
        this.rows = 0;
        this.cols = cols;
        beginWithSmallRow = false;
    }

    /**
     * Generates a HexagonalLayout with the number of columns given. The layout
     * divides the componenets into equal portions of the parent container. The
     * rows are arranged in big rows and small rows (every other). The layout
     * calculates how many rows it need to hold the number of items the parent has.
     * The number of columns represent the number of items in a big row.
     *
     * This constructor has a flag for wether or not to begin with a small row
     *
     * @param cols Number of items in a big row.
     * @param beginWithSmallRow Wether or not to begin with a small row.
     */
    public HexagonalLayout(int cols, boolean beginWithSmallRow) {
        checkColInput(cols);
        minSize = new Dimension(400, 300); //Standard size. Can be changed with setter.
        prefSize = new Dimension(400, 300); //Standard size. Can be changed with setter.
        insets = new Insets(0, 0, 0, 0);
        this.rows = 0;
        this.cols = cols;
        this.beginWithSmallRow = beginWithSmallRow;
    }

    /**
     * Generates a HexagonalLayout with the number of columns given. The layout
     * divides the componenets into equal portions of the parent container. The
     * rows are arranged in big rows and small rows (every other). The layout
     * calculates how many rows it need to hold the number of items the parent has.
     * The number of columns represent the number of items in a big row.
     *
     * This constructor also takes an Insets object that specify insets between
     * elements in the gui.
     *
     * @param cols Number of coulumns in a big row.
     * @param i Insets object that specifies the spacing between gui elements.
     */
    public HexagonalLayout(int cols, Insets i) {
        checkColInput(cols);
        insets = i;
        minSize = new Dimension(400, 300); //Standard size. Can be changed with setter.
        prefSize = new Dimension(400, 300); //Standard size. Can be changed with setter.
        this.rows = 0;
        this.cols = cols;
        beginWithSmallRow = false;
    }

    /**
     * Generates a HexagonalLayout with the number of columns given. The layout
     * divides the componenets into equal portions of the parent container. The
     * rows are arranged in big rows and small rows (every other). The layout
     * calculates how many rows it need to hold the number of items the parent has.
     * The number of columns represent the number of items in a big row.
     *
     * This constructor has a flag for wether or not to start with a small row.
     *
     * This constructor also takes an Insets object that specify insets between
     * elements in the gui.
     *
     * @param cols Number of columns in a big row.
     * @param i Insets object that specify the spacing between gui elements
     * @param beginWithSmallRow Flag for wether or not to begin with a small row.
     */
    public HexagonalLayout(int cols, Insets i, boolean beginWithSmallRow) {
        checkColInput(cols);
        insets = i;
        minSize = new Dimension(400, 300); //Standard size. Can be changed with setter.
        prefSize = new Dimension(400, 300); //Standard size. Can be changed with setter.
        this.rows = 0;
        this.cols = cols;
        this.beginWithSmallRow = beginWithSmallRow;
    }

    /**
     * Checks that the column input is valid: Columns must be set to n > 0;
     * @param cols
     */
    private void checkColInput(int cols) {
        if (cols <= 0) {
            throw new IllegalArgumentException("Columns can't be set to n < 0");
        }
    }

    /**
     * Calculates the numbers of rows needed for the components given the
     * number of columns given.
     * @param componentCount
     * @return
     */
    private int calculateRows(int componentCount) {

        boolean smallRow = beginWithSmallRow;

        int numberOfRows = 0;
        int bgRow = cols;
        int smRow = bgRow - 1;

        int placedItems = 0;
        if (smallRow) {
            while (true) {
                if (placedItems >= componentCount) {
                    break;
                }
                placedItems += smRow;
                numberOfRows += 1;
                if (placedItems >= componentCount) {
                    break;
                }
                placedItems += bgRow;
                numberOfRows += 1;
            }
        } else {
            while (true) {
                if (placedItems >= componentCount) {
                    break;
                }
                placedItems += bgRow;
                numberOfRows += 1;
                if (placedItems >= componentCount) {
                    break;
                }
                placedItems += smRow;
                numberOfRows += 1;
            }

        }
        System.out.println(numberOfRows);
        return numberOfRows;
    }

    /*
     * (non-Javadoc)
     * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
     */
    @Override
    public void layoutContainer(Container parent) {

        // Get componentCount and check that it is not 0
        int componentCount = parent.getComponentCount();
        if (componentCount == 0) {
            return;
        }

        // This indicates wither or not to begin with a small row
        boolean smallRow = beginWithSmallRow;

        // Calculating the number of rows needed
        rows = calculateRows(componentCount);

        // insets
        int leftOffset = insets.left;
        int rightOffset = insets.right;
        int topOffset = insets.top;
        int bottomOffset = insets.bottom;

        // spacing dimensions
        int boxWidth = parent.getWidth() / cols+1;
        int boxHeight = (int) Math
                .round((parent.getHeight() / (1 + (0.75 * (rows - 1)))))-10;
        double heightRatio = 0.75;

        // component dimensions
        int cWidth = (boxWidth - (leftOffset + rightOffset));
        int cHeight = (boxHeight - (topOffset + bottomOffset));


        int x;
        int y;
        if(smallRow){
            x = (int)Math.round((boxWidth / 2.0));
        }else{
            x = 0;
        }
        y = 0;

        // Laying out each of the components in the container
        for (Component c : parent.getComponents()) {
            if (x > parent.getWidth() - boxWidth) {
                smallRow = !smallRow;
                if (smallRow) {
                    x = (int)Math.round(boxWidth / 2.0);
                    y += Math.round(boxHeight * heightRatio);
                } else {
                    x = 0;
                    y += Math.round(boxHeight * heightRatio);
                }

            }
            c.setBounds(x + leftOffset, y + topOffset, cWidth, cHeight);
            x += boxWidth;
        }

    }

    /*
     * (non-Javadoc)
     * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
     */
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return minSize;
    }

    /*
     * (non-Javadoc)
     * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
     */
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return prefSize;
    }

    /*
     * (non-Javadoc)
     * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
     */
    @Override
    public void removeLayoutComponent(Component comp) {
        // NOT IMPLEMENTED

    }
    /*
     * (non-Javadoc)
     * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String, java.awt.Component)
     */
    @Override
    public void addLayoutComponent(String name, Component comp) {
        // NOT IMPLEMENTED

    }

    public Insets getInsets() {
        return insets;
    }

    public void setInsets(Insets insets) {
        this.insets = insets;
    }

    public int getColumns() {
        return cols;
    }

    public void setColumns(int cols) {
        this.cols = cols;
    }

    public boolean isBeginWithSmallRow() {
        return beginWithSmallRow;
    }

    public void setBeginWithSmallRow(boolean beginWithSmallRow) {
        this.beginWithSmallRow = beginWithSmallRow;
    }

    public Dimension getMinimumSize() {
        return minSize;
    }

    public void setMinimumSize(Dimension minSize) {
        this.minSize = minSize;
    }

    public Dimension getPreferredSize() {
        return prefSize;
    }

    public void setPrefferedSize(Dimension prefSize) {
        this.prefSize = prefSize;
    }

    public int getRows() {
        return rows;
    }

}
