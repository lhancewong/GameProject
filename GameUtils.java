/**	
	@author Wilbert Meinard L. Chen (201153)
    @author Lhance Christian S. Wong (205467)
	@version May 31, 2021
**/

/*
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
*/

import javax.swing.*;

/**
 * A class that contains global variables that multiple files might need.
 */
public class GameUtils {
    private static GameUtils gu = null;
    
    //Objects
    private JFrame frame;
    private GameCanvas gc;
    //Variables
    private int width, height;
    private boolean isMaster;

    /**
     * Contructs GameUtils
     */
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
     * Sets the GameCanvas of GameUtils.
     * 
     * @param gc the GameCanvas
     */
    public void setGameCanvas(GameCanvas gc) {
        this.gc = gc;
    }

    /**
     * Returns the GameCanvas of GameUtils
     * 
     * @return the GameCanvas
     */
    public GameCanvas getGameCanvas() {
        return gc;
    }

    /**
     * Sets the isMaster boolean of GameUtils.
     * 
     * @param isMaster the value for isMaster
     */
    public void setIsMaster(boolean isMaster) {
        this.isMaster = isMaster;
    }

    /**
     * Returns the isMaster boolean of GameUtils.
     * 
     * @return the value of isMaster 
     */
    public boolean getIsMaster() {
        return isMaster;
    }
}
