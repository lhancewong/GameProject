import javax.swing.*;

public class GameUtils {
    private static GameUtils gu = null;
    
    //Objects
    private JFrame frame;
    
    private GameUtils() {}

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
}
