import java.awt.event.*;

/**
 * Handles keyboard input.
 */
public class KeyAction extends KeyAdapter {
    private Player playerShip;

    public KeyAction(Player p) {
        playerShip = p;
    }

    @Override
	public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch(keyCode) {
            case KeyEvent.VK_W:
                playerShip.movingUp = true;
                break;
            case KeyEvent.VK_A:
                playerShip.movingLeft = true;
                break;
            case KeyEvent.VK_S:
                playerShip.movingDown = true;
                break;
            case KeyEvent.VK_D:
                playerShip.movingRight = true;
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch(keyCode) {
            case KeyEvent.VK_W:
                playerShip.movingUp = false;
                break;
            case KeyEvent.VK_A:
                playerShip.movingLeft = false;
                break;
            case KeyEvent.VK_S:
                playerShip.movingDown = false;
                break;
            case KeyEvent.VK_D:
                playerShip.movingRight = false;
                break;
        }
        
    }
}
