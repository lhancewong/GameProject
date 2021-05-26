import javax.swing.*;

/**
 * A class that contains global variables that multiple files might need.
 */
public class GameUtils {
    private static GameUtils gu = null;
    
    //Objects
    private JFrame frame;
    //Variables
    private int width, height;
    private int pNum;

    private GameUtils() {
        width = 1280;
        height = 720;
    }

    /**
     * Gets the static instance of GameUtils.
     * 
     * @return the static instance of GameUtils
     */
    public static GameUtils get() {
        if (gu == null) {
            gu = new GameUtils();
        }
        return gu;
    }

    /**
     * Sets the JFrame of GameUtils.
     * 
     * @param frame 
     */
    public void setJFrame(JFrame frame) {
        this.frame = frame;
    }

    /**
     * Sets the current title of GameUtil's JFrame.
     * 
     * @param title the new title
     */
    public void setJFrameTitle(String title) {
        frame.setTitle(title);
    }

    /**
     * Sets the player number of the specific gameCanvas Instance.
     */
    public void setPlayerNum(int pNum) {
        this.pNum = pNum;
    }

    /**
     * Returns the width of the game window.
     * 
     * @return the width of the window
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the game window.
     * 
     * @return the height of the window
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the player number of the gameCanvas instance.
     * 
     * @return the player number
     */
    public int getPlayerNum() {
        return pNum;
    }
}
