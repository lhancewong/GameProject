/**
	This is a template for a Java file.
	
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
        window = new JFrame();
        GameUtils.get().setJFrame(window);

        gameCanvas = new GameCanvas();
        game = gameCanvas.getGame();
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
        ka = new KeyAction(gameCanvas.p1, game);
        mainPanel.addKeyListener(ka);
    }

    public void setUpClassSelection() {
        mainPanel.addMouseListener(gameCanvas.getCSelection());
    }

    /**
     * Starts the game.
     */
    public void startGame() {
        setUpGUI();
        setUpKeyActions();
        setUpClassSelection();
        mainPanel.requestFocus();
    }
    
}
