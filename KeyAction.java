import java.awt.event.*;

/**
 * Handles keyboard input.
 */
public class KeyAction extends KeyAdapter {
    private Player playerShip;
    Game game;

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
