import javax.swing.*;
import java.awt.*;

/**
 *  This class contains the code that sets up the main JFrame for the player.
 */
public class GameFrame {
    //GUI stuff
    private Container mainPanel;
    private JFrame window;

    //game stuff
    private GameCanvas gameCanvas;

    //Player stuff
    private Player p1;
    private Player p2;

    public GameFrame() {
        gameCanvas = new GameCanvas();

    }

    /**
     * Sets up the GUI.
     */
    private void setUpGUI() {
        mainPanel = window.getContentPane();
        window.setTitle("Shoot and Scoot");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setFocusable(true);

        mainPanel.add(gameCanvas, BorderLayout.CENTER);

        window.pack();
    }

    /**
     * Passes p1 and p2 to the KeyAction class.
     */
    private void setUpKeyActions() {
        KeyAction ka;
        if (gameCanvas.getPlayerNumber() == 1) {
            ka = new KeyAction(p1);
        } else {
            ka = new KeyAction(p2);
        }
        mainPanel.addKeyListener(ka);
    }

    /**
     * Starts the game.
     */
    public void startGame() {
        setUpGUI();
        setUpKeyActions();

    }
    
}
