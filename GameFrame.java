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
    private Game game;

    //Player stuff

    public GameFrame() {
        gameCanvas = new GameCanvas();
        window = new JFrame();
        game = gameCanvas.getGame();

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
        if (game.getPlayerNumber() == 2) {
            ka = new KeyAction(game.p2, game);
        } else {
            ka = new KeyAction(game.p1, game);
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
