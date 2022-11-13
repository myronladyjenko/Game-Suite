package boardgame.ui;

/*
 * Class to provide a GUI button component that knows what its position is in a grid.
 */
public class PositionAwareButton extends javax.swing.JButton {
    private int yPos;
    private int xPos;

    /** 
     * Calling the constructor of the superclass.
     */
    public PositionAwareButton() {
        super();
    }

    /** 
     * Calling the constructor of the superclass.
     */
    public PositionAwareButton(String val) {
        super(val);
    }

    /**
     * This function returns the x position of the object.
     * 
     * @return The xPos variable is being returned.
     */
    public int getAcross() {
        return xPos;
    }

    public int getDown() {
        return yPos;
    }

    /**
     * This function sets the xPos variable to the value of the val parameter.
     * 
     * @param val The value to set the parameter to.
     */
    public void setAcross(int val) {
        xPos = val;
    }

    /**
     * This function sets the yPos variable to the value of the val parameter.
     * 
     * @param val The value to set the parameter to.
     */
    public void setDown(int val) {
        yPos = val;
    }
}