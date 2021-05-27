import java.awt.event.*;

/**
 * Handles keyboard input.
 */
public class KeyAction extends KeyAdapter {
    private Player playerShip;

    /**
     * Initializes KeyActions.
     * 
     * @param p the player object that will move based on the keyboard inputs
     */
    public KeyAction(Player p) {
        playerShip = p;
    }

    @Override
	public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch(keyCode) {
            case KeyEvent.VK_W:
                playerShip.mUp = true;
                System.out.println("W");
                break;
            case KeyEvent.VK_A:
                playerShip.mLeft = true;
                System.out.println("A");
                break;
            case KeyEvent.VK_S:
                playerShip.mDown = true;
                System.out.println("S");
                break;
            case KeyEvent.VK_D:
                playerShip.mRight = true;
                System.out.println("D");
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
