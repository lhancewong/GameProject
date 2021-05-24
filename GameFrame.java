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

    public GameFrame() {
        gameCanvas = new GameCanvas();
        window = new JFrame();

        GameUtils.get().setJFrame(window);
    }

    /**
     * Sets up the GUI.
     */
    public void setUpGUI() {
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
    public void setUpKeyActions() {
        KeyAction ka;
        if (gameCanvas.getPlayerNumber() == 2) {
            ka = new KeyAction(gameCanvas.p2, gameCanvas);
        } else {
            ka = new KeyAction(gameCanvas.p1, gameCanvas);
        }
        mainPanel.addKeyListener(ka);
    }

    /**
     * Starts the game.
     */
    public void startGame() {
        setUpGUI();
        setUpKeyActions();
        mainPanel.requestFocus();
    }
    
}
