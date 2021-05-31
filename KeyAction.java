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

import java.awt.event.*;

/**
 * Handles keyboard input.
 */
public class KeyAction extends KeyAdapter {
    private Player playerShip;
    private Game game;

    /**
     * Initializes KeyActions.
     * 
     * @param p the player object that will move based on the keyboard inputs
     */
    public KeyAction(Player p, Game game) {
        playerShip = p;
        this.game = game;
    }

    @Override
	public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        /**
         * W to move up
         * A to move left
         * S to move down
         * D to move right
         * 
         * Space to shoot a bullet
         */
        switch(keyCode) {
            case KeyEvent.VK_W:
                playerShip.mUp = true;
                break;
            case KeyEvent.VK_A:
                playerShip.mLeft = true;
                break;
            case KeyEvent.VK_S:
                playerShip.mDown = true;
                break;
            case KeyEvent.VK_D:
                playerShip.mRight = true;
                break;
            case KeyEvent.VK_SPACE:
                game.getController().addBullet();
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        /**
         * Release W to stop moving up
         * Release A to stop moving up
         * Release S to stop moving up
         * Release D to stop moving up
         */
        switch(keyCode) {
            case KeyEvent.VK_W:
                playerShip.mUp = false;
                break;
            case KeyEvent.VK_A:
                playerShip.mLeft = false;
                break;
            case KeyEvent.VK_S:
                playerShip.mDown = false;
                break;
            case KeyEvent.VK_D:
                playerShip.mRight = false;
                break;
        }
        
    }
}
