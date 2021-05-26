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
                playerShip.movingUp = true;
                System.out.println("W");
                break;
            case KeyEvent.VK_A:
                playerShip.movingLeft = true;
                System.out.println("A");
                break;
            case KeyEvent.VK_S:
                playerShip.movingDown = true;
                System.out.println("S");
                break;
            case KeyEvent.VK_D:
                playerShip.movingRight = true;
                System.out.println("D");
                break;
            case KeyEvent.VK_SPACE:
                game.bullets.add(new ShipBullet(playerShip.getX(), playerShip.getY() ) );
                System.out.println("SPACE");
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
